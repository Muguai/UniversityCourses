package com.example.GarbageBinTrack;

import static java.lang.Float.parseFloat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private MqttAndroidClient client;
    private static final String SERVER_URI = "tcp://test.mosquitto.org:1883";
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    MyApplication myApplication = (MyApplication) this.getApplication();
    List<GarbageBin> garbageList;

    Button btn_addOne;

    private static MainActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){

            NotificationChannel channel= new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager =getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        MyApplication.UpdateGarbageList();

        garbageList = MyApplication.getGarbageList();

        Toast.makeText(MainActivity.this, "Garbage Bin Size " + garbageList.size(), Toast.LENGTH_SHORT).show();



        btn_addOne = findViewById(R.id.btn_addOne);



        recyclerView = findViewById(R.id.lv_garbageList);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecycleViewAdapter(garbageList, MainActivity.this);
        recyclerView.setAdapter(mAdapter);





        btn_addOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddEditOne.class);
                startActivity(intent);
            }
        });

        connect();

        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    System.out.println("Reconnected to : " + serverURI);
                    // Re-subscribe as we lost it due to new session
                   // subscribe("TrashBin1");
                    for(GarbageBin gb : garbageList) {
                        subscribe(gb.getTopic());
                    }

                } else {
                    System.out.println("Connected to: " + serverURI);
                    //subscribe("TrashBin1");
                    for(GarbageBin gb : garbageList) {
                        subscribe(gb.getTopic());
                    }
                }
            }
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("The Connection was lost.");
            }
            @Override
            public void messageArrived(String topic, MqttMessage message) throws
                    Exception {
                String newMessage = new String(message.getPayload());
                System.out.println("Incoming message: " + newMessage);
                String[] arrOfStr = newMessage.split("-", 3);
                for(GarbageBin gb : MyApplication.getGarbageList()) {
                    if(gb.getTopic().equals(topic)) {

                        //Callibrate garbage bin
                        if(!gb.isCalibrated()){
                            gb.setEmptyBinValue(parseFloat(arrOfStr[0]));
                            gb.setCalibrated(true);
                        }

                        //Check if garbage got picked up
                        if(gb.getPercent() > 5 && parseFloat(arrOfStr[0]) > 50){
                            gb.setPickupNotification(false);

                            Date c = Calendar.getInstance().getTime();
                            System.out.println("Current time => " + c);

                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                            String formattedDate = df.format(c);

                            //Check new month
                            if(!gb.getLastPickupDate().equals(formattedDate)){
                                gb.setAveragePickupProcentThisMonth(0f);
                                gb.setTimePickedUpThisMonth(0);
                            }

                            gb.setTimePickedUpThisMonth(gb.getTimePickedUpThisMonth() + 1);
                            gb.setLastPickupDate(formattedDate);
                            gb.calculateAndSetAvgPercent((1 - (parseFloat(arrOfStr[0]) / gb.getEmptyBinValue())) * 100);


                        }

                        //Send Notification when trash bin is more than 80% full
                        if(parseFloat(arrOfStr[0]) > 80 && !gb.isPickupNotification()){
                            gb.setPickupNotification(true);


                            String notMessage = gb.getName() + " is full and ready too be picked up";
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"My Notification");
                            builder.setContentTitle(gb.getName() + " is full");
                            builder.setContentText(notMessage);
                            builder.setSmallIcon(android.R.drawable.ic_menu_delete);
                            builder.setAutoCancel(true);
                            NotificationManagerCompat managerCompat=NotificationManagerCompat.from(MainActivity.this);
                            managerCompat.notify(1,builder.build());


                        }



                        //Set Garbage bin values
                        gb.setPercent( (1 - (parseFloat(arrOfStr[0]) / gb.getEmptyBinValue())) * 100);
                        gb.setLatitude(parseFloat(arrOfStr[1]));
                        gb.setLongitude(parseFloat(arrOfStr[2]));

                        myApplication.UpdateDatabaseItem(gb);
                    }

                    recyclerView.setHasFixedSize(true);

                    layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);

                    mAdapter = new RecycleViewAdapter(myApplication.getGarbageList(), MainActivity.this);
                    recyclerView.setAdapter(mAdapter);

                }


            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });

    }


    private void connect(){
        String clientId = MqttClient.generateClientId();
        client =
                new MqttAndroidClient(this.getApplicationContext(), SERVER_URI,
                        clientId);
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");
                    System.out.println(TAG + " Success. Connected to " + SERVER_URI);
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception)
                {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure");
                    System.out.println(TAG + " Oh no! Failed to connect to " +
                            SERVER_URI);
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topicToSubscribe) {
        final String topic = topicToSubscribe;
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    System.out.println("Subscription successful to topic: " + topic);
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken,Throwable exception) {
                    System.out.println("Failed to subscribe to topic: " + topic);
                    Toast.makeText(MainActivity.this, "Error failed too subscribe to " + topic, Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static MainActivity getInstance() {
        return instance;
    }





}
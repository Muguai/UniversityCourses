package com.example.GarbageBinTrack;

import static java.lang.Float.parseFloat;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MyApplication extends Application {


    private static List<GarbageBin> garbageList = new ArrayList<GarbageBin>();

    private static int nextId = 1;

    public static DataBaseHelper dataBaseHelper;



    public MyApplication(){

         dataBaseHelper = new DataBaseHelper(MyApplication.this);

    }

    public static GarbageBin findByTopic(String topic) {
        for(GarbageBin gb : garbageList) {
            if(gb.getTopic().equals(topic)) {
                return gb;
            }
        }
        return null;
    }


    public static List<GarbageBin> getGarbageList() {
        return garbageList;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        MyApplication.nextId = nextId;
    }

    public static void RemoveGarbageBin(GarbageBin bin){
        dataBaseHelper.deleteOne(bin);

        garbageList.remove(bin);
        for (GarbageBin bin2 : garbageList) {
            if(bin2.getId() > bin.getId()){
                bin2.setId(bin2.getId() - 1);
            }
        }
        nextId = garbageList.size();

    }

    public static void AddGarbageBin(GarbageBin Bin, Context context){
        Boolean worked = dataBaseHelper.addOne(Bin);
        garbageList.add(Bin);
    }

    public static void UpdateDatabaseItem(GarbageBin bin){
        dataBaseHelper.updateSpecificBin(bin);
    }

    public static void UpdateGarbageList(){
        garbageList = dataBaseHelper.getEveryBin();
        nextId = (garbageList.size());
    }
}

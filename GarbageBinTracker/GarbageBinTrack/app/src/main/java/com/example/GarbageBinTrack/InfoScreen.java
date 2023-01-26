package com.example.GarbageBinTrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class InfoScreen extends AppCompatActivity {

    int id;

    Button backButton;
    Button deleteButton;

    TextView infoValue1;
    TextView infoValue2;
    TextView infoValue3;
    TextView infoValue4;

    TextView infoName;

    private GarbageBin targetedBin;

    MyApplication myApplication = (MyApplication) this.getApplication();
    List<GarbageBin> garbageList;
    private static final String TAG = "InfoScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_screen);
        Intent intent = getIntent();


        backButton = findViewById(R.id.btn_infoBack);
        deleteButton = findViewById(R.id.btn_deleteSensor);

        infoValue1 = findViewById(R.id.tv_infoValue1);
        infoValue2 = findViewById(R.id.tv_infoValue2);
        infoValue3 = findViewById(R.id.tv_infoValue3);
        infoName = findViewById(R.id.tv_infoName);

        garbageList = myApplication.getGarbageList();

        System.out.print("Entering Info Screen");
        System.out.print(intent.getExtras().toString());
        Log.d(TAG, "Enter Info Screen" + intent.getExtras().get("topic"));

        targetedBin = MyApplication.findByTopic((String) intent.getExtras().get("topic"));
        Toast.makeText(InfoScreen.this, "Entered Garbage Bin "+ targetedBin.getName(), Toast.LENGTH_SHORT).show();

        infoValue1.setText(Float.toString(targetedBin.getAveragePickupProcentThisMonth()));
        infoValue2.setText(Integer.toString(targetedBin.getTimePickedUpThisMonth()));
        infoValue3.setText(targetedBin.getLastPickupDate());

        infoName.setText(targetedBin.getName());


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InfoScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(InfoScreen.this, "Removed Garbage Bin "+ targetedBin.getName() , Toast.LENGTH_SHORT).show();

                myApplication.RemoveGarbageBin(targetedBin);
                Intent intent = new Intent(InfoScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
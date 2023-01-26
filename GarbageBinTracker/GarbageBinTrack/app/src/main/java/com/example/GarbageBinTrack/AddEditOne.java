package com.example.GarbageBinTrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddEditOne extends AppCompatActivity {
    Button btn_ok, btn_cancel;
    List<GarbageBin> garbageList;
    MyApplication myApplication = (MyApplication) this.getApplication();
    EditText et_binTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_one);
        btn_ok = findViewById(R.id.btn_ok);
        btn_cancel = findViewById(R.id.btn_cancel);
        Intent intent = getIntent();

        et_binTopic = findViewById(R.id.et_SensorId);

        garbageList = myApplication.getGarbageList();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextId = myApplication.getNextId();
                GarbageBin gnew;
                try {
                    gnew = new GarbageBin(nextId, et_binTopic.getText().toString(), et_binTopic.getText().toString(), 77f, 77f, 0f);
                    myApplication.setNextId(nextId + 1);
                    Toast.makeText(AddEditOne.this, "Created Garbage Bin "+ et_binTopic.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(AddEditOne.this, "Error Creating Garbage Bin", Toast.LENGTH_SHORT).show();
                    gnew = new GarbageBin(-1, "Error", "Error", 0f, 0f, 0f);

                }
                MyApplication.AddGarbageBin(gnew, AddEditOne.this);
                MainActivity.getInstance().subscribe(et_binTopic.getText().toString());

                Intent intent = new Intent(AddEditOne.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditOne.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
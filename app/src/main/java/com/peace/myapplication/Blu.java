package com.peace.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;


import java.util.ArrayList;
import java.util.Set;


import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class Blu extends AppCompatActivity {
    private Set<BluetoothDevice>pairedDevices;
    ListView lv;
    ArrayAdapter aAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();

        setContentView(R.layout.layout_main);
        Button on;
        on = (Button)findViewById(R.id.on);
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bAdapter == null)
                {
                    Toast.makeText(getApplicationContext(),"Bluetooth Not Supported",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!bAdapter.isEnabled()){
                        startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),1);
                        Toast.makeText(getApplicationContext(),"Bluetooth Turned ON",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button off = (Button)findViewById(R.id.off);
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bAdapter.disable();
                Toast.makeText(getApplicationContext(),"Bluetooth Turned OFF", Toast.LENGTH_SHORT).show();
            }
        });
        Button OFF =(Button)findViewById(R.id.off1);
        OFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                getVisible.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 100);
                startActivityForResult(getVisible, 0);
                recreate();

            }
        });
        lv = (ListView)findViewById(R.id.listView);
        Button ON =(Button)findViewById(R.id.on1);
        ON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pairedDevices = bAdapter.getBondedDevices();
                lv = (ListView)findViewById(R.id.listView);
                ArrayList list = new ArrayList();

                for (BluetoothDevice device : pairedDevices) list.add(device.getName());
                Toast.makeText(getApplicationContext(), "Showing Paired Devices", Toast.LENGTH_SHORT).show();

                aAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);

                lv.setAdapter(aAdapter);

            }
        });
        Button paybtn;
        paybtn=findViewById(R.id.paybtn);
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction("com.peace.myapplication.CUSTOM_INTENT");
                sendBroadcast(intent);
            }
        });
        Button b1;
        b1 = (Button)findViewById(R.id.note);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification();
            }
        });

    }
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_faces)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(this, Blu.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


}




package com.moovby.kit;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.moovby.kit.bluetooth.BtHelperClient;
import com.moovby.kit.bluetooth.Filter;
import com.moovby.kit.bluetooth.MessageItem;
import com.moovby.kit.bluetooth.OnSearchDeviceListener;
import com.moovby.kit.bluetooth.OnSendMessageListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private BtHelperClient btHelperClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText codeEt = (EditText) findViewById(R.id.code_tv);

        btHelperClient = BtHelperClient.from(MainActivity.this);

        btHelperClient.requestEnableBt();

        btHelperClient.setFilter(new Filter() {
            @Override
            public boolean isCorrect(String response) {
                return response.trim().length() >= 5;
            }
        });

        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btHelperClient.searchDevices(new OnSearchDeviceListener() {
                    @Override
                    public void onStartDiscovery() {
                        Log.d(TAG, "onStartDiscovery()");
                    }

                    @Override
                    public void onNewDeviceFound(BluetoothDevice device) {
                        Log.d(TAG, "new device: " + device.getName() + " " + device.getAddress());
                    }

                    @Override
                    public void onSearchCompleted(List<BluetoothDevice> bondedList, List<BluetoothDevice> newList) {
                        Log.d(TAG, "SearchCompleted: bondedList" + bondedList.toString());
                        Log.d(TAG, "SearchCompleted: newList" + newList.toString());
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });


            }
        });

        findViewById(R.id.btn_lock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "Sending message to lock...", Toast.LENGTH_SHORT).show();

                MessageItem item = new MessageItem("lock_door");
                btHelperClient.sendMessage(codeEt.getText().toString(), item, true, new OnSendMessageListener() {
                    @Override
                    public void onSuccess(int status, String response) {
                        Toast.makeText(MainActivity.this, "Received response: " + response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onConnectionLost(Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });

            }
        });

        findViewById(R.id.btn_unlock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "Sending message to unlock...", Toast.LENGTH_SHORT).show();

                MessageItem item = new MessageItem("unlock_door");
                btHelperClient.sendMessage(codeEt.getText().toString(), item, true, new OnSendMessageListener() {
                    @Override
                    public void onSuccess(int status, String response) {
                        Toast.makeText(MainActivity.this, "Received response: " + response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onConnectionLost(Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btHelperClient.close();
    }

    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
}

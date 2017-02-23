package com.moovby.kit.bluetooth.runnable;

import android.bluetooth.BluetoothSocket;

import com.moovby.kit.bluetooth.OnReceiveMessageListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class ReadRunnable implements Runnable {

    private final OnReceiveMessageListener mListener;
    private final BluetoothSocket mSocket;
    private volatile boolean mReadable = true;

    public ReadRunnable(OnReceiveMessageListener listener, BluetoothSocket socket) {
        mListener = listener;
        mSocket = socket;
    }

    @Override
    public void run() {
        InputStream stream = null;
        try {
            stream = mSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (stream != null && mReadable) ;
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        while (mReadable) {
            try {
                while (stream.available() == 0) ;
            } catch (IOException e) {
                mListener.onConnectionLost(e);
                break;
            }

            while (mReadable) {

                try {
                    String s = reader.readLine();
                    mListener.onNewLine(s);
                } catch (IOException e) {
                    mListener.onConnectionLost(e);
                    break;
                }


            }

        }


    }


}
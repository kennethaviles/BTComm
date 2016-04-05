package edu.uprm.capstone.icom5047.btcomm;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Kenneth on 3/25/2016.
 */
public class ConnectThread extends Thread {
    private static final String TAG = "CONNECTTHREAD" ;
    private final BluetoothDevice blueDevice;
    private final BluetoothSocket blueSocket;

    public ConnectThread(BluetoothDevice blueDevice, UUID uuid) {
        BluetoothSocket temp = null;
        this.blueDevice = blueDevice;

        try {
            temp = this.blueDevice.createRfcommSocketToServiceRecord(uuid);
        }
        catch (IOException e){
            Log.d(TAG, "Could not start listening for RFCOMM");
        }
        blueSocket = temp;
    }

    public boolean connect(){
        try {
            blueSocket.connect();
        }
        catch (IOException e){
            Log.d(TAG, "Could not connect: " + e.toString());
            try {
                blueSocket.close();
            }
            catch (IOException e2){
                Log.d(TAG, "Could not close connection: " + e2.toString());
                return false;
            }
        }
        return true;
    }

    public boolean cancel(){
        try{
            blueSocket.close();
        }
        catch (IOException e){
            return false;
        }
        return true;
    }

}

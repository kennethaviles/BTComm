package edu.uprm.capstone.icom5047.btcomm.Connecting;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Kenneth on 3/25/2016.
 */
public class ServerConnectThread extends Thread {
    private static final String TAG = "SERVERCONNECT" ;
    private BluetoothSocket blueSocket;

    public ServerConnectThread(){}

    public void acceptConnect(BluetoothAdapter blueAdapter, UUID uuid){
        BluetoothServerSocket temp = null;
        try {
            temp = blueAdapter.listenUsingRfcommWithServiceRecord("Service_Name", uuid);
        }
        catch (IOException e){
            Log.d(TAG, "Could not get a BluetoothServerSocket: " + e.toString());
        }
        while (true){
            try {
                blueSocket = temp.accept();
            }
            catch (IOException e){
                Log.d(TAG, "Could not accept an incoming connection.");
                break;
            }
            if (blueSocket != null){
                try{
                    temp.close();
                }
                catch (IOException e){
                    Log.d(TAG, "Could not close ServerSocket: " + e.toString());
                }
                break;
            }
        }
    }

    public void closeConnect(){
        try{
            blueSocket.close();
        }
        catch (IOException e){
            Log.d(TAG, "Could not close connection: " + e.toString());
        }
    }
}

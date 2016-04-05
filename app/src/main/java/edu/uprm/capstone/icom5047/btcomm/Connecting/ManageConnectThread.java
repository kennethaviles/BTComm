package edu.uprm.capstone.icom5047.btcomm.Connecting;

import android.bluetooth.BluetoothSocket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Kenneth on 3/25/2016.
 */
public class ManageConnectThread extends Thread {
    public ManageConnectThread(){}

    public void sendData(BluetoothSocket socket, int data) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(data);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(out.toByteArray());
    }

    public int receiveData(BluetoothSocket socket) throws IOException{
        byte[] buffer = new byte[4];
        ByteArrayInputStream in = new ByteArrayInputStream(buffer);
        InputStream inputStream = socket.getInputStream();
        inputStream.read(buffer);
        return in.read();
    }
}

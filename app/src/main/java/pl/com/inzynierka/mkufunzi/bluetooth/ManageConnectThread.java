package pl.com.inzynierka.mkufunzi.bluetooth;

import android.bluetooth.BluetoothSocket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by impresyjna on 06.01.16.
 */
public class ManageConnectThread extends Thread {

    public ManageConnectThread() { }

    public void sendData(BluetoothSocket socket, int data) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream(4);
        output.write(data);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(output.toByteArray());
    }

    public String receiveData(BluetoothSocket socket) throws IOException{
        byte[] buffer = new byte[1024];
        int bytes;
        InputStream inputStream = socket.getInputStream();
        inputStream.read(buffer);
        bytes = inputStream.read(buffer);
        String message = new String(buffer, "UTF-8");
        return message;
    }
}

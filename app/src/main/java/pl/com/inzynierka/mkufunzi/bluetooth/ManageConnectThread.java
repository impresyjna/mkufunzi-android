package pl.com.inzynierka.mkufunzi.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.AppUser;

/**
 * Created by impresyjna on 06.01.16.
 */
public class ManageConnectThread extends Thread {

    public ManageConnectThread() {
    }

    public String receiveData(BluetoothSocket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        StringBuffer receivedMessage = new StringBuffer();
        int ch;
        while ((ch = inputStream.read()) == 10 || ch == 13) {

        }
        receivedMessage.append(Character.toString((char) ch));
        while ((ch = inputStream.read()) != 10 && (ch != 13)) {
            receivedMessage.append(Character.toString((char) ch));
        }

        return receivedMessage.toString();
    }
}

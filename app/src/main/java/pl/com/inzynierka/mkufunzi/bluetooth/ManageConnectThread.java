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
 * Classed used to receive data from BT device
 */
public class ManageConnectThread extends Thread {

    /**
     *  Constructor
     */
    public ManageConnectThread() {
    }

    /**
     * Method called to receiveData from BT device.
     * @param socket - socket to communicate wih device
     * @return String received from BT device 
     * @throws IOException
     */
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

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


    private static String msg = "";

    public ManageConnectThread() {
    }

    public void sendData(BluetoothSocket socket, int data) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream(4);
        output.write(data);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(output.toByteArray());
    }

    public String receiveData(BluetoothSocket socket) throws IOException {
            InputStream inputStream = socket.getInputStream();
            msg = "";
            StringBuffer dupa = new StringBuffer();
            List<Integer> inputList = new ArrayList<>();
            List<String> inputList2 = new ArrayList<>();
            char[] msgTemp = new char[200];
            int ch;
            while ((ch = inputStream.read()) == 10 || ch == 13) {

            }
            dupa.append(Character.toString((char) ch));
            while ((ch = inputStream.read()) != 10 && (ch  != 13)) {

                dupa.append(Character.toString((char) ch));

                inputList.add(ch);
                Log.d("BytesCount", "Result 2  " + dupa + " " + dupa.length());

            }
            Log.e("BytesCount", dupa.length()+ "" );

            return dupa.toString();
    }
}

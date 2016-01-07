package pl.com.inzynierka.mkufunzi.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by impresyjna on 06.01.16.
 */
public class ConnectThread extends Thread{

    private final BluetoothDevice bTDevice;
    private final BluetoothSocket bTSocket;

    public ConnectThread(BluetoothDevice bTDevice, UUID UUID) {
        BluetoothSocket tmp = null;
        this.bTDevice = bTDevice;

        try {
            tmp = this.bTDevice.createInsecureRfcommSocketToServiceRecord(UUID);
            Log.d("CONNECTTHREAD", "Created");
            Log.d("btDeviceName", bTDevice.getName());
        }
        catch (IOException e) {
            Log.d("CONNECTTHREAD", "Could not start listening for RFCOMM");
        }
        this.bTSocket = tmp;

    }

    public boolean connect() {

        Log.d("btDeviceNameConnect", bTDevice.getName());
        try {
            bTSocket.connect();
            Log.d("CONNECTTHREAD", "Connected");
        } catch(IOException e) {
            Log.d("CONNECTTHREAD","Could not connect: " + e.toString());
            e.printStackTrace();
            try {
                bTSocket.close();
            } catch(IOException close) {
                Log.d("CONNECTTHREAD", "Could not close connection:" + e.toString());
                return false;
            }
        }
        return true;
    }

    public boolean cancel() {
        try {
            bTSocket.close();
        } catch(IOException e) {
            return false;
        }
        return true;
    }

    public BluetoothSocket getbTSocket() {
        return bTSocket;
    }
}

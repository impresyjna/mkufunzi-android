package pl.com.inzynierka.mkufunzi.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Class used to get socket to connect with BT device and check if connection exists
 */
public class ConnectThread extends Thread{

    private final BluetoothDevice bTDevice;
    private final BluetoothSocket bTSocket;

    /**
     * Method used to get socket with device
     * @param bTDevice BluetoothDevice to connect with
     * @param UUID UUID number for connection(it is like port in network connection)
     */
    public ConnectThread(BluetoothDevice bTDevice, UUID UUID) {
        BluetoothSocket tmp = null;
        this.bTDevice = bTDevice;

        try {
            tmp = this.bTDevice.createInsecureRfcommSocketToServiceRecord(UUID);
        }
        catch (IOException e) {
            Log.d("CONNECTTHREAD", "Could not start listening for RFCOMM");
        }
        this.bTSocket = tmp;

    }

    /**
     * Method checks if connection exists
     * @return
     */
    public boolean connect() {

        Log.d("btDeviceNameConnect", bTDevice.getName());
        try {
            bTSocket.connect();
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

    /**
     * Method used to cancel the connection with BT
     * @return
     */
    public boolean cancel() {
        try {
            bTSocket.close();
        } catch(IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Getter for btSocket
     * @return BluetoothSocket object 
     */
    public BluetoothSocket getbTSocket() {
        return bTSocket;
    }

}

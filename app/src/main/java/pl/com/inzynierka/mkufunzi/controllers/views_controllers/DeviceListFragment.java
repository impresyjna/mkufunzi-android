package pl.com.inzynierka.mkufunzi.controllers.views_controllers;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.provider.*;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import pl.com.inzynierka.mkufunzi.R;
import pl.com.inzynierka.mkufunzi.adapters.DeviceListAdapter;
import pl.com.inzynierka.mkufunzi.bluetooth.ConnectThread;
import pl.com.inzynierka.mkufunzi.bluetooth.ManageConnectThread;
import pl.com.inzynierka.mkufunzi.models.AppUser;
import pl.com.inzynierka.mkufunzi.models.DeviceItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class DeviceListFragment extends Fragment implements AbsListView.OnItemClickListener {

    private ArrayList<DeviceItem> deviceItemList;
    private AppUser appUser = AppUser.getInstance();
    private NavigationAndOptionsController navigationAndOptionsController = new NavigationAndOptionsController();
    private AppCompatActivity activity;

    private OnFragmentInteractionListener mListener;
    private static BluetoothAdapter bTAdapter;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ArrayAdapter<DeviceItem> mAdapter;

    // TODO: Rename and change types of parameters
    public static DeviceListFragment newInstance(BluetoothAdapter adapter) {
        DeviceListFragment fragment = new DeviceListFragment();
        bTAdapter = adapter;
        return fragment;
    }

    private final BroadcastReceiver bReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getName().contains("Mkufunzi")) {
                    // Create a new device item
                    DeviceItem newDevice = new DeviceItem(device.getName(), device.getAddress(), "false");
                    // Add it to our adapter
                    mAdapter.add(newDevice);
                }
            }
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DeviceListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d("DEVICELIST", "Super called for DeviceListFragment onCreate\n");
        deviceItemList = new ArrayList<DeviceItem>();

        Set<BluetoothDevice> pairedDevices = bTAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().contains("Mkufunzi")) {
                    DeviceItem newDevice = new DeviceItem(device.getName(), device.getAddress(), "false");
                    deviceItemList.add(newDevice);
                }
            }
        }

        // If there are no devices, add an item that states so. It will be handled in the view.
        if (deviceItemList.size() == 0) {
            deviceItemList.add(new DeviceItem("Brak urządzeń", "", "false"));
        }

        mAdapter = new DeviceListAdapter(getActivity(), deviceItemList, bTAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deviceitem_list, container, false);
        ToggleButton scan = (ToggleButton) view.findViewById(R.id.scan);
        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        scan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                if (isChecked) {
                    mAdapter.clear();
                    getActivity().registerReceiver(bReciever, filter);
                    bTAdapter.startDiscovery();
                } else {
                    getActivity().unregisterReceiver(bReciever);
                    bTAdapter.cancelDiscovery();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BluetoothDevice btDevice = bTAdapter.getRemoteDevice(deviceItemList.get(position).getAddress());
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        ConnectThread conn = new ConnectThread(btDevice, uuid);
        if (conn.connect()) {
            appUser.setConnectThread(conn);
            navigationAndOptionsController.openIntent(activity, TrainingMonitor.class);
        }
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(deviceItemList.get(position).getDeviceName());
        }

    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }
}

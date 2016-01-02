package pl.com.inzynierka.mkufunzi.API.measure_types;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import pl.com.inzynierka.mkufunzi.API.ServerConnector;
import pl.com.inzynierka.mkufunzi.controllers.models_controllers.MeasureTypesController;
import pl.com.inzynierka.mkufunzi.models.MeasureType;

/**
 * Created by impresyjna on 01.01.16.
 */
public class IndexMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();

    private ProgressDialog pDialog;

    private AppCompatActivity activity;
    public Menu menu;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Trwa ładowanie. Proszę czekać...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... args) {
        try {

            HashMap<String, String> params = new HashMap<>();

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getMEASURE_TYPES_INDEX(), "GET", params);

            if (json != null) {
                Log.d("JSON result", json.toString());
                return json;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(JSONObject json) {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        try {
            if (json.has("measure_types") && !json.isNull("measure_types")) {
                Menu subMenu = menu.addSubMenu("Kartoteka");
                List<MeasureType> measureTypes = new MeasureTypesController().getArrayFromJSON(json);
                for (MeasureType measureType : measureTypes) {
                    measureType.save();
                    subMenu.add(measureType.name.substring(0,1).toUpperCase()+measureType.name.substring(1).toLowerCase());
                }
                MenuItem mi = menu.getItem(menu.size() - 1);
                mi.setTitle(mi.getTitle());
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


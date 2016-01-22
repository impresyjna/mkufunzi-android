package pl.com.inzynierka.mkufunzi.API.blood_types;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import pl.com.inzynierka.mkufunzi.API.ServerConnector;
import pl.com.inzynierka.mkufunzi.controllers.models_controllers.BloodTypesController;
import pl.com.inzynierka.mkufunzi.models.BloodType;

/**
 * Class extends AsyncTask and is used to get BloodTypes from server
 */
public class GetBloodTypesMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();
    private ProgressDialog pDialog;
    private AppCompatActivity activity;

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

    /**
     * To call this method developer has to call in view controller
     * GetBloodTypesMobile getBloodTypesMobile = new GetBloodTypesMobile();
     * getBloodTypesMobile.setActivity(this);
     * getBloodTypesMobile.execute();
     * @param args no params needed
     * @return
     */
    @Override
    protected JSONObject doInBackground(String... args) {
        try {

            HashMap<String, String> params = new HashMap<>();

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getGET_BLOOD_TYPES(), "GET", params);

            if (json != null) {
                Log.d("JSON result", json.toString());
                return json;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method saves all bloodTypes in sqlite3 database on device 
     * @param json
     */
    protected void onPostExecute(JSONObject json) {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        try {
            if (json.has("blood_types") && !json.isNull("blood_types")) {
                List<BloodType> bloodTypes = new BloodTypesController().getArrayFromJSON(json);
                for (BloodType bloodType : bloodTypes) {
                    bloodType.save();
                }
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

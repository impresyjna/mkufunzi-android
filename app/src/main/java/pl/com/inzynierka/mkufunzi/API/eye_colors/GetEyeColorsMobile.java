package pl.com.inzynierka.mkufunzi.API.eye_colors;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import pl.com.inzynierka.mkufunzi.API.ServerConnector;
import pl.com.inzynierka.mkufunzi.controllers.models_controllers.EyeColorsController;
import pl.com.inzynierka.mkufunzi.models.EyeColor;

/**
 * Class used to get list of eyeColors from server
 */
public class GetEyeColorsMobile extends AsyncTask<String, String, JSONObject> {

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
     * Method used to connect with server and get eyeColorsIndex from it
     * @param args - no args needed
     * @return
     */
    @Override
    protected JSONObject doInBackground(String... args) {
        try {

            HashMap<String, String> params = new HashMap<>();

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getGET_EYE_COLORS(), "GET", params);

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
     * Saves all read data from server to local sqlite3 database
     * @param json - JSONObject from doInBackground method 
     */
    protected void onPostExecute(JSONObject json) {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        try {
            if (json.has("eye_colors") && !json.isNull("eye_colors")) {
                List<EyeColor> eyeColors = new EyeColorsController().getArrayFromJSON(json);
                for (EyeColor eyeColor : eyeColors) {
                    eyeColor.save();
                }
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

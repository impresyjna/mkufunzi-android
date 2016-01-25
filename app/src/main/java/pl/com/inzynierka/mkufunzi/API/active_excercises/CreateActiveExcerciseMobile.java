package pl.com.inzynierka.mkufunzi.API.active_excercises;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import pl.com.inzynierka.mkufunzi.API.ServerConnector;
import pl.com.inzynierka.mkufunzi.models.ActiveExcercise;
import pl.com.inzynierka.mkufunzi.models.AppUser;

/**
 * Class used to connect with server and create new ActiveExcercise at the beginning. Extends AsyncTask.
 */
public class CreateActiveExcerciseMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();

    private ProgressDialog pDialog;

    private AppCompatActivity activity;
    private AppUser appUser = AppUser.getInstance();

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Proszę czekać");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    /**
     * Method called to make connection with server
     * @param args args[0] - protege_id, args[1] - training_id
     * @return json with all information about new active excercise if success, failure message otherwise
     */
    @Override
    protected JSONObject doInBackground(String... args) {
        try {

            HashMap<String, String> params = new HashMap<>();
            params.put("protege_id",args[0]);
            params.put("training_id", args[1]);

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getCREATE_ACTIVE_EXCERCISE_MOBILE(), "POST", params);

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
            if (json.getString("status").equals("success")) {
                appUser.setActiveExcercise(new ActiveExcercise(json.getJSONObject("active_excercise")));
            } else {
                Toast.makeText(activity, "Trening odbędzie się bez zapisu w bazie", Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package pl.com.inzynierka.mkufunzi.API.trainings;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import pl.com.inzynierka.mkufunzi.API.ServerConnector;
import pl.com.inzynierka.mkufunzi.models.AppUser;
import pl.com.inzynierka.mkufunzi.models.Training;

/**
 * Class extends AsyncTask to make connection with server in order to create new training in database
 */
public class CreateTrainingMobile extends AsyncTask<String, String, JSONObject> {

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
     * @param args args[0] - protege_id
     * @return json with all information about new training if success, failure message otherwise
     */
    @Override
    protected JSONObject doInBackground(String... args) {
        try {

            HashMap<String, String> params = new HashMap<>();
            params.put("protege_id",args[0]);

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getCREATE_TRAINING_MOBILE(), "POST", params);

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
                appUser.setTraining(new Training(json.getJSONObject("training")));
            } else {
                Toast.makeText(activity, "Trening odbędzie się bez zapisu w bazie", Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


package pl.com.inzynierka.mkufunzi.API.measurements;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import pl.com.inzynierka.mkufunzi.API.ServerConnector;
import pl.com.inzynierka.mkufunzi.controllers.views_controllers.MeasurementPage;
import pl.com.inzynierka.mkufunzi.controllers.views_controllers.NavigationAndOptionsController;

/**
 * Class which extends AsyncTask.
 * It is called to save measurement on server
 */
public class PostMeasurementMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();
    private ProgressDialog pDialog;
    private AppCompatActivity activity;
    private String measureTypeName;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Trwa dodawanie");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    /**
     * Method called to connect with server
     * @param args args[0] - value, args[1] - cardId, args[2] - measure_type_id
     *             args[3] - measureType name(not used when application connect with server, necessary later)
     *             args[4] - second_value
     * @return
     */
    @Override
    protected JSONObject doInBackground(String... args) {
        try {

            HashMap<String, String> params = new HashMap<>();
            params.put("value",args[0]);
            params.put("card_id",args[1]);
            params.put("measure_type_id",args[2]);
            params.put("second_value", args[4]);

            measureTypeName = args[3];

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getPOST_MEASUREMENT(), "POST", params);

            if (json != null) {
                Log.d("JSON result", json.toString());
                return json;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /** If save was correct method opens the intent with measurement */
    protected void onPostExecute(JSONObject json) {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }

        try {
            if (json.getString("status").equals("success")) {
                new NavigationAndOptionsController().openIntentWithParam(activity, MeasurementPage.class, measureTypeName);
                activity.finish();
            } else {
                Toast.makeText(activity, json.getString("Nastąpił błąd"), Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


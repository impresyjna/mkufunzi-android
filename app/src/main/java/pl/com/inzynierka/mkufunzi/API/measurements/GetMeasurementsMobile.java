package pl.com.inzynierka.mkufunzi.API.measurements;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import pl.com.inzynierka.mkufunzi.API.ServerConnector;
import pl.com.inzynierka.mkufunzi.adapters.MeasurementAdapter;
import pl.com.inzynierka.mkufunzi.controllers.models_controllers.MeasurementsController;
import pl.com.inzynierka.mkufunzi.models.Measurement;

/**
 * Created by impresyjna on 05.01.16.
 */
public class GetMeasurementsMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();
    private ProgressDialog pDialog;
    private AppCompatActivity activity;
    private RecyclerView rvMeasures;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setRvMeasures(RecyclerView rvMeasures) {
        this.rvMeasures = rvMeasures;
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
            params.put("card_id", args[0]);
            params.put("measure_type_id", args[1]);

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getGET_MEASUREMENTS(), "GET", params);

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
            if (json.has("measurements") && !json.isNull("measurements")) {
                List<Measurement> measurements = new MeasurementsController().getArrayFromJSON(json);
                // Create adapter passing in the sample user data
                MeasurementAdapter adapter = new MeasurementAdapter(measurements);
                // Attach the adapter to the recyclerview to populate items
                rvMeasures.setAdapter(adapter);
                // Set layout manager to position the items
                rvMeasures.setLayoutManager(new LinearLayoutManager(activity));
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
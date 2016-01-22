package pl.com.inzynierka.mkufunzi.API.measurements;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.activeandroid.query.Select;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import pl.com.inzynierka.mkufunzi.API.ServerConnector;
import pl.com.inzynierka.mkufunzi.adapters.MeasurementAdapter;
import pl.com.inzynierka.mkufunzi.controllers.models_controllers.MeasurementsController;
import pl.com.inzynierka.mkufunzi.models.MeasureType;
import pl.com.inzynierka.mkufunzi.models.Measurement;

/**
 * Method called to get measurements from server.
 */
public class GetMeasurementsMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();
    private ProgressDialog pDialog;
    private AppCompatActivity activity;
    private RecyclerView rvMeasures;
    private MeasureType measureType;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    /** RecyclerView for measurements in activity */
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

    /**
     * Method called to get data from server.
     * @param args args[0] - card_id, args[1] - measure_type_id
     * @return
     */
    @Override
    protected JSONObject doInBackground(String... args) {
        try {

            HashMap<String, String> params = new HashMap<>();
            params.put("card_id", args[0]);
            params.put("measure_type_id", args[1]);

            measureType = new Select().from(MeasureType.class).where("measure_id = ?", args[1]).executeSingle();
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

    /**
     * If there is any measurement this method shows them in view, otherwise does nothing
     * @param json - JSONObject from doInBackground method 
     */
    protected void onPostExecute(JSONObject json) {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        try {
            if (json.has("measurements") && !json.isNull("measurements")) {
                List<Measurement> measurements = new MeasurementsController().getArrayFromJSON(json);
                // Create adapter passing in the sample user data
                MeasurementAdapter adapter = new MeasurementAdapter(measurements,measureType.unit);
                // Attach the adapter to the recyclerview to populate items
                rvMeasures.setAdapter(adapter);
                // Set layout manager to position the items
                final LinearLayoutManager mLayoutManager;
                mLayoutManager = new LinearLayoutManager(activity);
                rvMeasures.setLayoutManager(mLayoutManager);
                rvMeasures.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    private boolean loading = true;
                    int pastVisiblesItems, visibleItemCount, totalItemCount;
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        if (dy > 0) //check for scroll down
                        {

                            visibleItemCount = mLayoutManager.getChildCount();
                            totalItemCount = mLayoutManager.getItemCount();
                            pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                            if (loading) {
                                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                    loading = false;
                                    Log.v("...", "Last Item Wow !");
                                    //Do pagination.. i.e. fetch new data
                                }
                            }
                        }
                    }
                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

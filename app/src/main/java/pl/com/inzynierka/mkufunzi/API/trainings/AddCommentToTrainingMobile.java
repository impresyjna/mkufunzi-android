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
import pl.com.inzynierka.mkufunzi.controllers.views_controllers.NavigationAndOptionsController;
import pl.com.inzynierka.mkufunzi.controllers.views_controllers.SingleTrainingSummary;

/** Class extends AsyncTask. It has to communicate with server and send data to save this comment in database */
public class AddCommentToTrainingMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();
    private ProgressDialog pDialog;
    private AppCompatActivity activity;
    private String trainingId;
    private NavigationAndOptionsController navigationAndOptionsController = new NavigationAndOptionsController();

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

    @Override
    /**
     * Method communicates with server. args[0] - trainingId, args[1] - comment
     */
    protected JSONObject doInBackground(String... args) {
        try {

            HashMap<String, String> params = new HashMap<>();
            params.put("id",args[0]);
            params.put("comment", args[1]);

            trainingId = args[0];

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getADD_COMMENT_TO_TRAINING_MOBILE(), "POST", params);

            if (json != null) {
                Log.d("JSON result", json.toString());
                return json;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /** After receive message from server method has to decise. If success it opens new Intent with SingleTraininSummary, if failure shows the Toast message */
    protected void onPostExecute(JSONObject json) {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }

        try {
            if (json.getString("status").equals("success")) {
                navigationAndOptionsController.openIntentWithTrainingParam(activity, SingleTrainingSummary.class, trainingId);
            } else {
                Toast.makeText(activity, "Wystąpił problem przy zapisie", Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

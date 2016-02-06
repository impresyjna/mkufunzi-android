package pl.com.inzynierka.mkufunzi.API.trainings;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import pl.com.inzynierka.mkufunzi.API.ServerConnector;
import pl.com.inzynierka.mkufunzi.adapters.DoneExcerciseAdapter;
import pl.com.inzynierka.mkufunzi.adapters.MeasurementAdapter;
import pl.com.inzynierka.mkufunzi.controllers.models_controllers.DoneExcercisesController;
import pl.com.inzynierka.mkufunzi.models.DoneExcercise;
import pl.com.inzynierka.mkufunzi.models.Training;

/**
 * Created by impresyjna on 05.02.16.
 */
public class TrainingShowMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();
    private ProgressDialog pDialog;
    private AppCompatActivity activity;
    private TextView singleTrainingId, singleTrainingStart, singleTrainingStop, singleTrainingComment;
    private RecyclerView rvDoneExcercises;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public TrainingShowMobile(AppCompatActivity activity, TextView singleTrainingId, TextView singleTrainingStart, TextView singleTrainingStop, TextView singleTrainingComment, RecyclerView rvDoneExcercises) {
        this.activity = activity;
        this.singleTrainingId = singleTrainingId;
        this.singleTrainingStart = singleTrainingStart;
        this.singleTrainingStop = singleTrainingStop;
        this.singleTrainingComment = singleTrainingComment;
        this.rvDoneExcercises = rvDoneExcercises;
    }

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Trwa ładowanie");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... args) {
        try {

            HashMap<String, String> params = new HashMap<>();
            params.put("id", args[0]);

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getTRAINING_SHOW_MOBILE(), "GET", params);

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
                Training training = new Training(json.getJSONObject("training"));
                singleTrainingId.setText(Integer.toString(training.id));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startDate, stopDate;
                String startFormattedDate = null, stopFormattedDate = null;
                try {
                    startDate = format.parse(training.start);
                    startFormattedDate = df.format(startDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    stopDate = format.parse(training.end);
                    stopFormattedDate = df.format(stopDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                singleTrainingStart.setText(startFormattedDate);
                singleTrainingStop.setText(stopFormattedDate);
                if (!training.comment.equals("null")) {
                    singleTrainingComment.setText(training.comment);
                }
                List<DoneExcercise> doneExcerciseList = new DoneExcercisesController().getArrayFromJSON(json);
                DoneExcerciseAdapter adapter = new DoneExcerciseAdapter(doneExcerciseList);
                // Attach the adapter to the recyclerview to populate items
                rvDoneExcercises.setAdapter(adapter);
                // Set layout manager to position the items
                final LinearLayoutManager mLayoutManager;
                mLayoutManager = new LinearLayoutManager(activity);
                rvDoneExcercises.setLayoutManager(mLayoutManager);
            } else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}


package pl.com.inzynierka.mkufunzi.API.trainings;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.com.inzynierka.mkufunzi.API.ServerConnector;
import pl.com.inzynierka.mkufunzi.adapters.TrainingHistoryListAdapter;
import pl.com.inzynierka.mkufunzi.controllers.models_controllers.TrainingsController;
import pl.com.inzynierka.mkufunzi.controllers.views_controllers.NavigationAndOptionsController;
import pl.com.inzynierka.mkufunzi.controllers.views_controllers.SingleTrainingSummary;
import pl.com.inzynierka.mkufunzi.models.Training;

/**
 * Class extends AsyncTask to make possible to communicate with server in background task
 * It has to receive data from server about all training belongs to actual user and show them in the activity 
 */
public class IndexMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();
    private ProgressDialog pDialog;
    private AppCompatActivity activity;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<Training>> listDataChild;
    private TrainingHistoryListAdapter listAdapter;
    private NavigationAndOptionsController navigationAndOptionsController = new NavigationAndOptionsController();

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setExpListView(ExpandableListView expListView) {
        this.expListView = expListView;
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
            params.put("protege_id", args[0]);

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getTRAININGS_INDEX_MOBILE(), "GET", params);

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
                List<Training> trainings = new TrainingsController().getArrayFromJSON(json);
                for (Training training : trainings) {
                    training.save();
                }
                // preparing list data
                prepareListData();
                listAdapter = new TrainingHistoryListAdapter(activity, listDataHeader, listDataChild);

                // setting list adapter
                expListView.setAdapter(listAdapter);

                expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v,
                                                int groupPosition, int childPosition, long id) {
                        Training training = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                        navigationAndOptionsController.openIntentWithTrainingParam(activity, SingleTrainingSummary.class, Integer.toString(training.id));
                        return false;
                    }
                });

                expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    @Override
                    public void onGroupExpand(int groupPosition) {

                    }
                });

                // Listview Group collasped listener
                expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                    @Override
                    public void onGroupCollapse(int groupPosition) {

                    }
                });
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void prepareListData() {
        List<Training>trainings = new Select().from(Training.class).execute();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Training>>();
        List<Training> childTrainings = new ArrayList<>();
        for(Training training: trainings){
            String date = training.start;
            int position = date.indexOf("T");
            boolean foundDuplicate = false;
            date = date.substring(0,position);
            position = 0;
            for(int i=0; i<listDataHeader.size(); i++){
                position = i;
                if(listDataHeader.get(i).equals(date)) {
                    foundDuplicate = true;
                    break;
                }
            }
            if(!foundDuplicate){
                listDataHeader.add(date);
                childTrainings.clear();
                childTrainings.add(training);
            } else {
                childTrainings.add(training);
            }
            if(listDataHeader.size()>position+1){
                position += 1;
            }
            listDataChild.put(listDataHeader.get(position), new ArrayList<>(childTrainings));
        }
    }
}

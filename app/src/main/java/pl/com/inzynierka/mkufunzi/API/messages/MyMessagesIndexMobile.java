package pl.com.inzynierka.mkufunzi.API.messages;

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
import pl.com.inzynierka.mkufunzi.adapters.MessageAdapter;
import pl.com.inzynierka.mkufunzi.controllers.models_controllers.MessagesController;
import pl.com.inzynierka.mkufunzi.models.AppUser;
import pl.com.inzynierka.mkufunzi.models.Message;

/**
 * Created by impresyjna on 27.01.16.
 */
public class MyMessagesIndexMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();
    private ProgressDialog pDialog;
    private AppCompatActivity activity;
    private RecyclerView rvMessages;
    private AppUser appUser = AppUser.getInstance();

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    /** RecyclerView for measurements in activity */
    public void setRvMeasures(RecyclerView rvMessages) {
        this.rvMessages = rvMessages;
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
            params.put("id", Integer.toString(appUser.getUser().id));

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getMY_MESSAGES_INDEX_MOBILE(), "GET", params);

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
                List<Message> messages = new MessagesController().getArrayFromJSON(json);
                // Create adapter passing in the sample user data
                MessageAdapter adapter = new MessageAdapter(messages);
                // Attach the adapter to the recyclerview to populate items
                rvMessages.setAdapter(adapter);
                // Set layout manager to position the items
                final LinearLayoutManager mLayoutManager;
                mLayoutManager = new LinearLayoutManager(activity);
                rvMessages.setLayoutManager(mLayoutManager);
                rvMessages.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
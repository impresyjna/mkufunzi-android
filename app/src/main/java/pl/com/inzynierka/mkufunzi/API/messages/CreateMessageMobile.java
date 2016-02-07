package pl.com.inzynierka.mkufunzi.API.messages;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import pl.com.inzynierka.mkufunzi.API.ServerConnector;
import pl.com.inzynierka.mkufunzi.controllers.views_controllers.MyMessages;
import pl.com.inzynierka.mkufunzi.controllers.views_controllers.NavigationAndOptionsController;
import pl.com.inzynierka.mkufunzi.models.AppUser;

/**
 * Class extends AsyncTask to make possible to communicate with server in background task
 * It has to send data to server and call the method to save this in database on server
 */
public class CreateMessageMobile extends AsyncTask<String, String, JSONObject> {

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
        pDialog.setMessage("Trwa wysyłanie");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }


    @Override
    protected JSONObject doInBackground(String... args) {
        try {

            HashMap<String, String> params = new HashMap<>();
            params.put("content",args[0]);
            params.put("user_send_id",Integer.toString(appUser.getUser().id));
            params.put("user_receive_id",Integer.toString(appUser.getProtege().trainerId));

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getCREATE_MESSAGE_MOBILE(), "POST", params);

            if (json != null) {
                Log.d("JSON result", json.toString());
                return json;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /** After correct save open the Activity with messages from server */
    protected void onPostExecute(JSONObject json) {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }

        try {
            if (json.getString("status").equals("success")) {
               new NavigationAndOptionsController().openIntent(activity, MyMessages.class);
            } else {
                Toast.makeText(activity, "Nie udało się wysłać wiadomości", Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

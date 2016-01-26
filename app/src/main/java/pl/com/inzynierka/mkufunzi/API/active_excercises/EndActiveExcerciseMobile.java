package pl.com.inzynierka.mkufunzi.API.active_excercises;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

import pl.com.inzynierka.mkufunzi.API.ServerConnector;
import pl.com.inzynierka.mkufunzi.models.AppUser;

/**
 * Created by impresyjna on 26.01.16.
 */
public class EndActiveExcerciseMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();

    private AppUser appUser = AppUser.getInstance();


    @Override
    protected void onPreExecute() {
    }

    /**
     * Method called to make connection with server to update active excercise
     * @param args - all params from appUser.getActiveExcercise
     * @return json
     */
    @Override
    protected JSONObject doInBackground(String... args) {
        try {

            HashMap<String, String> params = new HashMap<>();
            params.put("id", Integer.toString(appUser.getActiveExcercise().id));

            Log.e("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getEND_ACTIVE_EXCERCISE_MOBILE(), "POST", params);

            if (json != null) {
                Log.e("JSON result", json.toString());
                return json;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(JSONObject json) {
        //TODO: Write here implementation of new excercise

    }
}


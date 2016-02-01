package pl.com.inzynierka.mkufunzi.API.active_excercises;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import pl.com.inzynierka.mkufunzi.API.ServerConnector;
import pl.com.inzynierka.mkufunzi.models.AppUser;

/**
 * Created by impresyjna on 26.01.16.
 */
public class EndActiveExcerciseMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();
    private AppCompatActivity activity;
    private AppUser appUser = AppUser.getInstance();

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
    }


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
        try {
            if (json.getString("status").equals("success")) {
                CreateActiveExcerciseMobile createActiveExcerciseMobile = new CreateActiveExcerciseMobile();
                createActiveExcerciseMobile.setActivity(activity);
                createActiveExcerciseMobile.execute(Integer.toString(appUser.getProtege().id), Integer.toString(appUser.getTraining().id), Integer.toString(appUser.getActiveExcercise().excerciseTypeId));
            } else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


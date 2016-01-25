package pl.com.inzynierka.mkufunzi.API.active_excercises;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

import pl.com.inzynierka.mkufunzi.API.ServerConnector;
import pl.com.inzynierka.mkufunzi.models.AppUser;
import pl.com.inzynierka.mkufunzi.models.Training;

/**
 * Created by impresyjna on 25.01.16.
 */
public class UpdateActiveExcerciseMobile extends AsyncTask<String, String, JSONObject> {

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
            params.put("protege_id",Integer.toString(appUser.getActiveExcercise().excerciseTypeId));
            params.put("how_many", Integer.toString(appUser.getActiveExcercise().howMany));
            params.put("puls", Integer.toString(appUser.getActiveExcercise().pulse));
            params.put("excercise_type_id", Integer.toString(appUser.getActiveExcercise().excerciseTypeId));
            params.put("training_id", Integer.toString(appUser.getActiveExcercise().trainingId));
            params.put("time", appUser.getActiveExcercise().time);

            Log.e("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getUPDATE_ACTIVE_EXCERCISE_MOBILE(), "POST", params);

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

    }
}

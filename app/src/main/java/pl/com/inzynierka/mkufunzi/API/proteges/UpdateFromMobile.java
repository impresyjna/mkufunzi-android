package pl.com.inzynierka.mkufunzi.API.proteges;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import pl.com.inzynierka.mkufunzi.API.ServerConnector;
import pl.com.inzynierka.mkufunzi.controllers.models_controllers.UsersController;
import pl.com.inzynierka.mkufunzi.controllers.views_controllers.MainActivity;

/**
 * Class used to update protege data on server. It uses AsyncTask to do connection inBackground
 */
public class UpdateFromMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();

    private ProgressDialog pDialog;

    private AppCompatActivity activity;

    /**
     * Setter for activity. Called by view controller which set himself as activity for this task
     * @param activity
     */
    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * Show dialog with message to wait
     */
    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Trwa aktualizowanie");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    /**
     * Method which connect with server and save data.
     * @param args args[0] - protege_id, args[1] - eye_color_id, args[2] - gender first letter,
     *             args[3] - blood_type_id, args[4] - birth_date
     * @return JSONObject with message - success if update was fine or failure if not
     */
    @Override
    protected JSONObject doInBackground(String... args) {
        try {

            HashMap<String, String> params = new HashMap<>();
            params.put("id",args[0]);
            params.put("eye_color_id",args[1]);
            params.put("gender",args[2]);
            params.put("blood_type_id",args[3]);
            params.put("birth_date",args[4]);

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getPOST_UPDATE_PROTEGE(), "POST", params);

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
     * Method called after closing the connection with server and receiving the message about status.
     * @param json - JSONObject from doInBackground method with information about status
     *             failure - shows ToastMessage with info about problem
     *             success - shows ToastMessage with info that data was saved 
     */
    protected void onPostExecute(JSONObject json) {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }

        try {
            if (json.getString("status").equals("success")) {
                Toast.makeText(activity, "Pomyślnie zaktualizowano", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Nastąpił problem. Proszę spróbować później", Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


package pl.com.inzynierka.mkufunzi.API.users;

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
import pl.com.inzynierka.mkufunzi.API.measurements.GetMainData;
import pl.com.inzynierka.mkufunzi.controllers.models_controllers.UsersController;
import pl.com.inzynierka.mkufunzi.controllers.views_controllers.Login;
import pl.com.inzynierka.mkufunzi.controllers.views_controllers.MainActivity;
import pl.com.inzynierka.mkufunzi.models.AppUser;

/**
 * Class used to check if user from device still exists. It extends AsyncTask.
 * This class connect with server and return results to UI.
 */
public class UserExistsMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();

    private ProgressDialog pDialog;

    private AppCompatActivity activity;
    private AppUser appUser = AppUser.getInstance();

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * Set dialog for user to show him to wait before starting connecting to server
     */
    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Trwa ładowanie");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    /**
     * This method connect with server to receive data.
     * @param args agrs[0] - email to check if user exists
     * @return JSONObject json with status(failure if user doesn't exist or success with data if exists)
     */
    @Override
    protected JSONObject doInBackground(String... args) {
        try {

            HashMap<String, String> params = new HashMap<>();
            params.put("email", args[0]);

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getUSER_EXISTS(), "GET", params);

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
     * Method called after doInBackgroud. It closes the dialog to wait for user.
     * This method is calling another class to get weight and height from server if succes
     * If failure this method log out the user.
     * @param json - JSONObject from doInBackground method
     */
    protected void onPostExecute(JSONObject json) {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        try {
            if (json.getString("status").equals("success")) {
                new UsersController().rememberAndLoginUser(json);
                Log.i("MainPage", "Opening main page activity ");
                GetMainData getMainData = new GetMainData();
                getMainData.setActivity(activity);
                getMainData.execute(Integer.toString(appUser.getUser().id));
            } else {
                new UsersController().clearUsers();
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

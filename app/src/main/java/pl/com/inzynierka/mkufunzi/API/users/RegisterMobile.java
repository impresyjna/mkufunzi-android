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
import pl.com.inzynierka.mkufunzi.controllers.models_controllers.UsersController;
import pl.com.inzynierka.mkufunzi.controllers.views_controllers.MainActivity;

/**
 * Class used to register user on server
 */
public class RegisterMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();

    private ProgressDialog pDialog;

    private AppCompatActivity activity;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Trwa aktualizowanie");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    /**
     * Method called to make connection with server
     * @param args args[0] - login, args[1] - name, args[2] - surname, args[3] - password
     *             args[4] - password_confirmation, args[5] - email
     * @return json with all information if success, failure message otherwise
     */
    @Override
    protected JSONObject doInBackground(String... args) {
        try {

            HashMap<String, String> params = new HashMap<>();
            params.put("login",args[0]);
            params.put("name",args[1]);
            params.put("surname",args[2]);
            params.put("password",args[3]);
            params.put("password_confirmation",args[4]);
            params.put("email",args[5]);

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getREGISTER(), "POST", params);

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
     * Method opens the MainActivity if success, ToastMessage otherwise 
     * @param json
     */
    protected void onPostExecute(JSONObject json) {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }

        try {
            if (json.getString("status").equals("success")) {
                new UsersController().rememberAndLoginUser(json);
                Log.i("MainPage", "Opening main page activity ");
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                Toast.makeText(activity, json.getString("message"), Toast.LENGTH_SHORT).show();
                activity.finish();
            } else {
                Toast.makeText(activity, json.getString("message"), Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

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
import pl.com.inzynierka.mkufunzi.models.User;

/**
 * Created by impresyjna on 27.12.15.
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
        pDialog.setMessage("Trwa rejestracja");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

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

    protected void onPostExecute(JSONObject json) {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }

        try {
            if (json.getString("status").equals("success")) {
                User user = new User(json.getJSONObject("user"));
                new UsersController().rememberAndLoginUser(user);
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

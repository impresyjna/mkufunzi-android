package pl.com.inzynierka.mkufunzi.API;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import pl.com.inzynierka.mkufunzi.controllers.About;
import pl.com.inzynierka.mkufunzi.controllers.MainPage;
import pl.com.inzynierka.mkufunzi.models.User;

/**
 * Created by impresyjna on 20.12.15.
 */
public class LoginMobile extends AsyncTask<String, String, JSONObject> {

    private ServerConnector serverConnector = ServerConnector.getInstance();

    private ProgressDialog pDialog;

    private AppCompatActivity activity;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Trwa logowanie");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... args) {
        try {

            HashMap<String, String> params = new HashMap<>();
            params.put("email", args[0]);
            params.put("password", args[1]);

            Log.d("request", "starting");

            JSONObject json = serverConnector.getJsonParser().makeHttpRequest(
                    serverConnector.getLOGIN(), "GET", params);

            if (json != null) {
                Log.d("JSON result", json.getJSONObject("user").toString());
                return json.getJSONObject("user");
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

        if (json.has("email") && !json.isNull("email")) {
            User user = new User();
            Log.i("MainPage", "Opening main page activity ");
            Intent intent = new Intent(activity, MainPage.class);
            activity.startActivity(intent);
            activity.finish();
        } else {
            Toast.makeText(activity,"Niepoprawne dane", Toast.LENGTH_SHORT).show();

        }
    }
}


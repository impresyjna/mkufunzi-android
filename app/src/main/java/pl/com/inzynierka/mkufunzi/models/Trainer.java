package pl.com.inzynierka.mkufunzi.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by impresyjna on 28.01.16.
 */
public class Trainer {
    public String trainerName;
    public String trainerSurname;


    private final static String TAG_NAME = "name";
    private final static String TAG_SURNAME = "surname";

    public Trainer(){

    }

    public Trainer(JSONObject json){
        try {
            this.trainerName = json.getString(TAG_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.trainerSurname = json.getString(TAG_SURNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

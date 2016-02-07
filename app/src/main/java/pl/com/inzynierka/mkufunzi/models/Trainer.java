package pl.com.inzynierka.mkufunzi.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class describint the Trainer model for android App
 */
public class Trainer {
    /** Field with trainer name */
    public String trainerName;
    /** Field with trainer surname */
    public String trainerSurname;

    /** Tag from server with name of trainer */
    private final static String TAG_NAME = "name";
    /** Tag from server with surname of trainer */
    private final static String TAG_SURNAME = "surname";

    /** No params constructor */
    public Trainer(){

    }

    /** Constructor called when it is necessary to create object using data from server */
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

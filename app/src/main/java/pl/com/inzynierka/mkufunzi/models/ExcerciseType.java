package pl.com.inzynierka.mkufunzi.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by impresyjna on 29.01.16.
 */
public class ExcerciseType {

    public int id;
    public String name;
    public String formula;

    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_FORMULA = "formula";

    public ExcerciseType(){

    }

    public ExcerciseType(JSONObject json){
        try {
            this.id = json.getInt(TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.name = json.getString(TAG_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.formula = json.getString(TAG_FORMULA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

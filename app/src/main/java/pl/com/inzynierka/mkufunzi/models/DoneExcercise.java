package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by impresyjna on 06.02.16.
 */
public class DoneExcercise {
    public int id;
    public String excerciseName;
    public int pulse;
    public int howMany;
    public String time;

    private static final String TAG_ID = "id";
    private static final String TAG_EXCERCISE_ID = "excercise_type_id";
    private static final String TAG_HOW_MANY = "how_many";
    private static final String TAG_TIME = "time";
    private static final String TAG_PULS = "puls";

    public DoneExcercise(){

    }

    public DoneExcercise(JSONObject json){
        try {
            this.id = json.getInt(TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int excerciseTypeId;
        try {
            excerciseTypeId = json.getInt(TAG_EXCERCISE_ID);
            ExcerciseType excerciseType = new Select().from(ExcerciseType.class).where("ref_id = ?", excerciseTypeId).executeSingle();
            this.excerciseName = excerciseType.name;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.pulse = json.getInt(TAG_PULS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.howMany = json.getInt(TAG_HOW_MANY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.time = json.getString(TAG_TIME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

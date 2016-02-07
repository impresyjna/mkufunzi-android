package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class describing DoneExcercise model from database
 */
public class DoneExcercise {
    /** Field with id from server */
    public int id;
    /** Field with name of excercise to make easier to show information in activities */
    public String excerciseName;
    /** Field with user pulse after excercise */
    public int pulse;
    /** Field with how many repeats of excercise */
    public int howMany;
    /** Field with duration of excercise */
    public String time;

    /** Tag with id from server */
    private static final String TAG_ID = "id";
    /** Tag with excercise id from server */
    private static final String TAG_EXCERCISE_ID = "excercise_type_id";
    /** Tag with repeats from server */
    private static final String TAG_HOW_MANY = "how_many";
    /** Tag with duration of training from server */
    private static final String TAG_TIME = "time";
    /** Tag with pulse value from server */
    private static final String TAG_PULS = "puls";

    /** No params constructor */
    public DoneExcercise(){

    }

    /** Constructor called when object has to be created using data from server */
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

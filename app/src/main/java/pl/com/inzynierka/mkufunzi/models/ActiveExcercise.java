package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class describing active excercise which user is doing. Compatible with server model
 */
@Table(name="active_excercises")
public class ActiveExcercise {
    /** Field with user id from server */
    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    /** Field with how many repeats user does sth  */
    @Column(name = "how_many")
    public int howMany;
    /** Field with id of excerciseType */
    @Column(name = "excercise_type_id")
    public int excerciseTypeId;
    /** Field with trainingId */
    @Column(name = "training_id")
    public int trainingId;
    /** Field with duration of excercise */
    @Column(name = "time")
    public String time;
    /** Field with protegeId */
    @Column(name = "protege_id")
    public int protegeId;

    /** Tag with active excercise id on server */
    private static final String TAG_ID = "id";
    /** Tag with how many on server */
    private static final String TAG_HOW_MANY = "how_many";
    /** Tag with excercise type id on server */
    private static final String TAG_EXCERCISE_TYPE_ID ="excercise_type_id";
    /** Tag with training id on server */
    private static final String TAG_TRAINING_ID = "training_id";
    /** Tag with duration of training on server */
    private static final String TAG_TIME = "time";
    /** Tag with protege id on server */
    private static final String TAG_PROTEGE_ID = "protege_id";

    /**
     * No parameters constructor
     */
    public ActiveExcercise(){
        super();
    }

    /**
     * Constructor called when object has to be created using data from server
     * @param json - json object with data from server
     */
    public ActiveExcercise(JSONObject json){
        super();
        try {
            this.id = json.getInt(TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.howMany = json.getInt(TAG_HOW_MANY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.excerciseTypeId = json.getInt(TAG_EXCERCISE_TYPE_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.trainingId = json.getInt(TAG_TRAINING_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.time = json.getString(TAG_TIME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.protegeId = json.getInt(TAG_PROTEGE_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

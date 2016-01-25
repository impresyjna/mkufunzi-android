package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class describing Training model compatible with model on server
 */
@Table(name = "Trainings")
public class Training {
    /** Field with user id from server */
    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    /** Field with protegeId */
    @Column(name = "protege_id")
    public int protegeId;
    /** Field with start of training */
    @Column(name = "start")
    public String start;
    /** Field with end of training */
    @Column(name = "end")
    public String end;
    /** Field with comment if user want to save some information */
    @Column(name = "comment")
    public String comment;


    /** Tag with user id on server */
    private static final String TAG_ID = "id";
    /** Tag with protegeId on server */
    private static final String TAG_PROTEGE_ID = "protege_id";
    /** Tag with start time on server */
    private static final String TAG_START = "start";
    /** Tag with end time on server */
    private static final String TAG_END = "end";
    /** Tag with user surname on server */
    private static final String TAG_COMMENT = "comment";

    /**
     * No parameters constructor
     */
    public Training(){
        super();
    }

    /**
     * Constructor called when it is necessary to create object using data from server
     * @param json - data in json from server
     */
    public Training(JSONObject json){
        try {
            this.id = json.getInt(TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.protegeId = json.getInt(TAG_PROTEGE_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.start = json.getString(TAG_START);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.end = json.getString(TAG_END);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.comment = json.getString(TAG_COMMENT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

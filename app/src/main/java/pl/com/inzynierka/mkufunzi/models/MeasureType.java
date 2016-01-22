package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class describing MeasureType object, compatible with server model. Extends ActiveAndroid Model to make easier connection
 * between sqlite3 database on device and Java object
 */
@Table(name="measure_types")
public class MeasureType extends Model{
    /** Field with id from server */
    @Column(name = "measure_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    /** Field with measureType name */
    @Column(name = "name")
    public String name;
    /** Field with measureType unit */
    @Column(name = "unit")
    public String unit;
    /** Field which is true if measureType need extra field for value */
    @Column(name = "extra_field")
    public boolean extraField;
    /** Field with label for first value */
    @Column(name = "first_label")
    public String firstLabel;
    /** Field with label for second value */
    @Column(name = "second_label")
    public String secondLabel;

    /** Tag on server with measureType id */
    private static final String TAG_ID = "id";
    /** Tag on server with measureType name */
    private static final String TAG_NAME = "name";
    /** Tag on server with measureType unit */
    private static final String TAG_UNIT = "unit";
    /** Tag on server with value if extraField */
    private static final String TAG_EXTRA_FIELD = "extra_field";
    /** Tag on server with firstLabel */
    private static final String TAG_FIRST_LABEL = "first_label";
    /** Tag on server with secondLabel */
    private static final String TAG_SECOND_LABEL = "second_label";

    /**
     * No parameters constructor
     */
    public MeasureType(){
        super();
    }

    /**
     * Constructor called when object is created using data from server. Tags describe fields in json with values in Java object 
     * @param json
     */
    public MeasureType(JSONObject json){
        super();
        try {
            this.id = json.getInt(TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.name = json.getString(TAG_NAME).toLowerCase();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.unit = json.getString(TAG_UNIT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.extraField = json.getBoolean(TAG_EXTRA_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.firstLabel = json.getString(TAG_FIRST_LABEL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.secondLabel = json.getString(TAG_SECOND_LABEL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

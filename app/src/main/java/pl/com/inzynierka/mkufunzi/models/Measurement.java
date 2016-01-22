package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Class describing Measurement object. It uses ActiveAndroid Model and can be saved in sqlite3 database on device
 */
@Table(name="measurements")
public class Measurement extends Model {
    /** Field saving information about id on server */
    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    /** Field saving value of measurement */
    @Column(name = "value")
    public double value;
    /** Field saving information about measureTypeID */
    @Column(name = "measure_type_id")
    public int measureTypeId;
    /** Field saving information about belonging to card(cardId) */
    @Column(name = "card_id")
    public int cardId;
    /** Field saving information about time when measurement was saved */
    @Column(name = "time")
    public Date time;
    /** Extra field for measurements with two values to be completed for example: blood pressure  */
    @Column(name = "second_value")
    public double secondValue;

    /** Tag on server with id */
    private static final String TAG_ID = "id";
    /** Tag on server with value */
    private static final String TAG_VALUE = "value";
    /** Tag on server with measureTypeId */
    private static final String TAG_MEASURE_TYPE_ID = "measure_type_id";
    /** Tag on server with cardId */
    private static final String TAG_CARD_ID = "card_id";
    /** Tag on server with time */
    private static final String TAG_TIME = "created_at";
    /** Tag on server with extra secondValue */
    private static final String TAG_SECOND_VALUE = "second_value";

    /**
     * No parameters constructor
     * */
    public Measurement() {
        super();
    }

    /**
     * Constructor used when object is created from json
     * @param json - JSONObject with fields to save in new Measurement object
     *             It uses the tags to find values and try to save it in object fields 
     */
    public Measurement(JSONObject json){
        super();
        try {
            this.id = json.getInt(TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.value = json.getDouble(TAG_VALUE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.measureTypeId = json.getInt(TAG_MEASURE_TYPE_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.cardId = json.getInt(TAG_CARD_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            this.time = format.parse(json.getString(TAG_TIME));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.secondValue = json.getDouble(TAG_SECOND_VALUE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

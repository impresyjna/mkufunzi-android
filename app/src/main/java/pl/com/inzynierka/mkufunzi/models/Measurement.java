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
 * Created by impresyjna on 03.01.16.
 */

@Table(name="measurements")
public class Measurement extends Model {
    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    @Column(name = "value")
    public double value;
    @Column(name = "measure_type_id")
    public int measureTypeId;
    @Column(name = "card_id")
    public int cardId;
    @Column(name = "time")
    public Date time;
    @Column(name = "second_value")
    public double secondValue;

    private static final String TAG_ID = "id";
    private static final String TAG_VALUE = "value";
    private static final String TAG_MEASURE_TYPE_ID = "measure_type_id";
    private static final String TAG_CARD_ID = "card_id";
    private static final String TAG_TIME = "created_at";
    private static final String TAG_SECOND_VALUE = "second_value";

    public Measurement() {
        super();
    }

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

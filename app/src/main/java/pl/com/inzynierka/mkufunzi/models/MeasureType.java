package pl.com.inzynierka.mkufunzi.models;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by impresyjna on 01.01.16.
 */
@Table(name="measure_types")
public class MeasureType extends Model{
    @Column(name = "measure_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    @Column(name = "name")
    public String name;
    @Column(name = "unit")
    public String unit;
    @Column(name = "extra_field")
    public boolean extraField;
    @Column(name = "first_label")
    public String firstLabel;
    @Column(name = "second_label")
    public String secondLabel;

    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_UNIT = "unit";
    private static final String TAG_EXTRA_FIELD = "extra_field";
    private static final String TAG_FIRST_LABEL = "first_label";
    private static final String TAG_SECOND_LABEL = "second_label";

    public MeasureType(){
        super();
    }

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

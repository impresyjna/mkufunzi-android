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

    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_UNIT = "unit";

    public MeasureType(){
        super();
    }

    public MeasureType(JSONObject json){
        super();
        try {
            this.id = json.getInt(TAG_ID);
            this.name = json.getString(TAG_NAME);
            this.unit = json.getString(TAG_UNIT);
        } catch (JSONException e) {
            Log.e("MeasureTypeConstructor", "JSONError");
            e.printStackTrace();
        }
    }
}

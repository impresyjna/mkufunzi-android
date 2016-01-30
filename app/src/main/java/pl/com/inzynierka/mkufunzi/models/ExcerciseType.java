package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by impresyjna on 29.01.16.
 */
@Table(name="excercise_types")
public class ExcerciseType extends Model{

    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    @Column(name = "name")
    public String name;
    @Column(name = "formula")
    public String formula;

    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_FORMULA = "formula";

    public ExcerciseType(){
        super();
    }

    public ExcerciseType(JSONObject json){
        super(); 
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

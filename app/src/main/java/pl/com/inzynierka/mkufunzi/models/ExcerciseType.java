package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class describing ExcerciseType model from server. Also extends ActiveAndroid model to allow to easy save data in sqlite3 database on device
 */
@Table(name="excercise_types")
public class ExcerciseType extends Model{

    /** Field with id of excerciseType from server */
    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    /** Field with excerciseType name */
    @Column(name = "name")
    public String name;
    /** Field with description about excerciseType */
    @Column(name = "formula")
    public String formula;

    /** Tag from server with id in database */
    private static final String TAG_ID = "id";
    /** Tag from server with name */
    private static final String TAG_NAME = "name";
    /** Tag from server with description about excercise */
    private static final String TAG_FORMULA = "formula";

    /** No params constructor */
    public ExcerciseType(){
        super();
    }

    /** Constructor called when ExcerciseType object has to be created using data from server */
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

package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class describing bloodType object, compatible with server model. Extends ActiveAndroid Model to make easier
 * connect sqlite3 database on device and Java object
 */
@Table(name="blood_types")
public class BloodType extends Model {

    /** Field with id for bloodType on server */
    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    /** Field with name for bloodType */
    @Column(name = "name")
    public String name;

    /** Tag from server with id */
    private static String TAG_ID = "id";
    /** Tag from server with name */
    private static String TAG_NAME = "name";

    /**
     * No parameters constructor
     */
    public BloodType() {
        super();
    }

    /**
     * Constructor for the class
     * @param id
     * @param name
     */
    public BloodType(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    /**
     * Constructor used when object has to be created using data from server
     * @param json
     */
    public BloodType(JSONObject json) {
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
    }

    /**
     * Method necessary for Spinner in ProtegeData activity.
     * @return name of bloodType when BloodType.toString() is called.
     */
    public String toString(){
        return name;
    }
}

package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class describing eyeColor model compatible with model on server. Extends ActiveAndroid model to make easier to connect to sqlite3 database on
 * device and Java object.
 */
@Table(name="eye_colors")
public class EyeColor extends Model{

    /** Field with id from server */
    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    /** Field with eyeColor name */
    @Column(name = "name")
    public String name;
    /** Field with string value of eyeColor */
    @Column(name = "color")
    public String color;

    /** Tag from server with id */
    private static String TAG_ID = "id";
    /** Tag from server with name */
    private static String TAG_NAME = "name";
    /** Tag from server with color */
    private static String TAG_COLOR = "color";

    /**
     * No parameters constructor
     */
    public EyeColor() {
        super();
    }

    /**
     * Constructor
     * @param color
     * @param id
     * @param name
     */
    public EyeColor(String color, int id, String name) {
        super();
        this.color = color;
        this.id = id;
        this.name = name;
    }

    /**
     * Constructor called when object has to be created using data from server. Tags describes which field from json should be
     * saved in field in Java object
     * @param json
     */
    public EyeColor(JSONObject json) {
        super();
        try {
            this.color = json.getString(TAG_COLOR);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
     * Method necessary for Spinner in ProtegeData
     * @return name of eyeColor on call EyeColor.toString()
     */
    public String toString() {
        return name;
    }
}

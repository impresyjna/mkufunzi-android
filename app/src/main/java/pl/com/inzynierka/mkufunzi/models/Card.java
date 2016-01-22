package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class describing card model from server on device. Extends ActiveAndroid Model to make easier connection between
 * sqlite3 database on device and Java object
 */
@Table(name="cards")
public class Card extends Model {
    /** Field with data about cardId on server */
    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    /** Field with information about whose card is this(ProtegeId) */
    @Column(name = "protege_id")
    public int protegeId;

    /** Tag on server with id */
    private static final String TAG_ID = "id";
    /** Tag on server with ProtegeId */
    private static final String TAG_PROTEGE_ID = "protege_id";

    /**
     * No parameters constructor
     */
    public Card() {
        super();
    }

    /**
     * Constructor called when object has to be created using data from server.
     * Tags describe which field from json should be saved in field in object 
     * @param json
     */
    public Card(JSONObject json){
        super();
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
    }
}

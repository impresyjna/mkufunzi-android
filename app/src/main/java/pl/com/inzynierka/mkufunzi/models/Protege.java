package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class describing Protege object compatible with Protege model from server.
 * Extends ActivAndroid Model to make easier to connect to sqlite3 database on device and Java object.
 */
@Table(name="proteges")
public class Protege extends Model {
    /** Field with protege id from server*/
    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    /** Field with userId that is the owner of this protege*/
    @Column(name = "user_id")
    public int userId;
    /** Field with trainerId that is connected with this protege */
    @Column(name = "trainer_id")
    public int trainerId;
    /** Field with bloodTypeId of the protege */
    @Column(name = "blood_type_id")
    public int bloodType;
    /** Field with first letter of gender */
    @Column(name = "gender")
    public String gender;
    /** Field with eyeColorId of the protege */
    @Column(name = "eye_color_id")
    public int eyeColor;
    /** Field with birthDate */
    @Column(name = "birth_date")
    public String birthDate;

    /** Tag from server with id */
    private static final String TAG_ID = "id";
    /** Tag from server with userId */
    private static final String TAG_USER_ID = "user_id";
    /** Tag from server with trainerId */
    private static final String TAG_TRAINER_ID = "trainer_id";
    /** Tag from server with bloodTypeId */
    private static final String TAG_BLOOD_TYPE = "blood_type_id";
    /** Tag from server with first letter of protege gender */
    private static final String TAG_GENDER = "gender";
    /** Tag from server with eyeColorId */
    private static final String TAG_EYE_COLOR = "eye_color_id";
    /** Tag from server with birthDate */
    private static final String TAG_BIRTH_DATE = "birth_date";

    /**
     * No parameters constructor
     */
    public Protege() {
        super();
    }

    /**
     * Constructor called when object has to be created using data from server. Tags describe which field belongs to which tag
     * @param json
     */
    public Protege(JSONObject json) {
        super();
        try {
            this.bloodType = json.getInt(TAG_BLOOD_TYPE);
        } catch (JSONException e) {
            this.bloodType = 0;
            e.printStackTrace();
        }
        try {
            this.eyeColor = json.getInt(TAG_EYE_COLOR);
        } catch (JSONException e) {
            this.eyeColor = 0;
            e.printStackTrace();
        }
        try {
            this.gender = json.getString(TAG_GENDER);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.id = json.getInt(TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.trainerId = json.getInt(TAG_TRAINER_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.userId = json.getInt(TAG_USER_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.birthDate = json.getString(TAG_BIRTH_DATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

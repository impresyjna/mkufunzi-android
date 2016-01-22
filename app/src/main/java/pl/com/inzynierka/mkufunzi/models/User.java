package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class describing User object. Extends ActiveAndroid Model to allow easy save on sqlite3 database on device
 */
@Table(name = "Users")
public class User extends Model{

    /** Field with user id from server */
    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    /** Field with user login */
    @Column(name = "login")
    public String login;
    /** Field with user email */
    @Column(name = "email")
    public String email;
    /** Field with user name */
    @Column(name = "name")
    public String name;
    /** Field with user surname */
    @Column(name = "surname")
    public String surname;

    /** Tag with user id on server */
    private static final String TAG_ID = "id";
    /** Tag with user login on server */
    private static final String TAG_LOGIN = "login";
    /** Tag with user email on server */
    private static final String TAG_EMAIL = "email";
    /** Tag with user name on server */
    private static final String TAG_NAME = "name";
    /** Tag with user surname on server */
    private static final String TAG_SURNAME = "surname";

    /**
     * No parameters constructor
     */
    public User() {
        super();
    }

    /**
     * Constructor used when user has to be created using data from server
     * @param json - JSONObject with values from server. It uses tags to connect data from server with object fields 
     */
    public User(JSONObject json) {
        super();
        try {
            this.id = json.getInt(TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.login = json.getString(TAG_LOGIN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.email = json.getString(TAG_EMAIL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.name = json.getString(TAG_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.surname = json.getString(TAG_SURNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

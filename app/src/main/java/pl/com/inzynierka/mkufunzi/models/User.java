package pl.com.inzynierka.mkufunzi.models;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by impresyjna on 16.12.15.
 */
@Table(name = "Users")
public class User extends Model{

    @Column(name = "id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    @Column(name = "login")
    public String login;
    @Column(name = "email")
    public String email;
    @Column(name = "name")
    public String name;
    @Column(name = "surname")
    public String surname;

    private static final String TAG_ID = "id";
    private static final String TAG_LOGIN = "login";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_NAME = "name";
    private static final String TAG_SURNAME = "surname";

    public User() {
        super();
    }

    public User(JSONObject json) {
        super();
        try {
            this.id = json.getInt(TAG_ID);
            this.login = json.getString(TAG_LOGIN);
            this.email = json.getString(TAG_EMAIL);
            this.name = json.getString(TAG_NAME);
            this.surname = json.getString(TAG_SURNAME);
        } catch (JSONException e) {
            Log.e("UserConstructor", "JSONErrror");
            e.printStackTrace();
        }
    }
}

package pl.com.inzynierka.mkufunzi.models;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by impresyjna on 03.01.16.
 */

@Table(name="proteges")
public class Protege extends Model {
    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    @Column(name = "user_id")
    public int userId;
    @Column(name = "trainer_id")
    public int trainerId;
    @Column(name = "blood_type")
    public String bloodType;
    @Column(name = "gender")
    public String gender;
    @Column(name = "eye_color")
    public String eyeColor;
    @Column(name = "birth_date")
    public String birthDate;

    private static final String TAG_ID = "id";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_TRAINER_ID = "trainer_id";
    private static final String TAG_BLOOD_TYPE = "blood_type";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_EYE_COLOR = "eye_color";
    private static final String TAG_BIRTH_DATE = "birth_date";

    public Protege() {
        super();
    }

    public Protege(JSONObject json) {
        super();
        try {
            this.bloodType = json.getString(TAG_BLOOD_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.eyeColor = json.getString(TAG_EYE_COLOR);
        } catch (JSONException e) {
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

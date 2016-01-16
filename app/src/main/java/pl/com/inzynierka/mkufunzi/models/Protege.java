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
    @Column(name = "blood_type_id")
    public int bloodType;
    @Column(name = "gender")
    public String gender;
    @Column(name = "eye_color_id")
    public int eyeColor;
    @Column(name = "birth_date")
    public String birthDate;

    private static final String TAG_ID = "id";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_TRAINER_ID = "trainer_id";
    private static final String TAG_BLOOD_TYPE = "blood_type_id";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_EYE_COLOR = "eye_color_id";
    private static final String TAG_BIRTH_DATE = "birth_date";

    public Protege() {
        super();
    }

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

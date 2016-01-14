package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by impresyjna on 14.01.16.
 */
@Table(name="blood_types")
public class BloodType extends Model {

    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    @Column(name = "name")
    public String name;

    private static String TAG_ID = "id";
    private static String TAG_NAME = "name";

    public BloodType() {
        super();
    }

    public BloodType(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

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

    public String toString(){
        return name;
    }
}

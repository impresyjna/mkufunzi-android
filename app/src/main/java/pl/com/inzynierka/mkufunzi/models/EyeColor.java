package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by impresyjna on 14.01.16.
 */
@Table(name="eye_colors")
public class EyeColor extends Model{

    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    @Column(name = "name")
    public String name;
    @Column(name = "color")
    public String color;

    private static String TAG_ID = "id";
    private static String TAG_NAME = "name";
    private static String TAG_COLOR = "color";

    public EyeColor() {
        super();
    }

    public EyeColor(String color, int id, String name) {
        super();
        this.color = color;
        this.id = id;
        this.name = name;
    }

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

    public String toString() {
        return name;
    }
}

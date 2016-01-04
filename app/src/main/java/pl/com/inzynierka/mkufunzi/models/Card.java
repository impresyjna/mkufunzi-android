package pl.com.inzynierka.mkufunzi.models;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by impresyjna on 03.01.16.
 */
@Table(name="cards")
public class Card extends Model {
    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    @Column(name = "protege_id")
    public int protegeId;

    private static final String TAG_ID = "id";
    private static final String TAG_PROTEGE_ID = "protege_id";

    public Card() {
        super();
    }

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

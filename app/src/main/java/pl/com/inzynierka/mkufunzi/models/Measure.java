package pl.com.inzynierka.mkufunzi.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by impresyjna on 03.01.16.
 */

@Table(name="measurements")
public class Measure extends Model {
    @Column(name = "ref_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;
    @Column(name = "value")
    public long value;
    @Column(name = "measure_type_id")
    public int measureTypeId;
    @Column(name = "card_id")
    public int cardId;

    private static final String TAG_ID = "id";
    private static final String TAG_VALUE = "value";
    private static final String TAG_MEASURE_TYPE_ID = "measure_type_id";
    private static final String TAG_CARD_ID = "card_id";

    public Measure() {
        super();
    }

    public Measure(JSONObject json){
        super();
        try {
            this.id = json.getInt(TAG_ID);
            this.value = json.getLong(TAG_VALUE);
            this.measureTypeId = json.getInt(TAG_MEASURE_TYPE_ID);
            this.cardId = json.getInt(TAG_CARD_ID); 
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

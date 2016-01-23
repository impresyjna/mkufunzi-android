package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.MeasureType;

/**
 * Class which is controller for MeasureType objects
 */
public class MeasureTypesController {

    /**
     * This method is used to get array of MeasureType objects from server
     * and deserialize it to ArrayList
     * @param jsonObject This is the object from server
     * @return ArrayList of MeasureType objects
     */
    public List<MeasureType> getArrayFromJSON(JSONObject jsonObject) {
        List<MeasureType> measure_types = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = jsonObject.getJSONArray("measure_types");
            for (int i = 0; i < jsonArray.length(); i++) {
                measure_types.add(new MeasureType(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return measure_types;
    }

    /**
     * Method used to get single MeasureType object from device database
     * @param name name of MeasureType
     * @return MeasureType object or null if object doesn't exist in database
     */
    public MeasureType getMeasureType(String name) {
        return new Select()
                .from(MeasureType.class)
                .where("name = ?", name)
                .executeSingle();
    }
}

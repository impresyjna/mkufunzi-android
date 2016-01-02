package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.MeasureType;

/**
 * Created by impresyjna on 01.01.16.
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
}

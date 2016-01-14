package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.BloodType;

/**
 * Created by impresyjna on 14.01.16.
 */
public class BloodTypesController {

    /**
     * This method is used to get array of BloodType objects from server
     * and deserialize it to ArrayList
     *
     * @param jsonObject This is the object from server
     * @return ArrayList of BloodType objects
     */
    public List<BloodType> getArrayFromJSON(JSONObject jsonObject) {
        List<BloodType> bloodTypes = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = jsonObject.getJSONArray("blood_types");
            for (int i = 0; i < jsonArray.length(); i++) {
                bloodTypes.add(new BloodType(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bloodTypes;
    }
}
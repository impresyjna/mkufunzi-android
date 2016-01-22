package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.Measurement;

/**
 * Class used to control Measurements type objects
 */
public class MeasurementsController {

    /**
     * This method is used to get array of Measurement objects from server
     * and deserialize it to ArrayList
     * @param jsonObject This is the object from server
     * @return ArrayList of Measurement objects
     */
    public List<Measurement> getArrayFromJSON(JSONObject jsonObject) {
        List<Measurement> measurements = new ArrayList<>();
        JSONArray jsonArray;
        try {
            jsonArray = jsonObject.getJSONArray("measurements");
            for (int i = 0; i < jsonArray.length(); i++) {
                measurements.add(new Measurement(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return measurements;
    }
}

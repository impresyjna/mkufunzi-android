package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.ExcerciseType;

/**
 * Class with methods used to controll ExcerciseType objects in android app
 */
public class ExcerciseTypesController {

    /**
     * Method called to generate list of ExcerciseType objects using data from server
     * @param jsonObject json with data from server
     * @return ArrayList of ExcerciseType objects
     */
    public List<ExcerciseType> getArrayFromJSON(JSONObject jsonObject) {
        List<ExcerciseType> excerciseTypes = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = jsonObject.getJSONArray("excercise_types");
            for (int i = 0; i < jsonArray.length(); i++) {
                ExcerciseType excerciseType = new ExcerciseType(jsonArray.getJSONObject(i));
                excerciseTypes.add(excerciseType);
                excerciseType.save();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return excerciseTypes;
    }
}

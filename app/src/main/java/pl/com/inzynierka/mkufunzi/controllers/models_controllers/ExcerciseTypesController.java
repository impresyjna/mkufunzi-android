package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.ExcerciseType;

/**
 * Created by impresyjna on 29.01.16.
 */
public class ExcerciseTypesController {

    public List<ExcerciseType> getArrayFromJSON(JSONObject jsonObject) {
        List<ExcerciseType> excerciseTypes = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = jsonObject.getJSONArray("excercise_types");
            for (int i = 0; i < jsonArray.length(); i++) {
                excerciseTypes.add(new ExcerciseType(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return excerciseTypes;
    }
}

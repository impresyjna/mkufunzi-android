package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.DoneExcercise;
import pl.com.inzynierka.mkufunzi.models.EyeColor;

/**
 * Class using to control DoneExcercise objects in android app
 */
public class DoneExcercisesController {
    /**
     * Method called to generate list of DoneExcercise objects using data from server
     * @param jsonObject json with data from server
     * @return ArrayList of DoneExcercise objects
     */
    public List<DoneExcercise> getArrayFromJSON(JSONObject jsonObject) {
        List<DoneExcercise> doneExcercises = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = jsonObject.getJSONArray("done_excercises");
            for (int i = 0; i < jsonArray.length(); i++) {
                doneExcercises.add(new DoneExcercise(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return doneExcercises;
    }
}

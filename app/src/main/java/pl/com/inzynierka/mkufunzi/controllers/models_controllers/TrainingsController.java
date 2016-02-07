package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import com.activeandroid.ActiveAndroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.Training;

/**
 * Class used to control Training objects in application
 */
public class TrainingsController {

    /**
     * Method called to get list of training object using data from server to create this objects
     * @param jsonObject  json with informations from server
     * @return ArrayList of training objects
     */
    public List<Training> getArrayFromJSON(JSONObject jsonObject) {
        List<Training> trainings = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = jsonObject.getJSONArray("trainings");
            for (int i = 0; i < jsonArray.length(); i++) {
                trainings.add(new Training(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trainings;
    }

    /**
     * Method used to clear all informations from sqlite3 database on device
     */
    public void clearTrainings() {
        ActiveAndroid.execSQL("delete from trainings");
    }
}

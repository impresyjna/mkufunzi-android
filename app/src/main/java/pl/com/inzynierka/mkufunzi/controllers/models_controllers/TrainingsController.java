package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import com.activeandroid.ActiveAndroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.Training;

/**
 * Created by impresyjna on 04.02.16.
 */
public class TrainingsController {

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


    public void clearTrainings() {
        ActiveAndroid.execSQL("delete from trainings");
    }
}

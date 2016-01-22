package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.EyeColor;

public class EyeColorsController {

    /**
     * This method is used to get array of EyeColor objects from server
     * and deserialize it to ArrayList
     *
     * @param jsonObject This is the object from server
     * @return ArrayList of BloodType objects
     */
    public List<EyeColor> getArrayFromJSON(JSONObject jsonObject) {
        List<EyeColor> eyeColors = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = jsonObject.getJSONArray("eye_colors");
            for (int i = 0; i < jsonArray.length(); i++) {
                eyeColors.add(new EyeColor(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eyeColors;
    }
}

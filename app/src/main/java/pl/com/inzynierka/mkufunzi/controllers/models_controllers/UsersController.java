package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import com.activeandroid.ActiveAndroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.User;

/**
 * Created by impresyjna on 29.12.15.
 */
public class UsersController {

    /**
     * This method is used to get array of User objects from server
     * and deserialize it to ArrayList
     * @param jsonObject This is the object from server
     * @return ArrayList of User objects
     */
    public List<User> getArrayFromJSON(JSONObject jsonObject) {
        List<User> users = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = jsonObject.getJSONArray("users");
            for (int i = 0; i < jsonArray.length(); i++) {
                users.add(new User(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void clearUsers(){
        ActiveAndroid.execSQL("delete from users");
    }
}

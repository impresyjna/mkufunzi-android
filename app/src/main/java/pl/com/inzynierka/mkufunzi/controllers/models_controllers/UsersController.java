package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import com.activeandroid.ActiveAndroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.Card;
import pl.com.inzynierka.mkufunzi.models.Protege;
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

    public void rememberAndLoginUser(JSONObject json)
    {
        User user = null;
        Protege protege = null;
        Card card = null;
        try {
            user = new User(json.getJSONObject("user"));
            protege = new Protege(json.getJSONObject("protege"));
            card = new Card(json.getJSONObject("card"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        clearUsers();
        new CardsController().clearCards();
        new ProtegesController().clearProteges();
        user.save();
        protege.save();
        card.save(); 
    }
}

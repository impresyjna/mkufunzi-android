package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import com.activeandroid.ActiveAndroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.AppUser;
import pl.com.inzynierka.mkufunzi.models.Card;
import pl.com.inzynierka.mkufunzi.models.Protege;
import pl.com.inzynierka.mkufunzi.models.User;
import pl.com.inzynierka.mkufunzi.models.WHBMI;

/**
 * Class used to control User objects in application
 */
public class UsersController {

    AppUser appUser = AppUser.getInstance();

    /**
     * This method is used to get array of User objects from server
     * and deserialize it to ArrayList
     *
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

    /**
     * Method used to clear all User objects from sqlite3 database
     */
    public void clearUsers() {
        ActiveAndroid.execSQL("delete from users");
    }

    /**
     * Method used to remember user on device and login him or her on device
     * @param json
     */
    public void rememberAndLoginUser(JSONObject json) {
        User user = null;
        Protege protege = null;
        Card card = null;
        try {
            user = new User(json.getJSONObject("user"));
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        try {
            protege = new Protege(json.getJSONObject("protege"));
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        try {
            card = new Card(json.getJSONObject("card"));
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        clearUsers();
        new CardsController().clearCards();
        new ProtegesController().clearProteges();
        user.save();
        protege.save();
        card.save();
        appUser.setUser(user);
        appUser.setCard(card);
        appUser.setProtege(protege);
    }

    /**
     * Method used to save received data from server to WHBMI instance in AppUser
     * @param json
     */
    public void getMainData(JSONObject json) {
        WHBMI whbmi = new WHBMI(json);
        appUser.setWhbmi(whbmi);
    }
}

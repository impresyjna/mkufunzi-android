package pl.com.inzynierka.mkufunzi.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by impresyjna on 16.12.15.
 */
public class User {
    private int id;
    private String login;
    private String email;
    private String name;
    private String surname;

    private static final String TAG_ID = "id";
    private static final String TAG_LOGIN = "login";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_NAME = "name";
    private static final String TAG_SURNAME = "surname";

    public User() {

    }

    public User(JSONObject json) {
        try {
            id = json.getInt(TAG_ID);
            login = json.getString(TAG_LOGIN);
            email = json.getString(TAG_EMAIL);
            name = json.getString(TAG_NAME);
            surname = json.getString(TAG_SURNAME);
        } catch (JSONException e) {
            Log.e("UserConstructor", "JSONErrror");
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

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
}

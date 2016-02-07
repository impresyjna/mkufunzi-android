package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.Message;

/**
 * Class used to control Message objects in android app
 */
public class MessagesController {

    /**
     * Method used to generate list of message objects using data from server
     * @param jsonObject json with data from server to create message objects
     * @return ArrayList of message objects
     */
    public List<Message> getArrayFromJSON(JSONObject jsonObject) {
        List<Message> messages = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = jsonObject.getJSONArray("messages");
            for (int i = 0; i < jsonArray.length(); i++) {
                messages.add(new Message(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return messages;
    }
}

package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.models.Message;

/**
 * Created by impresyjna on 27.01.16.
 */
public class MessagesController {

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

package pl.com.inzynierka.mkufunzi.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by impresyjna on 27.01.16.
 */
public class Message {
    public int id;
    public int userSendId;
    public int userReceiveId;
    public String content;

    private final static String TAG_ID = "id";
    private final static String TAG_USER_SEND_ID = "user_send_id";
    private final static String TAG_USER_RECEIVE_ID = "user_receive_id";
    private final static String TAG_CONTENT = "content";

    public Message(){

    }

    public Message(JSONObject json){
        try {
            this.id = json.getInt(TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.userReceiveId = json.getInt(TAG_USER_RECEIVE_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.userSendId = json.getInt(TAG_USER_SEND_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.content = json.getString(TAG_CONTENT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

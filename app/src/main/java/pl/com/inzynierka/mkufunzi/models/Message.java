package pl.com.inzynierka.mkufunzi.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by impresyjna on 27.01.16.
 */
public class Message {
    public int id;
    public int userSendId;
    public int userReceiveId;
    public String content;
    public Date sendTime;

    private final static String TAG_ID = "id";
    private final static String TAG_USER_SEND_ID = "user_send_id";
    private final static String TAG_USER_RECEIVE_ID = "user_receive_id";
    private final static String TAG_CONTENT = "content";
    private final static String TAG_SEND_TIME = "created_at";

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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            this.sendTime = format.parse(json.getString(TAG_SEND_TIME));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

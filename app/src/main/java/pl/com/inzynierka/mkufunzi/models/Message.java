package pl.com.inzynierka.mkufunzi.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class describing Message model for android app
 */
public class Message {
    /** Field with message id from server */
    public int id;
    /** Field with user id who send message */
    public int userSendId;
    /** Field with user id who has to receive the message */
    public int userReceiveId;
    /** Field with content of message */
    public String content;
    /** Date when message has been send */
    public Date sendTime;

    /** Tag from server with message id */
    private final static String TAG_ID = "id";
    /** Tag from server with user send id */
    private final static String TAG_USER_SEND_ID = "user_send_id";
    /** Tag from server with user receive id */
    private final static String TAG_USER_RECEIVE_ID = "user_receive_id";
    /** Tag from server with content of message */
    private final static String TAG_CONTENT = "content";
    /** Tag from server with send data */
    private final static String TAG_SEND_TIME = "created_at";

    /** No params constructor */
    public Message(){

    }

    /** Constuctor called when message has to be created using data from server */
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

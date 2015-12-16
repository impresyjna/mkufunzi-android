package pl.com.inzynierka.mkufunzi.API;

/**
 * Created by impresyjna on 16.12.15.
 */
public class ServerConnector {

    private static ServerConnector instance;
    private JSONParser jsonParser = new JSONParser();
    private String HOST_IP = "http://trener-mkufunzi.herokuapp.com";
    private String LOGIN = HOST_IP + "/login_mobile";

    private ServerConnector(){

    }

    public static ServerConnector getInstance() {
        if (instance == null) {
            instance = new ServerConnector();
        }
        return instance;
    }

    public JSONParser getJsonParser() {
        return jsonParser;
    }

    public void setJsonParser(JSONParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public String getLOGIN() {
        return LOGIN;
    }

    public String getHOST_IP() {
        return HOST_IP;
    }

}

package pl.com.inzynierka.mkufunzi.API;

/**
 * Singleton class that holds whole addresses to server
 */
public class ServerConnector {

    private static ServerConnector instance;
    private JSONParser jsonParser = new JSONParser();
    private String HOST_IP = "https://trener-mkufunzi.herokuapp.com";
    private String LOGIN = HOST_IP + "/login_mobile";
    private String REGISTER = HOST_IP + "/register_mobile";
    private String MEASURE_TYPES_INDEX = HOST_IP + "/measure_types_index";
    private String GET_MEASUREMENTS = HOST_IP + "/get_measurements_mobile";
    private String POST_MEASUREMENT = HOST_IP + "/post_measurement_mobile";
    private String USER_EXISTS = HOST_IP + "/user_exists_mobile";
    private String GET_MAIN_DATA = HOST_IP + "/get_main_data";
    private String GET_BLOOD_TYPES = HOST_IP + "/get_blood_types_mobile";
    private String GET_EYE_COLORS = HOST_IP + "/get_eye_colors_mobile";
    private String POST_UPDATE_PROTEGE = HOST_IP + "/update_from_mobile";
    private String CREATE_TRAINING_MOBILE = HOST_IP + "/create_training_mobile";
    private String UPDATE_TRAINING_MOBILE = HOST_IP + "/update_training_mobile";
    private String CREATE_ACTIVE_EXCERCISE_MOBILE = HOST_IP + "/create_active_excercise_mobile";
    private String UPDATE_ACTIVE_EXCERCISE_MOBILE = HOST_IP + "/update_active_excercise_mobile";
    private String END_ACTIVE_EXCERCISE_MOBILE = HOST_IP + "/end_active_excercise_mobile";
    private String MY_MESSAGES_INDEX_MOBILE = HOST_IP + "/my_messages_index_mobile";
    private String CREATE_MESSAGE_MOBILE = HOST_IP + "/create_message_mobile";
    private String EXERCISES_TYPES_INDEX_MOBILE = HOST_IP + "/excercise_types_index_mobile";
    private String END_TRAINING_MOBILE = HOST_IP + "/end_training_mobile";
    private String TRAININGS_INDEX_MOBILE = HOST_IP + "/trainings_index_mobile";

    public ServerConnector() {

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

    public String getLOGIN() {
        return LOGIN;
    }

    public String getREGISTER() {
        return REGISTER;
    }

    public String getMEASURE_TYPES_INDEX() {
        return MEASURE_TYPES_INDEX;
    }

    public String getGET_MEASUREMENTS() {
        return GET_MEASUREMENTS;
    }

    public String getPOST_MEASUREMENT() {
        return POST_MEASUREMENT;
    }

    public String getUSER_EXISTS() {
        return USER_EXISTS;
    }

    public String getGET_MAIN_DATA() {
        return GET_MAIN_DATA;
    }

    public String getGET_EYE_COLORS() {
        return GET_EYE_COLORS;
    }

    public String getGET_BLOOD_TYPES() {
        return GET_BLOOD_TYPES;
    }

    public String getPOST_UPDATE_PROTEGE() {
        return POST_UPDATE_PROTEGE;
    }

    public String getCREATE_TRAINING_MOBILE() {
        return CREATE_TRAINING_MOBILE;
    }

    public String getUPDATE_TRAINING_MOBILE() {
        return UPDATE_TRAINING_MOBILE;
    }

    public String getCREATE_ACTIVE_EXCERCISE_MOBILE() {
        return CREATE_ACTIVE_EXCERCISE_MOBILE;
    }

    public String getUPDATE_ACTIVE_EXCERCISE_MOBILE() {
        return UPDATE_ACTIVE_EXCERCISE_MOBILE;
    }

    public String getEND_ACTIVE_EXCERCISE_MOBILE() {
        return END_ACTIVE_EXCERCISE_MOBILE;
    }

    public String getCREATE_MESSAGE_MOBILE() {
        return CREATE_MESSAGE_MOBILE;
    }

    public String getMY_MESSAGES_INDEX_MOBILE() {
        return MY_MESSAGES_INDEX_MOBILE;
    }

    public String getEXERCISES_TYPES_INDEX_MOBILE() {
        return EXERCISES_TYPES_INDEX_MOBILE;
    }

    public String getEND_TRAINING_MOBILE() {
        return END_TRAINING_MOBILE;
    }

    public String getTRAININGS_INDEX_MOBILE() {
        return TRAININGS_INDEX_MOBILE;
    }
}

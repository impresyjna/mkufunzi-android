package pl.com.inzynierka.mkufunzi.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class describing object with last weight measurement, last height measurement and units for them
 * Also hold actual BMI for user
 */
public class WHBMI {

    /** Field with last weight value */
    public double weightValue;
    /** Field with weight unit */
    public String weightUnit;
    /** Field with last height value */
    public double heightValue;
    /** Field with height unit */
    public String heightUnit;
    /** Field with BMI value */
    public double BMIValue;


    /** Tag from server with weightValue */
    private static final String TAG_WEIGHT_VALUE = "weight_value";
    /** Tag from server with weightUnit */
    private static final String TAG_WEIGHT_UNIT = "weight_unit";
    /** Tag from server with heightValue */
    private static final String TAG_HEIGHT_VALUE = "height_value";
    /** Tag from server with heightUnit */
    private static final String TAG_HEIGHT_UNIT = "height_unit";

    /**
     * No parameters constructor
     */
    public WHBMI() {

    }

    /**
     * Constructor called when object has to be created using data from server.
     * @param json
     */
    public WHBMI(JSONObject json) {
        weightValue = 0;
        heightValue = 0;
        BMIValue = 0;
        try {
            weightValue = json.getDouble(TAG_WEIGHT_VALUE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            weightUnit = json.getString(TAG_WEIGHT_UNIT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            heightValue = json.getDouble(TAG_HEIGHT_VALUE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            heightUnit = json.getString(TAG_HEIGHT_UNIT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (weightValue != 0 && heightValue != 0) {
            BMIValue = weightValue / (Math.pow(heightValue,2)*Math.pow(0.01,2));
        }
    }
}

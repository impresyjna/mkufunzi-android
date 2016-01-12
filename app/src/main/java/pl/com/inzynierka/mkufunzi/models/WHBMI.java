package pl.com.inzynierka.mkufunzi.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by impresyjna on 11.01.16.
 */
public class WHBMI {

    public double weightValue;
    public String weightUnit;
    public double heightValue;
    public String heightUnit;
    public double BMIValue;

    private static final String TAG_WEIGHT_VALUE = "id";
    private static final String TAG_WEIGHT_UNIT = "login";
    private static final String TAG_HEIGHT_VALUE = "email";
    private static final String TAG_HEIGHT_UNIT = "name";

    public WHBMI() {

    }

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
            BMIValue = weightValue / (Math.pow(heightValue,2)*Math.pow(10,2));
        }
    }
}

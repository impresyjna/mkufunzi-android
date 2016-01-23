package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import com.activeandroid.ActiveAndroid;

/**
 * Class used to control protege objects in app
 */
public class ProtegesController {

    /**
     * Method used to clear all records in proteges table in database on device
     */
    public void clearProteges(){
        ActiveAndroid.execSQL("delete from proteges");
    }
}

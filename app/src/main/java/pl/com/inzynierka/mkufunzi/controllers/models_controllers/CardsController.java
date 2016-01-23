package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import com.activeandroid.ActiveAndroid;

/**
 * Class used to controll Card objects
 */
public class CardsController {

    /**
     * Method used to clear all records from table cards in sqlite3 database on device 
     */
    public void clearCards(){
        ActiveAndroid.execSQL("delete from cards");
    }
}

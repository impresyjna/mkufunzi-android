package pl.com.inzynierka.mkufunzi.controllers.models_controllers;

import com.activeandroid.ActiveAndroid;

/**
 * Created by impresyjna on 03.01.16.
 */
public class CardsController {

    public void clearCards(){
        ActiveAndroid.execSQL("delete from cards");
    }
}

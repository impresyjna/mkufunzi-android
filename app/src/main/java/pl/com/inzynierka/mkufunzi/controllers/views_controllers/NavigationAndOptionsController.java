package pl.com.inzynierka.mkufunzi.controllers.views_controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import pl.com.inzynierka.mkufunzi.API.measure_types.MeasureTypesIndexMobile;
import pl.com.inzynierka.mkufunzi.R;
import pl.com.inzynierka.mkufunzi.controllers.models_controllers.UsersController;
import pl.com.inzynierka.mkufunzi.models.AppUser;

/**
 * Created by impresyjna on 02.01.16.
 */
public class NavigationAndOptionsController {


    private AppUser appUser = AppUser.getInstance();

    /**
     * Method used to init Cart Submenu in Left slide menu
     *
     * @param navigationView is the Drawer navigation where menu is stored
     * @param activity       is the activity which user is using
     */
    public void initCartSubMenuInDrawer(NavigationView navigationView, AppCompatActivity activity) {
        Menu menu = navigationView.getMenu();
        MeasureTypesIndexMobile measureTypesIndexMobile = new MeasureTypesIndexMobile();
        measureTypesIndexMobile.menu = menu;
        measureTypesIndexMobile.setActivity(activity);
        measureTypesIndexMobile.execute();
    }

    public void initNavHeader(TextView nameAndSurnameText, TextView loginText, TextView emailText) {
        nameAndSurnameText.setText(appUser.getUser().name + " " + appUser.getUser().surname);
        loginText.setText("Użytkownik: " + appUser.getUser().login);
        emailText.setText(appUser.getUser().email);
    }

    public void reactOnNavigationItemSelected(int id, AppCompatActivity activity, String name) {
        if (id == R.id.nav_start_training) {
            openIntent(activity, BluetoothConnection.class);
        } else if (id == R.id.nav_trainings_history) {
            Toast.makeText(activity, "Historia treningów", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_add_medicine) {
            openIntent(activity, AddMedicine.class);
        } else if (id == R.id.nav_my_medicines) {
            openIntent(activity, MyMedicines.class);
        } else if (id == R.id.nav_protege_data) {
            openIntent(activity, ProtegeData.class);
        } else {
            openIntentWithParam(activity, MeasurementPage.class, name);
        }
    }

    public void reactOnOptionItemSelected(int id, AppCompatActivity activity) {
        if (id == R.id.action_settings) {
            openIntent(activity, Settings.class);
        } else if (id == R.id.action_send_message) {
            openIntent(activity, SendMessage.class);
        } else if (id == R.id.action_my_messages) {
            openIntent(activity, MyMessages.class);
        } else if (id == R.id.action_logout) {
            logOut(activity);
        }
    }

    /**
     * Method used for log out user from device
     *
     * @param activity activity which called logOut method
     */
    public void logOut(Activity activity) {
        new UsersController().clearUsers();
        Intent intent = new Intent(activity, Login.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public void openIntent(Activity activity, Class windowClass) {
        Intent intent = new Intent(activity, windowClass);
        activity.startActivity(intent);
        activity.finish();
    }

    public void openIntentWithParam(Activity activity, Class windowClass, String name) {
        Intent intent = new Intent(activity, windowClass);
        Bundle bundle = new Bundle();
        bundle.putString("measure_name", name);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.finish();
    }
}

package pl.com.inzynierka.mkufunzi.controllers.views_controllers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;

import pl.com.inzynierka.mkufunzi.API.measurements.GetMainData;
import pl.com.inzynierka.mkufunzi.API.users.UserExistsMobile;
import pl.com.inzynierka.mkufunzi.R;
import pl.com.inzynierka.mkufunzi.models.AppUser;
import pl.com.inzynierka.mkufunzi.models.BloodType;
import pl.com.inzynierka.mkufunzi.models.EyeColor;
import pl.com.inzynierka.mkufunzi.models.User;

/**
 * Class which controlls action on basic activity for the app
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * instance of AppUser with information about user, protege, his card etc.
     */
    private AppUser appUser = AppUser.getInstance();
    /**
     * Class used to control left side menu
     */
    private NavigationAndOptionsController navigationAndOptionsController = new NavigationAndOptionsController();
    /**
     * Fields describing user in left side menu
     */
    private TextView nameAndSurnameText, loginText, emailText;
    /**
     * Field for data on mainActivity to write on
     */
    private TextView actualWeightText, actualHeightText, actualBmiText, birthDateText, bloodTypeText, genderText;
    /**
     * Field for data on mainActivity to write on
     */
    private TextView lastTrainingText, medicinesText, messagesText;
    /**
     * ImageView for eyeColor field
     */
    private ImageView eyeColorIcon;

    /**
     * Method called when user open the application.
     * It checks if user is logged or exists and make decision
     * If log out method open the Login activity
     * If user doesn't exist asyncTask open the Login activity
     * Otherwise it shows the MainActivity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Trener Mkufunzi");
        setSupportActionBar(toolbar);
        User user = new Select().from(User.class).executeSingle();
        /** Check if there is any logged user */
        if (user == null) {
            Intent intent = new Intent(this, Login.class);
            this.startActivity(intent);
            this.finish();
        } else if (appUser.getUser() == null) { /** Check if user exists */
            UserExistsMobile userExistsMobile = new UserExistsMobile();
            userExistsMobile.setActivity(this);
            userExistsMobile.execute(user.email);
        } else {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationAndOptionsController.initCartSubMenuInDrawer(navigationView, this);

            nameAndSurnameText = (TextView) findViewById(R.id.name_and_surname_text);
            loginText = (TextView) findViewById(R.id.login_text);
            emailText = (TextView) findViewById(R.id.email_text);
            navigationAndOptionsController.initNavHeader(nameAndSurnameText, loginText, emailText);
            initInformationOnActivity();
        }

    }

    /**
     * Method used to connect view with controller. It has to fill:
     * weight
     * height
     * BMI
     * protege blood type
     * protege eye color
     * protege gender
     * last training date
     * last message from trainer
     */
    private void initInformationOnActivity() {
        actualWeightText = (TextView) findViewById(R.id.actual_weight_text);
        actualHeightText = (TextView) findViewById(R.id.actual_height_text);
        actualBmiText = (TextView) findViewById(R.id.actual_bmi_text);
        birthDateText = (TextView) findViewById(R.id.birth_date_text);
        bloodTypeText = (TextView) findViewById(R.id.blood_type_text);
        genderText = (TextView) findViewById(R.id.gender_text);
        lastTrainingText = (TextView) findViewById(R.id.last_training_text);
        medicinesText = (TextView) findViewById(R.id.medicines_text);
        messagesText = (TextView) findViewById(R.id.new_messages_text);
        eyeColorIcon = (ImageView) findViewById(R.id.eye_color_icon);

        actualWeightText.setText("---");
        actualHeightText.setText("---");
        actualBmiText.setText("---");
        birthDateText.setText("---");
        bloodTypeText.setText("---");
        genderText.setText("---");
        lastTrainingText.setText("---");
        medicinesText.setText("---");
        messagesText.setText("---");
        eyeColorIcon.setColorFilter(Color.parseColor("#ffffff"));

        if (appUser.getProtege().birthDate != null && !appUser.getProtege().birthDate.equals("null")) {
            birthDateText.setText(appUser.getProtege().birthDate);
        }
        try {
            if (appUser.getProtege().bloodType != 0) {
                BloodType protegeBloodType = new Select().from(BloodType.class).where("ref_id = ?", appUser.getProtege().bloodType).executeSingle();
                bloodTypeText.setText(protegeBloodType.name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (appUser.getProtege().gender != null && !appUser.getProtege().gender.equals("null") && !appUser.getProtege().gender.equals("")) {
            genderText.setText(appUser.getProtege().gender);
        }
        try {
            if (appUser.getProtege().eyeColor != 0) {
                EyeColor protegeEyeColor = new Select().from(EyeColor.class).where("ref_id = ?", appUser.getProtege().eyeColor).executeSingle();
                eyeColorIcon.setColorFilter(Color.parseColor(protegeEyeColor.color));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (appUser.getWhbmi().weightValue != 0) {
            actualWeightText.setText(appUser.getWhbmi().weightValue + " " + appUser.getWhbmi().weightUnit);
        }
        if (appUser.getWhbmi().heightValue != 0) {
            actualHeightText.setText(appUser.getWhbmi().heightValue + " " + appUser.getWhbmi().heightUnit);
        }
        if (appUser.getWhbmi().BMIValue != 0) {
            actualBmiText.setText(Double.toString((double) Math.round(appUser.getWhbmi().BMIValue * 100) / 100));
        }
        if (!appUser.getWhbmi().lastMessage.equals("null")) {
            messagesText.setText(appUser.getWhbmi().lastMessage);
        }
        if (appUser.getWhbmi().lastTraining != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            lastTrainingText.setText(df.format(appUser.getWhbmi().lastTraining));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        navigationAndOptionsController.reactOnOptionItemSelected(id, this);

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String name = item.getTitle().toString();
        navigationAndOptionsController.reactOnNavigationItemSelected(id, this, name);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

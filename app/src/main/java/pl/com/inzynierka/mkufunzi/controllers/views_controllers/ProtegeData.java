package pl.com.inzynierka.mkufunzi.controllers.views_controllers;

import android.app.DatePickerDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.com.inzynierka.mkufunzi.API.proteges.UpdateFromMobile;
import pl.com.inzynierka.mkufunzi.R;
import pl.com.inzynierka.mkufunzi.models.AppUser;
import pl.com.inzynierka.mkufunzi.models.BloodType;
import pl.com.inzynierka.mkufunzi.models.EyeColor;

public class ProtegeData extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationAndOptionsController navigationAndOptionsController = new NavigationAndOptionsController();
    private TextView nameAndSurnameText, loginText, emailText;
    private Spinner eyeColorSpinner, bloodTypeSpinner, genderSpinner;
    private EyeColor choosenEyeColor = new EyeColor();
    private BloodType choosenBloodType = new BloodType();
    private String choosenGender;
    private AppUser appUser = AppUser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protege_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Podstawowe dane");
        setSupportActionBar(toolbar);

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

        eyeColorSpinner = (Spinner) findViewById(R.id.eye_color_spinner);
        bloodTypeSpinner = (Spinner) findViewById(R.id.blood_type_spinner);
        genderSpinner = (Spinner) findViewById(R.id.gender_spinner);

        eyeColorSpinner.setOnItemSelectedListener(new EyeColorItemSelectedListener());
        bloodTypeSpinner.setOnItemSelectedListener(new BloodTypeItemSelectedListener());
        genderSpinner.setOnItemSelectedListener(new GenderItemSelectedListener());

        List<EyeColor> eyeColors = new Select().from(EyeColor.class).execute();
        List<BloodType> bloodTypes = new Select().from(BloodType.class).execute();
        List<String> genders = Arrays.asList("Kobieta", "Mężczyzna", "Płeć");

        ArrayAdapter<EyeColor> eyeColorSpinnerAdapter = new ArrayAdapter<EyeColor>(this, R.layout.spinner_item, eyeColors) {
            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
        };
        eyeColors.add(new EyeColor("", 0, "Kolor oczu"));
        eyeColorSpinner.setAdapter(eyeColorSpinnerAdapter);

        ArrayAdapter<BloodType> bloodTypeArrayAdapter = new ArrayAdapter<BloodType>(this, R.layout.spinner_item, bloodTypes) {
            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
        };
        bloodTypes.add(new BloodType(0, "Grupa krwi"));
        bloodTypeSpinner.setAdapter(bloodTypeArrayAdapter);

        ArrayAdapter<String> genderArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, genders) {
            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
        };
        genderSpinner.setAdapter(genderArrayAdapter);


        bloodTypeSpinner.setSelection(bloodTypeArrayAdapter.getCount());
        eyeColorSpinner.setSelection(eyeColorSpinnerAdapter.getCount());
        genderSpinner.setSelection(genderArrayAdapter.getCount());
    }

    public class EyeColorItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (pos < parent.getCount() - 1) {
                choosenEyeColor = (EyeColor) parent.getItemAtPosition(pos);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }

    public class BloodTypeItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (pos < parent.getCount() - 1) {
                choosenBloodType = (BloodType) parent.getItemAtPosition(pos);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }

    public class GenderItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (pos < parent.getCount() - 1) {
                choosenGender = parent.getItemAtPosition(pos).toString();
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Log.d("CDA", "onBackPressed Called");
            navigationAndOptionsController.openIntent(this, MainActivity.class);
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

    public void updateProtege(View view){
        String genderFirstLetter = new String();
        try {
            appUser.getProtege().gender = choosenGender.substring(0,1);
            genderFirstLetter = choosenGender.substring(0,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            appUser.getProtege().eyeColor = choosenEyeColor.id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            appUser.getProtege().bloodType = choosenBloodType.id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        UpdateFromMobile updateFromMobile = new UpdateFromMobile();
        updateFromMobile.setActivity(this);
        updateFromMobile.execute(Integer.toString(appUser.getProtege().id), Integer.toString(choosenEyeColor.id), genderFirstLetter, Integer.toString(choosenBloodType.id),"");
    }
}

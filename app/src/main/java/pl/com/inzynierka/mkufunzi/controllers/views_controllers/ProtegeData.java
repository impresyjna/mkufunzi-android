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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import pl.com.inzynierka.mkufunzi.API.proteges.UpdateFromMobile;
import pl.com.inzynierka.mkufunzi.R;
import pl.com.inzynierka.mkufunzi.models.AppUser;
import pl.com.inzynierka.mkufunzi.models.BloodType;
import pl.com.inzynierka.mkufunzi.models.EyeColor;

/**
 * Controller for ProtegeData activity, allowing user to update protege data on server
 */
public class ProtegeData extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    /** Class used to control left side menu */
    private NavigationAndOptionsController navigationAndOptionsController = new NavigationAndOptionsController();
    /** Fields describing user in left side menu */
    private TextView nameAndSurnameText, loginText, emailText;
    /** Spinners for eyeColor, bloodType and gender*/
    private Spinner eyeColorSpinner, bloodTypeSpinner, genderSpinner;
    /** input for birthDate */
    private EditText birthDateInput;
    /** field for dialog with calendar after click on birthDateInput */
    private DatePickerDialog birthDateDatePicker;
    /** DateFormat for birthDate to make it compatible with server */
    private SimpleDateFormat dateFormatter;

    /** Chosen eyeColor by user */
    private EyeColor chosenEyeColor = new EyeColor();
    /** Chosen bloodType by user */
    private BloodType chosenBloodType = new BloodType();
    /** Chosen gender by user */
    private String chosenGender, chosenBirthDate;
    /** instance of AppUser with information about user, protege, his card etc.*/
    private AppUser appUser = AppUser.getInstance();

    /**
     * Method called at the beginning of using the activity
     * First it sets toolbar title, later draw the left side menu
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protege_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Podstawowe dane");
        setSupportActionBar(toolbar);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationAndOptionsController.initCartSubMenuInDrawer(navigationView, this);

        initFieldsInView();

        eyeColorFieldInit();
        bloodTypeFieldInit();
        genderFieldInit();
        birthDateFieldInit();


        navigationAndOptionsController.initNavHeader(nameAndSurnameText, loginText, emailText);


    }

    /**
     * This method is used to init fields from view in controller
     */
    private void initFieldsInView() {
        nameAndSurnameText = (TextView) findViewById(R.id.name_and_surname_text);
        loginText = (TextView) findViewById(R.id.login_text);
        emailText = (TextView) findViewById(R.id.email_text);
        birthDateInput = (EditText) findViewById(R.id.birth_date_input);
        birthDateInput.setInputType(InputType.TYPE_NULL);

        eyeColorSpinner = (Spinner) findViewById(R.id.eye_color_spinner);
        bloodTypeSpinner = (Spinner) findViewById(R.id.blood_type_spinner);
        genderSpinner = (Spinner) findViewById(R.id.gender_spinner);

    }

    /**
     * This method is used to set adapter for eyeColor field and set listener for spinner. Also it is used to set
     * text on spinner if protege has value from server
     */
    private void eyeColorFieldInit() {
        eyeColorSpinner.setOnItemSelectedListener(new EyeColorItemSelectedListener());
        List<EyeColor> eyeColors = new Select().from(EyeColor.class).execute();
        ArrayAdapter<EyeColor> eyeColorSpinnerAdapter = new ArrayAdapter<EyeColor>(this, R.layout.spinner_item, eyeColors) {
            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };
        eyeColors.add(new EyeColor("", 0, "Wybierz kolor oczu"));
        eyeColorSpinner.setAdapter(eyeColorSpinnerAdapter);
        if (appUser.getProtege().eyeColor == 0) {
            eyeColorSpinner.setSelection(eyeColorSpinnerAdapter.getCount());
        } else {
            eyeColorSpinner.setSelection(appUser.getProtege().eyeColor - 1);
        }
    }

    /**
     * This method is used to set adapter for bloodType field and set listener for spinner. Also it is used to set
     * text on spinner if protege has value from server
     */
    private void bloodTypeFieldInit() {
        bloodTypeSpinner.setOnItemSelectedListener(new BloodTypeItemSelectedListener());
        List<BloodType> bloodTypes = new Select().from(BloodType.class).execute();
        ArrayAdapter<BloodType> bloodTypeArrayAdapter = new ArrayAdapter<BloodType>(this, R.layout.spinner_item, bloodTypes) {
            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };
        bloodTypes.add(new BloodType(0, "Wybierz grupę krwi"));
        bloodTypeSpinner.setAdapter(bloodTypeArrayAdapter);
        if (appUser.getProtege().bloodType == 0) {
            bloodTypeSpinner.setSelection(bloodTypeArrayAdapter.getCount());
        } else {
            bloodTypeSpinner.setSelection(appUser.getProtege().bloodType - 1);
        }
    }

    /**
     * This method is used to set adapter for gender field and set listener for spinner. Also it is used to set
     * text on spinner if protege has value from server
     */
    private void genderFieldInit() {
        genderSpinner.setOnItemSelectedListener(new GenderItemSelectedListener());
        List<String> genders = Arrays.asList("Kobieta", "Mężczyzna", "Wybierz płeć");
        ArrayAdapter<String> genderArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, genders) {
            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };
        genderSpinner.setAdapter(genderArrayAdapter);
        if (appUser.getProtege().gender == null || appUser.getProtege().gender.equals("") || appUser.getProtege().birthDate.equals("null")) {
            genderSpinner.setSelection(genderArrayAdapter.getCount());
        } else if (appUser.getProtege().gender.equals("K")) {
            genderSpinner.setSelection(0);
        } else {
            genderSpinner.setSelection(1);
        }
    }

    /**
     * This method is used to init datePicker when editText for birthDate is clicked and set text if protege
     * has data on server
     */
    private void birthDateFieldInit() {
        setDateTimeField();
        if (appUser.getProtege().birthDate == null || appUser.getProtege().birthDate.equals("") || appUser.getProtege().birthDate.equals("null")) {
            birthDateInput.setText("Naciśnij aby wprowadzić datę");
        } else {
            birthDateInput.setText(appUser.getProtege().birthDate);
        }
    }

    /**
     * This method is used to initialize datePicker on click and to set action when date is picked
     */
    private void setDateTimeField() {
        birthDateInput.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        birthDateDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                birthDateInput.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    /**
     * Method called after clicking on birthDateInput
     * @param v - view that called that method
     */
    @Override
    public void onClick(View v) {
        birthDateDatePicker.show();
    }


    /**
     * Method called by Spinner for eyeColor when eyeColor is chosen. It has to find which item is picked and remember it in
     * local variable
     */
    public class EyeColorItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (pos < parent.getCount()) {
                chosenEyeColor = (EyeColor) parent.getItemAtPosition(pos);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }

    /**
     * Method called by Spinner for bloodType when bloodType is chosen. It has to find which item is picked and remember it in
     * local variable
     */
    public class BloodTypeItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (pos < parent.getCount()) {
                chosenBloodType = (BloodType) parent.getItemAtPosition(pos);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }

    /**
     * Method called by Spinner for gender when gender is chosen. It has to find which item is picked and remember it in
     * local variable
     */
    public class GenderItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (pos < parent.getCount()) {
                chosenGender = parent.getItemAtPosition(pos).toString();
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }

    /**
     * Method called when back button is pressed. It has to close actual activity and open MainActivity
     */
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

    /**
     * Method called when user uses left side menu and pick the option
     */
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

    /**
     * Method used to get data from view and call method to connect with server with this data
     * This method is checking if data is set by using try catch. If user choose something it saves it in protege fields in appUser object
     * Later it calls the UpdateFromMobile AsyncTask to communicate with server 
     * @params view - This is the view that called this method. Here this method is called by button
     */
    public void updateProtege(View view) {
        try {
            appUser.getProtege().gender = chosenGender.substring(0, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            appUser.getProtege().eyeColor = chosenEyeColor.id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            appUser.getProtege().bloodType = chosenBloodType.id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        chosenBirthDate = birthDateInput.getText().toString();
        try {
            dateFormatter.parse(chosenBirthDate);
            appUser.getProtege().birthDate = chosenBirthDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        UpdateFromMobile updateFromMobile = new UpdateFromMobile();
        updateFromMobile.setActivity(this);
        updateFromMobile.execute(Integer.toString(appUser.getProtege().id), Integer.toString(appUser.getProtege().eyeColor), appUser.getProtege().gender, Integer.toString(appUser.getProtege().bloodType), appUser.getProtege().birthDate );
    }
}

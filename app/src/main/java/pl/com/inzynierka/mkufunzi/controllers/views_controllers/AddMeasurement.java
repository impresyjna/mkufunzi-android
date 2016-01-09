package pl.com.inzynierka.mkufunzi.controllers.views_controllers;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import pl.com.inzynierka.mkufunzi.API.measurements.PostMeasurementMobile;
import pl.com.inzynierka.mkufunzi.R;
import pl.com.inzynierka.mkufunzi.controllers.models_controllers.MeasureTypesController;
import pl.com.inzynierka.mkufunzi.models.AppUser;
import pl.com.inzynierka.mkufunzi.models.MeasureType;

public class AddMeasurement extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationAndOptionsController navigationAndOptionsController = new NavigationAndOptionsController();
    private EditText firstValueInput, secondValueInput, unitTextInput;
    private TextInputLayout inputLayoutFirstValue, inputLayoutSecondValue;
    private LinearLayout linearLayoutForValues;
    private MeasureType measureType;
    private AppUser appUser = AppUser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measurement);

        /** Find measure type correct with param given */
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("measure_name").toLowerCase();
        measureType = new MeasureTypesController().getMeasureType(name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dodaj pomiar - " + name);
        setSupportActionBar(toolbar);

        linearLayoutForValues = (LinearLayout) findViewById(R.id.linear_layout_for_values);

        firstValueInput = (EditText) findViewById(R.id.first_value_input);
        secondValueInput = (EditText) findViewById(R.id.second_value_input);
        inputLayoutFirstValue = (TextInputLayout) findViewById(R.id.input_layout_first_value);
        inputLayoutSecondValue = (TextInputLayout) findViewById(R.id.input_layout_second_value);
        unitTextInput = (EditText) findViewById(R.id.unit_text);

        unitTextInput.setText(measureType.unit);

        if(!measureType.extraField){
            linearLayoutForValues.removeView(inputLayoutSecondValue);
            inputLayoutFirstValue.setHint(measureType.name.substring(0,1).toUpperCase()+measureType.name.substring(1).toLowerCase());
        }
        else {
            inputLayoutFirstValue.setHint(measureType.firstLabel.substring(0,1).toUpperCase()+measureType.firstLabel.substring(1).toLowerCase());
            inputLayoutSecondValue.setHint(measureType.secondLabel.substring(0,1).toUpperCase()+measureType.secondLabel.substring(1).toLowerCase());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationAndOptionsController.initCartSubMenuInDrawer(navigationView, this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    public void addMeasure(View view)
    {
        if(!validateFirstValue())
        {
            return;
        }
        if(!validateSecondValue() && measureType.extraField) {
            return;
        } else {
            double firstValue = 0;
            double secondValue = 0;
            if(measureType.extraField){
                secondValue = getValueFromField(secondValueInput);
            }
            firstValue = getValueFromField(firstValueInput);
            PostMeasurementMobile postMeasurementMobile = new PostMeasurementMobile();
            postMeasurementMobile.setActivity(this);
            postMeasurementMobile.execute(Double.toString(firstValue), Integer.toString(appUser.getCard().id), Integer.toString(measureType.id), measureType.name, Double.toString(secondValue));
        }

    }

    public boolean validateFirstValue(){
        String valueText = firstValueInput.getText().toString().trim();
        if(valueText.equals(""))
        {
            inputLayoutFirstValue.setError(getString(R.string.err_value_empty));
            requestFocus(firstValueInput);
            return false;
        } else {
            inputLayoutFirstValue.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validateSecondValue() {
        String valueText = secondValueInput.getText().toString().trim();
        if(valueText.equals(""))
        {
            inputLayoutSecondValue.setError(getString(R.string.err_value_empty));
            requestFocus(secondValueInput);
            return false;
        } else {
            inputLayoutSecondValue.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private double getValueFromField(EditText editText){
        String valueText = editText.getText().toString();
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        Number number = null;
        double value = 0;
        try {
            number = format.parse(valueText);
            value = number.doubleValue();
        } catch (ParseException e) {
            value = Double.parseDouble(valueText);
            e.printStackTrace();
        }
        return value;
    }
}

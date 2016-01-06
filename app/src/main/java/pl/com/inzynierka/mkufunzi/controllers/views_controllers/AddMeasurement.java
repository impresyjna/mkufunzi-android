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
    private EditText valueInput;
    private TextInputLayout inputLayoutValue;
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

        valueInput = (EditText) findViewById(R.id.value_input);
        inputLayoutValue = (TextInputLayout) findViewById(R.id.input_layout_value);

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
        if(!validateValue())
        {
            return;
        } else {
            String valueText = valueInput.getText().toString();
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
            PostMeasurementMobile postMeasurementMobile = new PostMeasurementMobile();
            postMeasurementMobile.setActivity(this);
            postMeasurementMobile.execute(Double.toString(value), Integer.toString(appUser.getCard().id), Integer.toString(measureType.id), measureType.name);
        }

    }

    public boolean validateValue(){
        String valueText = valueInput.getText().toString().trim();
        if(valueText.equals(""))
        {
            inputLayoutValue.setError(getString(R.string.err_value_empty));
            requestFocus(valueInput);
            return false;
        } else {
            inputLayoutValue.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}

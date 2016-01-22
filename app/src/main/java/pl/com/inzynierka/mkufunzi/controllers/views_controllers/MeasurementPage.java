package pl.com.inzynierka.mkufunzi.controllers.views_controllers;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import pl.com.inzynierka.mkufunzi.API.measurements.GetMeasurementsMobile;
import pl.com.inzynierka.mkufunzi.R;
import pl.com.inzynierka.mkufunzi.controllers.models_controllers.MeasureTypesController;
import pl.com.inzynierka.mkufunzi.models.AppUser;
import pl.com.inzynierka.mkufunzi.models.MeasureType;

/**
 * Controller for view showing user appropriate measureType measurements
 */
public class MeasurementPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Class used to control left side menu
     */
    private NavigationAndOptionsController navigationAndOptionsController = new NavigationAndOptionsController();
    /**
     * Object with information about MeasureType in this activity
     */
    private MeasureType measureType;
    /**
     * instance of AppUser with information about user, protege, his card etc.
     */
    private AppUser appUser = AppUser.getInstance();
    /**
     * Fields describing user in left side menu
     */
    private TextView nameAndSurnameText, loginText, emailText;

    /**
     * Method called at the beginning of using the activity
     * First it loads information about measureType name
     * Later init the toolbar with appropiate title
     * Later init method showMeasurementsFromServer and initNavUserInformationFields
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Find measure type correct with param given */
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("measure_name").toLowerCase();
        measureType = new MeasureTypesController().getMeasureType(name);

        setContentView(R.layout.activity_measurement_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationAndOptionsController.initCartSubMenuInDrawer(navigationView, this);

        showMeasurementsFromServer();

        initNavUserInformationFields();

    }

    /**
     * Method which is responsible for init the RecyclerView for measurements and call the method to connect with server
     * to receive measurements
     */
    private void showMeasurementsFromServer() {
        // Lookup the recyclerview in activity layout
        RecyclerView rvMeasures = (RecyclerView) findViewById(R.id.rvMeasures);
        GetMeasurementsMobile getMeasurementsMobile = new GetMeasurementsMobile();
        getMeasurementsMobile.setActivity(this);
        getMeasurementsMobile.setRvMeasures(rvMeasures);
        getMeasurementsMobile.execute(Integer.toString(appUser.getCard().id), Integer.toString(measureType.id));
    }

    /**
     * Method used to init information on left side menu
     */
    private void initNavUserInformationFields() {
        nameAndSurnameText = (TextView) findViewById(R.id.name_and_surname_text);
        loginText = (TextView) findViewById(R.id.login_text);
        emailText = (TextView) findViewById(R.id.email_text);
        navigationAndOptionsController.initNavHeader(nameAndSurnameText, loginText, emailText);
    }

    /**
     * Method called when user press the back button. It has to close actual activity and open MainActivity
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
     * Method called when user choose item from left side menu
     * @param item - which item was picked
     * @return
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
     * Method called when user pick the AddMeasure button
     * @param view - which view called it
     */
    public void showAddMeasurement(View view) {
        navigationAndOptionsController.openIntentWithParam(this, AddMeasurement.class, measureType.name);
        this.finish();
    }
}

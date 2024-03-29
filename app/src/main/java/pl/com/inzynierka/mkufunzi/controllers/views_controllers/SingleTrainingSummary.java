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
import android.widget.TextView;

import pl.com.inzynierka.mkufunzi.API.trainings.TrainingShowMobile;
import pl.com.inzynierka.mkufunzi.R;

public class SingleTrainingSummary extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Class used to control left side menu
     */
    private NavigationAndOptionsController navigationAndOptionsController = new NavigationAndOptionsController();
    /**
     * Fields describing user in left side menu
     */
    private TextView nameAndSurnameText, loginText, emailText;
    /**
     * Field for data on SingleTrainingSummary to write on
     */
    private TextView singleTrainingId, singleTrainingStart, singleTrainingStop, singleTrainingComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_training_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Podsumowanie treningu");
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id").toLowerCase();

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

        singleTrainingId = (TextView) findViewById(R.id.single_training_id);
        singleTrainingStart = (TextView) findViewById(R.id.single_training_start);
        singleTrainingStop = (TextView) findViewById(R.id.single_training_stop);
        singleTrainingComment = (TextView) findViewById(R.id.single_training_comment);
        RecyclerView rvDoneExcercises = (RecyclerView) findViewById(R.id.rv_done_excercises);

        TrainingShowMobile trainingShowMobile = new TrainingShowMobile(this, singleTrainingId, singleTrainingStart, singleTrainingStop, singleTrainingComment, rvDoneExcercises);
        trainingShowMobile.execute(id);
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
        navigationAndOptionsController.reactOnOptionItemSelected(id,this);

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


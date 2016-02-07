package pl.com.inzynierka.mkufunzi.controllers.views_controllers;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.activeandroid.query.Select;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.com.inzynierka.mkufunzi.API.active_excercises.CreateActiveExcerciseMobile;
import pl.com.inzynierka.mkufunzi.API.active_excercises.EndActiveExcerciseMobile;
import pl.com.inzynierka.mkufunzi.API.active_excercises.UpdateActiveExcerciseMobile;
import pl.com.inzynierka.mkufunzi.API.excercises.ExcercisesTypesIndexMobile;
import pl.com.inzynierka.mkufunzi.API.trainings.CreateTrainingMobile;
import pl.com.inzynierka.mkufunzi.API.trainings.EndTrainingMobile;
import pl.com.inzynierka.mkufunzi.R;
import pl.com.inzynierka.mkufunzi.bluetooth.ManageConnectThread;
import pl.com.inzynierka.mkufunzi.models.ActiveExcercise;
import pl.com.inzynierka.mkufunzi.models.AppUser;
import pl.com.inzynierka.mkufunzi.models.ExcerciseType;

/**
 * Class used to control view for traning
 */
public class TrainingMonitor extends AppCompatActivity
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
     * instance of AppUser with information about user, protege, his card etc.
     */
    private AppUser appUser = AppUser.getInstance();
    /**
     * Field for data on TrainingMonitor to write on
     */
    private TextView pulseOutput, exerciseOutput, excerciseTitleOutput, excerciseInstruction;
    /** LinearLayout with all TextViews with params about training. Necessary to hide everything before user starts training */
    private LinearLayout trainingParameters;
    /** List with ExcerciseTypes from slite3 database */
    private List<ExcerciseType> excerciseTypes = new ArrayList<>();

    /** How many repeats value */
    private int howManyReapets = 0;
    /** Actual pulse */
    private int pulse = 0;
    /** Mutex used to block the chance to count more than one repeat */
    private boolean mutex = false;

    /** Field for stoper */
    private TextView timerValue;


    private long startTime = 0L;

    /** Handler to update stoper field and count how much time is now */
    private Handler timerHandler = new Handler();
    /** Handler used to update pulse and howManyRepeats and to communicate with BT device */
    private Handler pulseHandler = new Handler();
    /** Handler used to communicate with server */
    private Handler updateOnServerHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_monitor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Monitorowanie treningu");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationAndOptionsController.initCartSubMenuInDrawer(navigationView, this);

        initFieldsInView();

        CreateTrainingMobile createTrainingMobile = new CreateTrainingMobile();
        createTrainingMobile.setActivity(this);
        createTrainingMobile.execute(Integer.toString(appUser.getProtege().id));

        ExcercisesTypesIndexMobile excercisesTypesIndexMobile = new ExcercisesTypesIndexMobile();
        excercisesTypesIndexMobile.setActivity(this);
        excercisesTypesIndexMobile.execute();

    }

    /** Method with all inits in this activity */
    public void initFieldsInView(){
        nameAndSurnameText = (TextView) findViewById(R.id.name_and_surname_text);
        loginText = (TextView) findViewById(R.id.login_text);
        emailText = (TextView) findViewById(R.id.email_text);
        navigationAndOptionsController.initNavHeader(nameAndSurnameText, loginText, emailText);

        pulseOutput = (TextView) findViewById(R.id.pulse_output);
        exerciseOutput = (TextView) findViewById(R.id.exercise_output);

        timerValue = (TextView) findViewById(R.id.timerValue);
        excerciseTitleOutput = (TextView) findViewById(R.id.excercise_title_output);
        excerciseInstruction = (TextView) findViewById(R.id.excercise_instruction);
        trainingParameters = (LinearLayout) findViewById(R.id.training_parameters);
        trainingParameters.setVisibility(View.INVISIBLE);
    }

    @Override
    /** What should be done when user presses the back button */
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

    /** Method called when user press the start button in activity. It starts the training or stop it if it is already running */
    public void startTraining(View view) {
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            trainingPreparation();
        } else {
            trainingStop();
        }

    }

    /** Method called when user presses nextExcerciseButton in activity. It has to change the excercise, stop handler connecting with server for the moment
     * clear all fields and call the method to connect with server to end activeExcercise and begin new one */
    public void nextExcercise(View view) {
        appUser.getActiveExcercise().excerciseTypeId = excerciseTypes.get(1).id;

        updateOnServerHandler.removeCallbacks(updateActiveExcerciseOnServerThread);

        clearAllAndDisplay(excerciseTypes.get(1).name, excerciseTypes.get(1).formula);
        EndActiveExcerciseMobile endActiveExcerciseMobile = new EndActiveExcerciseMobile();
        endActiveExcerciseMobile.setActivity(this);
        endActiveExcerciseMobile.execute();
        view.setVisibility(View.INVISIBLE);
        updateOnServerHandler.postDelayed(updateActiveExcerciseOnServerThread, 5000);
    }

    /** At the beginning of training this method is called when user starts the training
     * It has to create new ActiveExcercise in database and run all handlers to refresh data in activity
     */
    private void trainingPreparation() {
        trainingParameters.setVisibility(View.VISIBLE);
        appUser.setActiveExcercise(new ActiveExcercise());
        excerciseTypes = new Select().from(ExcerciseType.class).execute();
        appUser.getActiveExcercise().excerciseTypeId = excerciseTypes.get(0).id;

        CreateActiveExcerciseMobile createActiveExcerciseMobile = new CreateActiveExcerciseMobile();
        createActiveExcerciseMobile.setActivity(this);
        createActiveExcerciseMobile.execute(Integer.toString(appUser.getProtege().id), Integer.toString(appUser.getTraining().id), Integer.toString(appUser.getActiveExcercise().excerciseTypeId));

        clearAllAndDisplay(excerciseTypes.get(0).name, excerciseTypes.get(0).formula);
        timerHandler.postDelayed(updateTimerThread, 0);
        pulseHandler.postDelayed(updatePulseThread, 0);
        updateOnServerHandler.postDelayed(updateActiveExcerciseOnServerThread, 5000);
    }

    /** method called to clear all variables and clear all data displayed in activity */
    private void clearAllAndDisplay(String excerciseTitle, String excerciseInstrunction){
        startTime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updatedTime = 0L;
        pulse = 0;
        howManyReapets = 0;
        startTime = SystemClock.uptimeMillis();
        excerciseTitleOutput.setText(excerciseTitle);
        excerciseInstruction.setText(excerciseInstrunction);
        pulseOutput.setText(Integer.toString(pulse));
        exerciseOutput.setText(Integer.toString(howManyReapets));
    }

    /** Method called to stop the training. It has to call the method to communicate with server and end training. It also has to stop handler */
    private void trainingStop() {
        timeSwapBuff += timeInMilliseconds;
        timerHandler.removeCallbacks(updateTimerThread);
        pulseHandler.removeCallbacks(updatePulseThread);
        updateOnServerHandler.removeCallbacks(updateActiveExcerciseOnServerThread);
        appUser.getConnectThread().cancel();
        EndTrainingMobile endTrainingMobile = new EndTrainingMobile();
        endTrainingMobile.setActivity(this);
        endTrainingMobile.execute(Integer.toString(appUser.getTraining().id));
    }


    /** Thread used to be a stoper */
    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            appUser.getActiveExcercise().time = "" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds);
            timerHandler.postDelayed(this, 0);
        }

    };

    /** Thread used to communicate with server and update ActiveExcercise in database */
    private Runnable updateActiveExcerciseOnServerThread = new Runnable() {

        public void run() {
            UpdateActiveExcerciseMobile updateActiveExcerciseMobile = new UpdateActiveExcerciseMobile();
            updateActiveExcerciseMobile.execute();

            updateOnServerHandler.postDelayed(this, 5000);
        }

    };

    /** Thread used to communicate with BT device and calculate howManyRepeats and receive pulse value */
    private Runnable updatePulseThread = new Runnable() {
        final ManageConnectThread manageConnectThread = new ManageConnectThread();

        public void run() {
            try {
                String message = manageConnectThread.receiveData(appUser.getConnectThread().getbTSocket());
                if (message.contains("Pulse")) {
                    try {
                        String[] pulseParts = message.split(":");
                        pulse = Integer.parseInt(pulseParts[1]);
                        pulseOutput.setText(Integer.toString(pulse));
                        appUser.getActiveExcercise().pulse = pulse;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        String[] acAndGyParts = message.split("\\|");
                        String accPartBeforeSecondSplit = acAndGyParts[0];
                        String gyPartBeforeSecondSplit = acAndGyParts[1];
                        String[] accPartsAfterSplit = accPartBeforeSecondSplit.split(";");
                        String[] gyPartsAfterSplit = gyPartBeforeSecondSplit.split(";");
                        double resultOfAcc = Math.sqrt(Math.pow(Double.parseDouble(accPartsAfterSplit[1]), 2) +
                                Math.pow(Double.parseDouble(accPartsAfterSplit[2]), 2) +
                                Math.pow(Double.parseDouble(accPartsAfterSplit[3]), 2)) - 10;
                        if (appUser.getActiveExcercise().excerciseTypeId == 1) {
                            crouchCount(resultOfAcc, gyPartsAfterSplit[1]);
                        } else {
                            sitUpCount(resultOfAcc, gyPartsAfterSplit[1]);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            pulseHandler.postDelayed(this, 0); // set time here to refresh textView
        }

    };

    /** Method used to count howManyCrouch */
    private void crouchCount(double resultOfAcc, String gyY){
        if (mutex && resultOfAcc < 15000) {
            mutex = false;
        } else if (!mutex && resultOfAcc > 20000 && Double.parseDouble(gyY) < 100) {
            mutex = true;
            howManyReapets++;
            exerciseOutput.setText(Integer.toString(howManyReapets));
            appUser.getActiveExcercise().howMany = howManyReapets;
        }
    }

    /** Method uset to count howManySitUp */
    private void sitUpCount(double resultOfAcc, String gyY){
        if (mutex && resultOfAcc < 16000) {
            mutex = false;
        } else if (!mutex && resultOfAcc > 20500 && Double.parseDouble(gyY) < 100) {
            mutex = true;
            howManyReapets++;
            exerciseOutput.setText(Integer.toString(howManyReapets));
            appUser.getActiveExcercise().howMany = howManyReapets;
        }
    }


}


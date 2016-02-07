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

import pl.com.inzynierka.mkufunzi.API.messages.MyMessagesIndexMobile;
import pl.com.inzynierka.mkufunzi.R;
import pl.com.inzynierka.mkufunzi.models.AppUser;

/** Activity with messages from server. Messages are from trainer to user and from user to trainer. Looks like sms in android */
public class MyMessages extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Class used to control left side menu
     */
    private NavigationAndOptionsController navigationAndOptionsController = new NavigationAndOptionsController();
    /**
     * Fields describing user in left side menu
     */
    private TextView nameAndSurnameText, loginText, emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Moje wiadomości");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationAndOptionsController.initCartSubMenuInDrawer(navigationView, this);

        showMessagesFromServer();
        initNavUserInformationFields();
    }

    /**
     * Method which is responsible for init the RecyclerView for messages and call the method to connect with server
     * to receive messages for user
     */
    private void showMessagesFromServer() {
        // Lookup the recyclerview in activity layout
        RecyclerView rvMeasures = (RecyclerView) findViewById(R.id.rvMessages);
        MyMessagesIndexMobile myMessagesIndexMobile = new MyMessagesIndexMobile();
        myMessagesIndexMobile.setActivity(this);
        myMessagesIndexMobile.setRvMeasures(rvMeasures);
        myMessagesIndexMobile.execute();
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

    /** Method called when user clicks the button to send new message */
    public void sendMessageViewOpen(View view){
        navigationAndOptionsController.openIntent(this,SendMessage.class);
    }
}

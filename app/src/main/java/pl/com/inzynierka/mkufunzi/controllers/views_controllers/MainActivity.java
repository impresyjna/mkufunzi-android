package pl.com.inzynierka.mkufunzi.controllers.views_controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.activeandroid.query.Select;

import pl.com.inzynierka.mkufunzi.R;
import pl.com.inzynierka.mkufunzi.models.AppUser;
import pl.com.inzynierka.mkufunzi.models.Card;
import pl.com.inzynierka.mkufunzi.models.Protege;
import pl.com.inzynierka.mkufunzi.models.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppUser appUser = AppUser.getInstance();
    private NavigationAndOptionsController navigationAndOptionsController = new NavigationAndOptionsController();
    private TextView nameAndSurnameText, loginText, emailText;

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
        } else {
            appUser.setUser(user);
            Protege protege = new Select().from(Protege.class).where("user_id = ?", user.id).executeSingle();
            Card card = new Select().from(Card.class).where("protege_id = ?", protege.id).executeSingle();
            appUser.setProtege(protege);
            appUser.setCard(card);
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
        }
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
}

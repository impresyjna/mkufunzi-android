package pl.com.inzynierka.mkufunzi.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import pl.com.inzynierka.mkufunzi.R;
import pl.com.inzynierka.mkufunzi.controllers.users_controller.LoginController;
import pl.com.inzynierka.mkufunzi.controllers.users_controller.RegisterController;


public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        getSupportActionBar().hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /* Button interaction in activity */
    public void exit(View view)
    {
        Log.i("Exit", "Begin");
        this.finish();
        System.exit(0);
        Log.i("Exit", "End");
    }

    public void showAbout(View view)
    {
        Log.i("About", "Opening about page activity ");
        Intent intent = new Intent(this, About.class);
        this.startActivity(intent);
    }

    public void showLogin(View view)
    {
        Log.i("LoginController", "Opening login page activity ");
        Intent intent = new Intent(this, LoginController.class);
        this.startActivity(intent);
    }

    public void showRegister(View view)
    {
        Log.i("RegisterController", "Opening register page activity");
        Intent intent = new Intent(this, RegisterController.class);
        this.startActivity(intent);
    }
}

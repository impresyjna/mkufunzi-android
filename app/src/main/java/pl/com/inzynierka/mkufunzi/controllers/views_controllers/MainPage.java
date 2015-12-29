package pl.com.inzynierka.mkufunzi.controllers.views_controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.query.Select;

import pl.com.inzynierka.mkufunzi.R;
import pl.com.inzynierka.mkufunzi.controllers.models_controllers.UsersController;
import pl.com.inzynierka.mkufunzi.models.User;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        getSupportActionBar().hide();
        User user = new Select()
                .from(User.class)
                .executeSingle();
        if(user == null)
        {
            Intent intent = new Intent(this, WelcomePage.class);
            this.startActivity(intent);
            this.finish();
        }
    }

    public void trainingPageShow(View view){

    }

    public void cartPageShow(View view){

    }

    public void logOut(View view){
        new UsersController().clearUsers();
        Intent intent = new Intent(this, WelcomePage.class);
        this.startActivity(intent);
        this.finish();
    }
}

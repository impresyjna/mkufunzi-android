package pl.com.inzynierka.mkufunzi.controllers.users_controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import pl.com.inzynierka.mkufunzi.API.users.LoginMobile;
import pl.com.inzynierka.mkufunzi.API.users.RegisterMobile;
import pl.com.inzynierka.mkufunzi.R;

public class RegisterController extends AppCompatActivity {

    private EditText loginInput;
    private EditText nameInput;
    private EditText surnameInput;
    private EditText passwordInput;
    private EditText passwordConfirmationInput;
    private EditText emailInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginInput = (EditText) findViewById(R.id.login_input);
        nameInput = (EditText) findViewById(R.id.name_input);
        surnameInput = (EditText) findViewById(R.id.surname_input);
        passwordInput = (EditText) findViewById(R.id.password_input);
        passwordConfirmationInput = (EditText) findViewById(R.id.password_confirmation_input);
        emailInput = (EditText) findViewById(R.id.email_input);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void register(View view){

        String login = loginInput.getText().toString();
        String name = nameInput.getText().toString();
        String surname = surnameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String passwordConfirmation = passwordConfirmationInput.getText().toString();
        String email = emailInput.getText().toString();
        RegisterMobile registerMobile = new RegisterMobile();
        registerMobile.setActivity(this);
        registerMobile.execute(login, name, surname, password, passwordConfirmation, email);
    }

}

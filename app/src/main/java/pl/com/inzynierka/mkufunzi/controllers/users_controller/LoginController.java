package pl.com.inzynierka.mkufunzi.controllers.users_controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import pl.com.inzynierka.mkufunzi.API.users.LoginMobile;
import pl.com.inzynierka.mkufunzi.R;

public class LoginController extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailInput = (EditText) findViewById(R.id.email_input);
        passwordInput = (EditText) findViewById(R.id.password_input);
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

    public void login(View view) {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        LoginMobile loginMobile = new LoginMobile();
        loginMobile.setActivity(this);
        loginMobile.execute(email,password);
    }
}

package pl.com.inzynierka.mkufunzi.controllers.views_controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.com.inzynierka.mkufunzi.API.users.LoginMobile;
import pl.com.inzynierka.mkufunzi.R;

public class Login extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private TextInputLayout inputLayoutEmail;
    private TextInputLayout inputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailInput = (EditText) findViewById(R.id.email_input);
        passwordInput = (EditText) findViewById(R.id.password_input);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
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
        if(!validateEmail())
        {
            return;
        }
        if(!validatePassword())
        {
            return;
        }
        else {
            LoginMobile loginMobile = new LoginMobile();
            loginMobile.setActivity(this);
            loginMobile.execute(email, password);
        }
    }

    public void showAbout(View view)
    {
        Log.i("About", "Opening about page activity ");
        Intent intent = new Intent(this, About.class);
        this.startActivity(intent);
    }

    public void showRegister(View view)
    {
        Log.i("Register", "Opening register page activity");
        Intent intent = new Intent(this, Register.class);
        this.startActivity(intent);
    }

    public boolean validatePassword(){
        String password = passwordInput.getText().toString().trim();
        if (password.isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password_empty));
            requestFocus(emailInput);
            return false;
        } else if(password.length()<6) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password_short));
            requestFocus(emailInput);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validateEmail(){
        String email = emailInput.getText().toString().trim();
        if (email.isEmpty() || !isEmailValid(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(emailInput);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


}

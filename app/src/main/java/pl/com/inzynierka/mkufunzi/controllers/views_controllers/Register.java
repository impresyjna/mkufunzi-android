package pl.com.inzynierka.mkufunzi.controllers.views_controllers;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.com.inzynierka.mkufunzi.API.users.RegisterMobile;
import pl.com.inzynierka.mkufunzi.R;

public class Register extends AppCompatActivity {

    private EditText loginInput, nameInput, surnameInput, passwordInput, passwordConfirmationInput, emailInput;
    private TextInputLayout inputLayoutLogin, inputLayoutName, inputLayoutSurname, inputLayoutPassword;
    private TextInputLayout inputLayoutPasswordConfirmation, inputLayoutEmail;

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

        inputLayoutLogin = (TextInputLayout) findViewById(R.id.input_layout_login);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutSurname = (TextInputLayout) findViewById(R.id.input_layout_surname);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutPasswordConfirmation = (TextInputLayout) findViewById(R.id.input_layout_password_confirmation);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
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

    /**
     * Method called by button in view. It reads data from view and check if they are valid
     * @param view
     */
    public void register(View view){
        String login = loginInput.getText().toString();
        String name = nameInput.getText().toString();
        String surname = surnameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String passwordConfirmation = passwordConfirmationInput.getText().toString();
        String email = emailInput.getText().toString();
        if(!validateLogin()){
            return;
        }
        if(!validateName())
        {
            return;
        }
        if(!validateSurname())
        {
            return;
        }
        if(!validatePassword()){
            return;
        }
        if(!validatePasswordConfirmation()){
            return;
        }
        if(!validatePasswords())
        {
            return;
        }
        if(!validateEmail())
        {
            return;
        }
        /** Try to register user if data is valid */
        else {
            RegisterMobile registerMobile = new RegisterMobile();
            registerMobile.setActivity(this);
            registerMobile.execute(login, name, surname, password, passwordConfirmation, email);
        }
    }

    public boolean validateLogin(){
        String login = loginInput.getText().toString().trim();
        if(login.equals(""))
        {
            inputLayoutLogin.setError(getString(R.string.err_msg_login_empty));
            requestFocus(loginInput);
            return false;
        } else {
            inputLayoutLogin.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validateName(){
        String name = nameInput.getText().toString().trim();
        if(name.equals(""))
        {
            inputLayoutName.setError(getString(R.string.err_msg_name_empty));
            requestFocus(nameInput);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validateSurname(){
        String surname = surnameInput.getText().toString().trim();
        if(surname.equals(""))
        {
            inputLayoutSurname.setError(getString(R.string.err_msg_surname_empty));
            requestFocus(surnameInput);
            return false;
        } else {
            inputLayoutSurname.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validatePassword(){
        String password = passwordInput.getText().toString();
        if(password.equals(""))
        {
            inputLayoutPassword.setError(getString(R.string.err_msg_password_empty));
            requestFocus(passwordInput);
            return false;
        } else if(password.length()<6) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password_short));
            requestFocus(passwordInput);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validatePasswordConfirmation(){
        String passwordConfirmation = passwordConfirmationInput.getText().toString();
        if(passwordConfirmation.equals(""))
        {
            inputLayoutPasswordConfirmation.setError(getString(R.string.err_msg_password_confirmation_empty));
            requestFocus(passwordConfirmationInput);
            return false;
        } else {
            inputLayoutPasswordConfirmation.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validatePasswords(){
        String password = passwordInput.getText().toString();
        String passwordConfirmation = passwordConfirmationInput.getText().toString();
        if(!password.equals(passwordConfirmation))
        {
            inputLayoutPasswordConfirmation.setError(getString(R.string.err_msg_passwords_do_not_fit));
            requestFocus(passwordConfirmationInput);
            return false;
        } else {
            inputLayoutPasswordConfirmation.setErrorEnabled(false);
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

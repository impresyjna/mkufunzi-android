package pl.com.inzynierka.mkufunzi.controllers.users_controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        /** Check if login is empty */
        if(login.equals(""))
        {
            Toast.makeText(this, "Podaj login", Toast.LENGTH_SHORT).show();
        }
        /** Check if name is empty */
        else if(name.equals(""))
        {
            Toast.makeText(this, "Podaj imię", Toast.LENGTH_SHORT).show();
        }
        /** Check if surname is empty */
        else if(surname.equals(""))
        {
            Toast.makeText(this, "Podaj nazwisko", Toast.LENGTH_SHORT).show();
        }
        /** Check if password is empty */
        else if(password.equals(""))
        {
            Toast.makeText(this, "Hasło nie może być puste", Toast.LENGTH_SHORT).show();
        }
        /** Check if passwordConfirmation is empty */
        else if(passwordConfirmation.equals(""))
        {
            Toast.makeText(this, "Należy potwierdzić hasło", Toast.LENGTH_SHORT).show();
        }
        /** Check if password has good length */
        else if(password.length()<6)
        {
            Toast.makeText(this, "Hasło jest za krótkie. Musi mieć co najmniej 6 znaków", Toast.LENGTH_SHORT).show();
        }
        /** Check if password and confirmation fit */
        else if(!password.equals(passwordConfirmation))
        {
            Toast.makeText(this, "Hasła się nie zgadzają", Toast.LENGTH_SHORT).show();
        }
        /** Check if email is empty */
        else if(email.equals(""))
        {
            Toast.makeText(this, "Podaj email", Toast.LENGTH_SHORT).show();
        }
        /** Check if email is valid */
        else if(!isEmailValid(email))
        {
            Toast.makeText(this, "Podany email nie jest poprawny", Toast.LENGTH_SHORT).show();
        }
        /** Try to register user if data is valid */
        else {
            RegisterMobile registerMobile = new RegisterMobile();
            registerMobile.setActivity(this);
            registerMobile.execute(login, name, surname, password, passwordConfirmation, email);
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

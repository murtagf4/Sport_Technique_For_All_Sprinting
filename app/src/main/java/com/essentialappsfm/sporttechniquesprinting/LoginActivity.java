package com.essentialappsfm.sporttechniquesprinting;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity {

    EditText loginField1;
    EditText loginField2;
    String stringLogin1;
    String stringLogin2;

    EditText createUsername;
    EditText createPassword1;
    EditText createPassword2;
    String stringUsername;
    String stringPassword1;
    String stringPassword2;

    boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginField1 = (EditText) findViewById(R.id.usernameInput);
        loginField2 = (EditText) findViewById(R.id.passwordInput);
        createUsername = (EditText) findViewById(R.id.usernameNewInput);
        createPassword1 = (EditText) findViewById(R.id.passwordNewInput);
        createPassword2 = (EditText) findViewById(R.id.passwordConfirmInput);
    }

    public void login(View view){
        stringLogin1 = loginField1.getText().toString();
        stringLogin2 = loginField2.getText().toString();

        if(stringLogin1.matches("") || stringLogin2.matches(""))
            Toast.makeText(getApplicationContext(), "You did not fill in all the credentials", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            isLoggedIn = true;
            Intent sendLogin = new Intent();
            sendLogin.putExtra("createUser", stringLogin1);
            sendLogin.putExtra("createPassword", stringLogin2);
            sendLogin.putExtra("logged", isLoggedIn);
            setResult(RESULT_OK, sendLogin);
            finish();
        }
    }

    public void create(View view){
        stringUsername = createUsername.getText().toString();
        stringPassword1 = createPassword1.getText().toString();
        stringPassword2 = createPassword2.getText().toString();

        if(stringUsername.matches("") || stringPassword1.matches("") || stringPassword2.matches(""))
            Toast.makeText(getApplicationContext(), "You did not fill in all the credentials", Toast.LENGTH_SHORT).show();
        else if(!stringPassword1.equals(stringPassword2))
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(getApplicationContext(), "Account Created Sucessfully", Toast.LENGTH_SHORT).show();
            isLoggedIn = true;
            Intent sendAccount = new Intent();
            sendAccount.putExtra("createUser", stringUsername);
            sendAccount.putExtra("createPassword", stringPassword1);
            sendAccount.putExtra("logged", isLoggedIn);
            setResult(RESULT_OK, sendAccount);
            finish();
        }

    }
}

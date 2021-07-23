package com.example.wheeldeal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wheeldeal.MainActivity;
import com.example.wheeldeal.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    public static LoginActivity instance = null;

    private EditText etUsername, etPassword;
    private Button btnLogin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instance = this;

        // check if user is already logged in
        if (ParseUser.getCurrentUser()!=null){
            goMainActivity(false);
        }
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick sign up button");
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                // go to signup activity
                startActivity(i);
                // finish() is not called such that user can go back to login if sign up not complete
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Trying to login user " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null){
                    Log.e(TAG, "login issue", e);
                    Toast.makeText(LoginActivity.this,"Wrong username or password", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    goMainActivity(true);
                    Toast.makeText(LoginActivity.this,"Login success", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goMainActivity(boolean fromLogin) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("flag", fromLogin);
        startActivity(i);
        // allows back button not to lead us back to login
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }
}
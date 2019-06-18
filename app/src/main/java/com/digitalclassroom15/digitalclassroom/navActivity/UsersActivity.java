package com.digitalclassroom15.digitalclassroom.navActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digitalclassroom15.digitalclassroom.R;
import com.digitalclassroom15.digitalclassroom.userPanel.UserViewActivity;
import com.digitalclassroom15.digitalclassroom.userSession.UserSessionManager2;

public class UsersActivity extends AppCompatActivity {
    Button btnLogin;

    EditText txtUsername, txtPassword;

    // User Session Manager Class
    UserSessionManager2 session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);


        getSupportActionBar().setTitle("Users Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // User Session Manager
        session = new UserSessionManager2(getApplicationContext());

        // get Email, Password input text
        txtUsername = (EditText) findViewById(R.id.userNameET);
        txtPassword = (EditText) findViewById(R.id.passwordET);

        //  Toast.makeText(getApplicationContext(), "User Login Status: " + session.isUserLoggedIn(), Toast.LENGTH_LONG).show();


        // User Login button
        btnLogin = (Button) findViewById(R.id.loginBtn);


        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Get username, password from EditText
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                // Validate if username, password is filled
                if (username.trim().length() > 0 && password.trim().length() > 0) {

                    // For testing puspose username, password is checked with static data
                    // username = admin
                    // password = admin

                    if (username.equals("user") && password.equals("user")) {

                        // Creating user login session
                        // Statically storing name="Android Example"
                        // and email="androidexample84@gmail.com"
                        session.createUserLoginSession("Developed By:",
                                "Md. Mahmudul Hasan");

                        // Starting AdminProfileActivity
                        Intent i = new Intent(getApplicationContext(), UserViewActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                        finish();

                    } else {

                        // username / password doesn't match&
                        Toast.makeText(getApplicationContext(),
                                "Username/Password is incorrect",
                                Toast.LENGTH_LONG).show();

                    }
                } else {

                    // user didn't entered username or password
                    Toast.makeText(getApplicationContext(),
                            "Please enter username and password",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    // Back Button Work
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
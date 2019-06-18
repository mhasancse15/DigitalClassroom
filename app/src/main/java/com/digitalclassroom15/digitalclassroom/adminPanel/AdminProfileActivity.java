package com.digitalclassroom15.digitalclassroom.adminPanel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.digitalclassroom15.digitalclassroom.R;
import com.digitalclassroom15.digitalclassroom.userSession.UserSessionManager;

public class AdminProfileActivity extends AppCompatActivity {

    // User Session Manager Class
    UserSessionManager session;
    // Button Logout
    Button btnLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

// Get Type user name
        getSupportActionBar().setTitle("Admin Panel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());
/*
        TextView lblName = (TextView) findViewById(R.id.lblName);
        TextView lblEmail = (TextView) findViewById(R.id.lblEmail);*/

        // Button logout
        btnLogout = (Button) findViewById(R.id.logoutBtn);

      //  Toast.makeText(getApplicationContext(),"User Login Status: " + session.isUserLoggedIn(), Toast.LENGTH_LONG).show();

        // Check user login (this is the important point)
        // If User is not logged in , This will redirect user to AdminActivity
        // and finish current activity from activity stack.
        if(session.checkLogin())
            finish();

/*        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get name
        String name = user.get(UserSessionManager.KEY_NAME);

        // get email
        String email = user.get(UserSessionManager.KEY_EMAIL);

        // Show user data on activity
        lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
        lblEmail.setText(Html.fromHtml("Email: <b>" + email + "</b>"));*/

        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Clear the User session data
                // and redirect user to AdminActivity
                session.logoutUser();
                finish();
            }
        });
    }

    public void goToUpload(View view) {
        Intent i=new Intent(AdminProfileActivity.this,UploadActivity.class);
        startActivity(i);
    }

    public void goToView(View view) {
        Intent i=new Intent(AdminProfileActivity.this,AdminViewActivity.class);
        startActivity(i);
    }

    // Back Button Work
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
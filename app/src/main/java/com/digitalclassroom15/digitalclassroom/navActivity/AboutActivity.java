package com.digitalclassroom15.digitalclassroom.navActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.digitalclassroom15.digitalclassroom.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        getSupportActionBar().setTitle("About Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Back Button Work
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}

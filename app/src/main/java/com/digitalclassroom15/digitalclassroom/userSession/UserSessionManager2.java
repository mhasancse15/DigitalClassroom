package com.digitalclassroom15.digitalclassroom.userSession;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.digitalclassroom15.digitalclassroom.adminPanel.AdminProfileActivity;
import com.digitalclassroom15.digitalclassroom.navActivity.UsersActivity;

import java.util.HashMap;

/**
 * Created by mahmu on 3/4/2018.
 */

public class UserSessionManager2 {
    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "AndroidExamplePref1";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn1";

    // User name (make variable public to access from outside)
    public static final String KEY_USERNAME = "user_Name";

    // Email address (make variable public to access from outside)
    public static final String KEY_USEREMAIL = "user_Email";

    // Constructor
    public UserSessionManager2(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String user_name, String user_email){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_USERNAME, user_name);

        // Storing email in pref
        editor.putString(KEY_USEREMAIL, user_email);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, UsersActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        else{
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, AdminProfileActivity.class);

        }
        return false;
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user_name = new HashMap<String, String>();

        // user name
        user_name.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));

        // user email id
        user_name.put(KEY_USEREMAIL, pref.getString(KEY_USEREMAIL, null));

        // return user
        return user_name;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, UsersActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
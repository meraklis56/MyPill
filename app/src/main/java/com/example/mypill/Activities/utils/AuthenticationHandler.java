package com.example.mypill.Activities.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.mypill.R;

import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

public class AuthenticationHandler {
    private static final AuthenticationHandler ourInstance = new AuthenticationHandler();
    private Context context = GlobalApplication.getAppContext();
    private SharedPreferences.Editor editor;

    public static AuthenticationHandler getInstance() {
        return ourInstance;
    }

    private AuthenticationHandler() { }

    public boolean Login(char[] username, char[] password) {
        char[] expectedUsername = context.getResources().getString(R.string.usernameLogin).toCharArray();
        char[] expectedPassword = context.getResources().getString(R.string.passwordLogin).toCharArray();

        if (Arrays.equals(username, expectedUsername) && Arrays.equals(password, expectedPassword)) {
            // Save the logged status into SharedPreferences
            editor = context.getSharedPreferences("mySharedPrefs", MODE_PRIVATE).edit();
            editor.putBoolean("loggedIn", true);
            editor.apply();
            return true;
        } else {
            return false;
        }
    }

    public boolean Logout() {
        try {
            SharedPreferences prefs = context.getSharedPreferences("mySharedPrefs", MODE_PRIVATE);
            prefs.edit().remove("loggedIn").apply();
            return true;
        } catch (Exception e) {
            Log.e("AuthenticationHandler", e.getMessage());
            return false;
        }
    }
}

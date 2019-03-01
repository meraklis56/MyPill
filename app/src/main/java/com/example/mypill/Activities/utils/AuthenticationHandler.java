package com.example.mypill.Activities.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.mypill.R;

import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

/*
    This is a singleton class which is responsible to confirm if the credentials are
    correct or now. It communicates with the back-end server to validate them (possible
    to be implemented).

    But now, due to simplicity of this application, it is designed to be used by one person
    so the credentials are stored into a local file.

    Also due to testing reasons, for now always returns true. In the future, the validation
    will be executed before the return

    The usernames and the passwords are on purpose saved as char[], as it is explained here:
    https://stackoverflow.com/questions/8881291/why-is-char-preferred-over-string-for-passwords
 */

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

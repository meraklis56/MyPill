package com.example.mypill.Activities.loginScreen;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mypill.Activities.utils.GlobalApplication;
import com.example.mypill.R;

/*
    This is the class which is responsible to confirm if the credentials are
    correct or now. It communicates with the back-end server to validate them.

    Due to simplicity of this application, it is designed to be used by one person
    so the credentials are stored into a local file.

    Also due to testing reasons, for now always returns true. In the future, the validation
    will be executed before the return

    The usernames and the passwords are on purpose saved as char[], as it is explained here:
    https://stackoverflow.com/questions/8881291/why-is-char-preferred-over-string-for-passwords
 */

public class LoginController {

    Context context = GlobalApplication.getAppContext();
    SharedPreferences.Editor editor;

    public boolean login(char[] username, char[] password) {
        char[] expectedUsername = context.getResources().getString(R.string.usernameLogin).toCharArray();
        char[] expectedPassword = context.getResources().getString(R.string.passwordLogin).toCharArray();

        // Save the logged status into SharedPreferences
        editor = context.getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE).edit();
        editor.putBoolean("loggedIn", true);
        editor.apply();

//        return Arrays.equals(username, expectedUsername) && Arrays.equals(password, expectedPassword);
        return true;
    }
}

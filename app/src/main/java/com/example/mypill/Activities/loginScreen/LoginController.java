package com.example.mypill.Activities.loginScreen;

import android.content.Context;

import com.example.mypill.Activities.utils.GlobalApplication;
import com.example.mypill.R;

/*
    This is the class which is responsible to confirm if the credentials are
    correct or now. It communicates with the back-end server to validate them.

    Due to simplicity of this application, the credentials are stored into a local file.

    Also due to testing reasons, for now always returns true. In the future, the validation
    will be executed before the return
 */

public class LoginController {

    Context context = GlobalApplication.getAppContext();

    public boolean login(char[] username, char[] password) {
        char[] expectedUsername = context.getResources().getString(R.string.usernameLogin).toCharArray();
        char[] expectedPassword = context.getResources().getString(R.string.passwordLogin).toCharArray();

//        return Arrays.equals(username, expectedUsername) && Arrays.equals(password, expectedPassword);
        return true;
    }
}

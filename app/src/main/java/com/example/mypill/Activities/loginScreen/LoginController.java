package com.example.mypill.Activities.loginScreen;


import com.example.mypill.Activities.utils.AuthenticationHandler;

public class LoginController {

    public boolean login(char[] username, char[] password) {
        return AuthenticationHandler.getInstance().Login(username, password);
    }
}

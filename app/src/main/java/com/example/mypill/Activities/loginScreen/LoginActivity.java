package com.example.mypill.Activities.loginScreen;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mypill.Activities.MainActivity;
import com.example.mypill.R;

import java.util.concurrent.CompletableFuture;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/*
    This is the View component of Login procedure. It is responsible to
    handle the layout and the user's events. Next, it communicates with the
    controller (LoginContoller.class), to ensure if the credentials are
    correct. If yes, it proceeds to the MainActivity
 */

public class LoginActivity extends AppCompatActivity {

    private TextView usernameTextView, passwordTextView;
    private CircularProgressButton signInButton;

    private Intent mainActivityIntent;

    private LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Login components initialization
        usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        passwordTextView = (TextView) findViewById(R.id.passwordTextView);
        final CircularProgressButton signInButton = (CircularProgressButton) findViewById(R.id.signInButton);

        // next activity initialization
        mainActivityIntent = new Intent(this, MainActivity.class);

        loginController = new LoginController();

        signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signInButton.startAnimation();
                char[] username = usernameTextView.getText().toString().toCharArray();
                char[] password = passwordTextView.getText().toString().toCharArray();
                CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                    boolean result = loginController.login(username, password);
                    return result;
                });

                try {
                    if (future.get()) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                signInButton.doneLoadingAnimation(Color.parseColor("#0288D1"), BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                        R.drawable.check));

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(mainActivityIntent);
                                    }
                                }, 500);
                            }
                        }, 1000);
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                signInButton.revertAnimation();
                            }
                        }, 1000);
                    }
                } catch (Exception e) {
                    Log.e("LoginActivity", e.getMessage());
                }
            }
            });
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            signInButton.dispose();
        } catch (Exception e) {
            Log.e("LoginActivity", e.getMessage());
        }
        LoginActivity.this.finish();// To prevent user to go back to LoginActivity pressing the back button.
    }
}

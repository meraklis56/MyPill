package com.example.mypill;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {

    private CircularProgressButton signInButton;
    private Intent mainActivityIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mainActivityIntent = new Intent(this, MainActivity.class);

        final CircularProgressButton signInButton = (CircularProgressButton) findViewById(R.id.signInButton);
        signInButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signInButton.startAnimation();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO password checking

                        signInButton.doneLoadingAnimation(Color.parseColor("#0288D1"), BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                R.drawable.check));

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(mainActivityIntent);
                            }
                        }, 500);

                    }}, 1000);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            signInButton.dispose();
        } catch (Exception e) {
            Log.e("LoadingButton", e.getMessage());
        }
        LoginActivity.this.finish();
    }
}

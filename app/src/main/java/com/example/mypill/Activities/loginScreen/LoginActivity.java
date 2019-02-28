package com.example.mypill.Activities.loginScreen;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mypill.Activities.mainScreen.MainActivity;
import com.example.mypill.R;

import java.util.concurrent.CompletableFuture;

import androidx.appcompat.app.AppCompatActivity;
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

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
    private SharedPreferences prefs;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Login components initialization
        usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        passwordTextView = (TextView) findViewById(R.id.passwordTextView);
        CircularProgressButton signInButton = (CircularProgressButton) findViewById(R.id.signInButton);

        // next activity initialization
        mainActivityIntent = new Intent(this, MainActivity.class);

        loginController = new LoginController();

        prefs = getSharedPreferences("mySharedPrefs", MODE_PRIVATE);
        if (prefs.getBoolean("loggedIn", false)) {
            startActivity(mainActivityIntent);
        }

        // The animation was created using After Effects and the
        // Lottie, a library which enables the export of animation from AE
        // into a json file and then animating natively in Android
        animationView = (LottieAnimationView) findViewById(R.id.logoHolder);

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                animationView.setMinFrame(180);
                // set the animation to start from the 3rd second - [60fps]
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signInButton.startAnimation();
                char[] username = usernameTextView.getText().toString().toCharArray();
                char[] password = passwordTextView.getText().toString().toCharArray();
                CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                    boolean result = loginController.login(username, password);
                    System.out.println("username: " + username);
                    System.out.println("password: " + password);
                    return result;
                });

                try {
                    boolean result = future.get();
                    Log.i("Login()", "" + result);
                    if (result) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                signInButton.doneLoadingAnimation(Color.parseColor("#0288D1"), drawableToBitmap(getDrawable(R.drawable.check)));

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(mainActivityIntent);
                                    }
                                }, 1000);
                            }
                        }, 1000);
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Toast.makeText(getBaseContext(),getString(R.string.wrongLogin), Toast.LENGTH_SHORT).show();
                                signInButton.revertAnimation(new Function0<Unit>() {
                                    @Override
                                    public Unit invoke() {
                                        return null;
                                    }
                                });
                            }
                        }, 1000);
                    }
                } catch (Exception e) {
                    Log.e("LoginActivity", e.getMessage());
                }
            }});
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

    public static Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}

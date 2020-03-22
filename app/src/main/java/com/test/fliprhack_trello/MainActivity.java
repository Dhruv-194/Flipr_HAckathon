package com.test.fliprhack_trello;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //our splash screen views

    private TextView mainTitle;

    /**
     * Let's initialize our widgets.
     */
    private void initializeWidgets() {

        mainTitle = findViewById(R.id.SplashR);

    }
    /**
     * Let's show our Splash animation using Animation class. We fade in our widgets.
     */
    private void showSplashAnimation() {


        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        mainTitle.startAnimation(fadeIn);

    }

    /**
     * Let's go to our DashBoard after 2 seconds
     */
    private void goToDashboard() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Utlis.openActivity(MainActivity.this, DashboardActivity.class);
                    finish();
                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    /**
     * Let's Override attachBaseContext method
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    /**
     * Let's create our onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeWidgets();
        this.showSplashAnimation();
        this.goToDashboard();
    }

}

package com.lmt.sawn.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lmt.sawn.Adapter.CountryAdapter;
import com.lmt.sawn.MApplication;
import com.lmt.sawn.R;
import com.victor.loading.rotate.RotateLoading;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity {

    RotateLoading rtLoading;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

/*        hTextView = (HTextView) findViewById(R.id.text);
        hTextView.setAnimateType(HTextViewType.RAINBOW);
        hTextView.animateText("الصوم");
        */
        rtLoading = (RotateLoading) findViewById(R.id.rotateloading);
        rtLoading.start();

        MApplication application = (MApplication) getApplication();
        MApplication.application = application;

        startTime = System.currentTimeMillis();

        if (application.isFirstTime()) {

            long processTime = System.currentTimeMillis() - startTime;

            if (processTime >= 3000) {

                startActivity(new Intent(SplashActivity.this, CountryActivity.class));
                finish();

            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //showMainActivity();
                        startActivity(new Intent(SplashActivity.this, CountryActivity.class));
                        finish();

                    }
                }, 3000 - processTime);
            }

        } else {

            long processTime = System.currentTimeMillis() - startTime;

            if (processTime >= 3000) {

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();

            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();

                    }
                }, 3000 - processTime);
            }


        }


        /*FontUtils utils=new FontUtils(this);
        utils.setTypeFace(Fonts.NOTO_REGULAR,title_mm);*/


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

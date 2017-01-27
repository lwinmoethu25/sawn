package com.lmt.sawn.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lmt.sawn.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by lwinmoethu on 5/28/16.
 */
public class DetailActivity extends AppCompatActivity {

    String day,date,sehri,iftari;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        Intent i=getIntent();

        day=i.getStringExtra("day");
        date=i.getStringExtra("date");
        sehri=i.getStringExtra("sehri");
        iftari=i.getStringExtra("iftari");


        // Set title of Detail page
        collapsingToolbar.setTitle("Day "+day);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/jflat-Regular.ttf");
        collapsingToolbar.setCollapsedTitleTypeface(tf);
        collapsingToolbar.setExpandedTitleTypeface(tf);

        TextView txtDate=(TextView) findViewById(R.id.txtDate);
        txtDate.setText(date);

        TextView txtSehri = (TextView) findViewById(R.id.sehri);
        txtSehri.setText(sehri);

        TextView txtIftari =  (TextView) findViewById(R.id.iftari);
        txtIftari.setText(iftari);

        ImageView imgHeader = (ImageView) findViewById(R.id.image);
        imgHeader.setImageResource(R.drawable.wp_detail);



    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

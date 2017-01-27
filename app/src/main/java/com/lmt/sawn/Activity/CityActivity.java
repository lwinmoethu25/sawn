package com.lmt.sawn.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lmt.sawn.Adapter.CityAdapter;
import com.lmt.sawn.Adapter.CountryAdapter;
import com.lmt.sawn.Adapter.DivisionAdapter;
import com.lmt.sawn.Database.DBHelper;
import com.lmt.sawn.MApplication;
import com.lmt.sawn.Model.Constant;
import com.lmt.sawn.Model.Header;
import com.lmt.sawn.Model.Location;
import com.lmt.sawn.R;
import com.lmt.sawn.Utils.CheckNetwork;
import com.lmt.sawn.Utils.JsonParser;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by lwinmoethu on 5/26/16.
 */
public class CityActivity extends AppCompatActivity {

    TextView txtWarning,txtTitle;
    String selectedDivision;
    Button btnNext;
    List<Location> cityList;
    DBHelper db;
    RecyclerView city_list;
    CheckNetwork checkNetwork;
    private RecyclerView.LayoutManager mLayoutManager;
    MApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        btnNext = (Button) findViewById(R.id.btnNext);
        txtTitle=(TextView) findViewById(R.id.txtTitle);
        txtWarning = (TextView) findViewById(R.id.txtWarning);
        city_list = (RecyclerView) findViewById(R.id.dataList);

        Toolbar mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        mytoolbar.setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitle.setText(R.string.choose_city);
        btnNext.setText("FINISH");

        application = (MApplication) getApplication();
        MApplication.application = application;

        selectedDivision=application.getStringSharedPreferrence("division");

        db = new DBHelper(getApplicationContext());
        cityList = db.getCityList(selectedDivision);
        CityAdapter cityAdapter = new CityAdapter(cityList,application,getApplicationContext());
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        city_list.setLayoutManager(mLayoutManager);
        city_list.setAdapter(cityAdapter);
        city_list.setNestedScrollingEnabled(false);

    }

    public void clickNext(View v) {

        if(CityAdapter.city_pos!=-1){
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }else{
            Toast.makeText(this,"Choose your city",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}

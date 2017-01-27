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
 * Created by lwinmoethu on 5/27/16.
 */
public class DivisionActivity extends AppCompatActivity {

    TextView txtWarning,txtTitle;
    String selectedCountry,selectedUrl;
    Button btnNext;
    CardView btnCard;
    List<Location> divList;
    DBHelper db;
    RecyclerView div_list;
    CheckNetwork checkNetwork;
    private RecyclerView.LayoutManager mLayoutManager;
    Boolean downloaded;
    MApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnCard= (CardView) findViewById(R.id.cardBtn);
        txtTitle=(TextView) findViewById(R.id.txtTitle);
        txtWarning = (TextView) findViewById(R.id.txtWarning);
        div_list = (RecyclerView) findViewById(R.id.dataList);

        Toolbar mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        mytoolbar.setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitle.setText(R.string.choose_division);

        application = (MApplication) getApplication();
        MApplication.application = application;

        selectedCountry=application.getStringSharedPreferrence("country");
        selectedUrl=application.getStringSharedPreferrence("url");

        checkNetwork = new CheckNetwork();

        db = new DBHelper(getApplicationContext());
        db.resetTables();
        downloaded = false;
        downloadData();

    }

    public void downloadData() {

        if (checkNetwork.isNetworkAvailable(getApplicationContext())) {
            txtWarning.setVisibility(View.GONE);
            btnNext.setBackgroundColor(Color.parseColor("#009688"));
            btnNext.setText("NEXT");
            btnNext.setVisibility(View.INVISIBLE);
            btnCard.setVisibility(View.INVISIBLE);
            new DownloadJson().execute();
        } else {
            noConnection();
        }
    }

    public void noConnection() {

        txtWarning.setVisibility(View.VISIBLE);
        btnNext.setBackgroundColor(Color.RED);
        btnNext.setText("Retry");
    }

    public void clickNext(View v) {
        if (downloaded) {
            if(DivisionAdapter.division_pos!=-1){
                startActivity(new Intent(getApplicationContext(),CityActivity.class));
            }else{
                Toast.makeText(this,"Choose your division",Toast.LENGTH_SHORT).show();
            }

        } else {
            downloadData();
        }


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private class DownloadJson extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog pDialog;
        String id, test, country, code, division, location, town, day, open, close, url;
        JsonParser jsonParser = new JsonParser();
        JSONObject feed;
        RotateLoading rtLoading;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rtLoading = (RotateLoading) findViewById(R.id.rotateloading);
            rtLoading.setVisibility(View.VISIBLE);
            rtLoading.start();

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            JSONObject json = null;
            JSONObject json_ct, cu, json_url;
            JSONArray date;
            JSONArray rarray = null;
            HashMap<String, String> params = new HashMap<>();
            //params.put("tag", "login");
            json = jsonParser.makeHttpRequest(selectedUrl, "GET", params);

            try {


                feed=json.getJSONObject("feed");

                rarray = feed.getJSONArray("entry");

                for (int i = 0; i < rarray.length(); i++) {

                    JSONObject c = rarray.getJSONObject(i);

                    json_ct= c.getJSONObject("title");
                    test=json_ct.getString("$t");

                    JSONObject jsonObject=new JSONObject(test);

                    id=jsonObject.getString("id");
                    country=jsonObject.getString("country");
                    division=jsonObject.getString("division");
                    location=jsonObject.getString("location");
                    town=jsonObject.getString("town");

                    db.addLocation(id,country,division,location,town);

                    date=jsonObject.getJSONArray("data");

                    for(int j=0;j<date.length();j++){


                        JSONObject d= date.getJSONObject(j);
                        day=d.getString("day");
                        close=d.getString("close");
                        open=d.getString("open");

                        db.addTimeTable(id, day, close, open);
                        //Log.i("Date ",day+" "+close+" "+open);


                    }




                    // Log.i("Data ",id );

                    //Log.i("Title ",ct.toString());
                    //Log.i("Test ",id+" "+country+" "+division+" "+location+" "+town);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("JSON Result ", json.toString());
            //pref.edit().putString("token",token).commit();


            return json;

        }

        @Override
        protected void onPostExecute(JSONObject json) {

            rtLoading.stop();
            rtLoading.setVisibility(View.GONE);

            if (json != null) {
                //Toast.makeText(getApplicationContext(), "Json Getting", Toast.LENGTH_SHORT).show();
                divList = db.getDivisionList(null);
                DivisionAdapter divisionAdapter = new DivisionAdapter(divList,application,getApplicationContext());
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                div_list.setLayoutManager(mLayoutManager);
                div_list.setAdapter(divisionAdapter);
                div_list.setNestedScrollingEnabled(false);
                btnNext.setVisibility(View.VISIBLE);
                btnCard.setVisibility(View.VISIBLE);
                downloaded = true;

            } else {
                Toast.makeText(getApplicationContext(), "Json Failure", Toast.LENGTH_SHORT).show();
            }

        }
    }


}

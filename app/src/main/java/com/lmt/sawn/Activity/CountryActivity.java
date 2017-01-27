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

import com.lmt.sawn.Adapter.CountryAdapter;
import com.lmt.sawn.Database.DBHelper;
import com.lmt.sawn.MApplication;
import com.lmt.sawn.Model.Constant;
import com.lmt.sawn.Model.Header;
import com.lmt.sawn.R;
import com.lmt.sawn.Utils.CheckNetwork;
import com.lmt.sawn.Utils.JsonParser;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by lwinmoethu on 5/22/16.
 */
public class CountryActivity extends AppCompatActivity {

    TextView txtWarning,txtTitle;
    Button btnNext;
    CardView btnCard;
    List<Header> countryList;
    DBHelper db;
    RecyclerView country_list;
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
        country_list = (RecyclerView) findViewById(R.id.dataList);

        Toolbar mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        mytoolbar.setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        application = (MApplication) getApplication();
        MApplication.application = application;


        txtTitle.setText(R.string.choose_country);

        checkNetwork = new CheckNetwork();

        db = new DBHelper(getApplicationContext());
        db.resetHeaderTable();
        downloaded = false;
        downloadData();

       /* FontUtils utils=new FontUtils(getApplicationContext());
        utils.setTypeFace(Fonts.MY_MM,txtTitle);*/


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
            if(CountryAdapter.country_pos!=-1){
                startActivity(new Intent(getApplicationContext(),DivisionActivity.class));
            }else{
                Toast.makeText(this,"Choose your country",Toast.LENGTH_SHORT).show();
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
            json = jsonParser.makeHttpRequest(Constant.JSON_URL, "GET", params);

            try {


                feed = json.getJSONObject("feed");

                rarray = feed.getJSONArray("entry");

                for (int i = 0; i < rarray.length(); i++) {

                    JSONObject c = rarray.getJSONObject(i);

                    json_ct = c.getJSONObject("title");
                    country = json_ct.getString("$t");

                    json_url = c.getJSONObject("content");
                    url = json_url.getString("$t");
                    String[] parts=url.split(",");
                    code=parts[0];
                    url=parts[1];
                    code=code.replace("code: ","");
                    url=url.replace("url:","");

                    db.addHeader(country, code, url);

                    Log.i("Header ", country + " " + code + " " + url);
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
            btnNext.setVisibility(View.VISIBLE);
            btnCard.setVisibility(View.VISIBLE);
            //Toast.makeText(getApplicationContext(),code,Toast.LENGTH_SHORT).show();

            if (json != null) {
                //Toast.makeText(getApplicationContext(), "Json Getting", Toast.LENGTH_SHORT).show();
                countryList = db.getHeaderList(null);
                CountryAdapter countryAdapter = new CountryAdapter(countryList,application);
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                country_list.setLayoutManager(mLayoutManager);
                country_list.setAdapter(countryAdapter);
                country_list.setNestedScrollingEnabled(false);
                downloaded = true;

            } else {
                Toast.makeText(getApplicationContext(), "Json Failure", Toast.LENGTH_SHORT).show();
            }

        }
    }


}

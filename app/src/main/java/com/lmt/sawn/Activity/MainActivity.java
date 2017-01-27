package com.lmt.sawn.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lmt.sawn.Adapter.RamadamAdapter;
import com.lmt.sawn.Database.DBHelper;
import com.lmt.sawn.MApplication;
import com.lmt.sawn.Model.Location;
import com.lmt.sawn.R;

import org.mmaug.mmfont.utils.FontUtils;
import org.mmaug.mmfont.utils.Fonts;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by lwinmoethu on 5/28/16.
 */
public class MainActivity extends AppCompatActivity {

    RecyclerView rcRamadam;
    RecyclerView.LayoutManager layoutManager;
    MApplication application;
    DBHelper db;
    TextView txtTitle;
    String country,division,location,lid,code;
    List<Location> ramadamList;
    ImageView flagOne,flagTwo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcRamadam=(RecyclerView) findViewById(R.id.rcRamadam);
        txtTitle=(TextView) findViewById(R.id.txtTitle);
        flagOne=(ImageView) findViewById(R.id.flag1);
        flagTwo=(ImageView) findViewById(R.id.flag2);

        Toolbar mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        mytoolbar.setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        application = (MApplication) getApplication();
        MApplication.application = application;

        country=application.getStringSharedPreferrence("country");
        division=application.getStringSharedPreferrence("division");
        location=application.getStringSharedPreferrence("location");
        lid=application.getStringSharedPreferrence("city_pos");
        code=application.getStringSharedPreferrence("code");

        txtTitle.setText(country+","+division+","+location);
        setFlag(code);

        db = new DBHelper(getApplicationContext());
        ramadamList=db.getRamadamList(lid);

        RamadamAdapter cityAdapter = new RamadamAdapter(ramadamList,application);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rcRamadam.setLayoutManager(layoutManager);
        rcRamadam.setAdapter(cityAdapter);
        rcRamadam.setNestedScrollingEnabled(false);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setFlag(String code){

        switch (code){

            case "my":
                flagOne.setImageResource(R.drawable.my);
                flagTwo.setImageResource(R.drawable.my);

                break;

            case "mm":
                flagOne.setImageResource(R.drawable.mm);
                flagTwo.setImageResource(R.drawable.mm);
                break;

            case "sg":
                flagOne.setImageResource(R.drawable.sg);
                flagTwo.setImageResource(R.drawable.sg);
                break;

            case "th":
                flagOne.setImageResource(R.drawable.th);
                flagTwo.setImageResource(R.drawable.th);
                break;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.setting) {
            Toast.makeText(this,"Setting",Toast.LENGTH_SHORT).show();

            return true;
        }else if(id==R.id.about){
            Toast.makeText(this,"About",Toast.LENGTH_SHORT).show();


        }

        return super.onOptionsItemSelected(item);
    }

}

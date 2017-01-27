package com.lmt.sawn;

import android.app.Application;
import android.content.SharedPreferences;

import com.lmt.sawn.Model.Constant;
import com.lmt.sawn.Database.DBHelper;

import java.util.UUID;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by lwinmoethu on 5/21/16.
 */
public class MApplication extends Application {
    public static String id = null;
    private boolean isFirstTime = false;
    private SharedPreferences sharedPreference;
    public static MApplication application;
    public static DBHelper dbHelper;

    public boolean isFirstTime(){
        return isFirstTime;
    }

    public void setFirstTime(boolean firstTime){
        isFirstTime = firstTime;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/jflat-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        this.sharedPreference = this.getSharedPreferences(Constant.PREFERENCE_KEY, MODE_PRIVATE);
        ///dbHelper= new DBHelper(getApplicationContext());


        id = getStringSharedPreferrence(Constant.TAG_ID);

        //check first time or not
        if(id == null || id.isEmpty()){

            isFirstTime = true;
            initDefaultData();

        }
        else{
            isFirstTime = false;


        }

    }

    private void initDefaultData(){
        id = UUID.randomUUID().toString();
        saveDataToPreference();
    }

    public void saveDataToPreference(){
        saveStringSharedPreferrence(Constant.TAG_ID, id);
    }

    public void saveStringSharedPreferrence(String key,String value){
        sharedPreference.edit().putString(key,value).commit();
    }

    public String getStringSharedPreferrence(String key){
        return sharedPreference.getString(key, "");
    }

    public void saveIntSharedPreferrence(String key,int value){
        sharedPreference.edit().putInt(key,value).commit();
    }

    public int getIntSharedPreferrence(String key){
        return sharedPreference.getInt(key, 0);
    }

    public void clearSharedPreference(String key){
        sharedPreference.edit().remove(key).commit();
    }

    public void clearSharedPreferrence(){
        sharedPreference.edit().clear().commit();
    }
}


package com.lmt.sawn.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lmt.sawn.Model.Constant;
import com.lmt.sawn.Model.Header;
import com.lmt.sawn.Model.Location;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwin on 10/27/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static String DB_PATH="/data/data/com.lmt.sawn/databases/";

    private static String DB_NAME="swiyam.db";

    String filepath=DB_PATH+DB_NAME;

    private static SQLiteDatabase DB_Helper;

    private Context mContext;



    public DBHelper(Context context) {
        super(context,DB_NAME,null, Constant.DATABASE_VERSION);
        this.mContext=context;

        File f=new File(filepath);

        if(f.exists()){

            Log.i("DataBase","Database Exist");
            openDataBase();
        }else {
            try{
                this.getReadableDatabase();
                copyDataBase();
                Log.i("DataBase", "DataBase copying finished");
                openDataBase();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }



    public void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput=mContext.getAssets().open(DB_NAME);
        Log.i("DB myInput : ",DB_NAME);

        //Path to the just created empty db
        String outFileName=DB_PATH+DB_NAME;
        Log.i("DB outFileName : ",outFileName);

        //Open the empty db as the output stream
        OutputStream myOutput=new FileOutputStream(outFileName);
        Log.i("DB myOutput : ",outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer=new byte[1024];
        int length;
        while((length=myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }
        Log.i("DB copy success : ",outFileName);

        //Close the sterams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }


    public void openDataBase() throws SQLException{

        //Open the database
        DB_Helper=SQLiteDatabase.openDatabase(filepath,null,SQLiteDatabase.OPEN_READONLY);
        Log.i("Database","Openig DataBase success");

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    private Header cursorToHeader(Cursor cursor){
        Header header = new Header();
        header.setCountryName(cursor.getString(cursor.getColumnIndex(Constant.KEY_COUNTRY_NAME)));
        header.setCountryCode(cursor.getString(cursor.getColumnIndex(Constant.KEY_COUNTRY_CODE)));
        header.setDownloadUrl(cursor.getString(cursor.getColumnIndex(Constant.KEY_URL)));
        return header;
    }

    private Location cursorToLocation(Cursor cursor){
        Location location = new Location();
        location.setLid(cursor.getString(cursor.getColumnIndex(Constant.KEY_ID)));
        location.setCountry(cursor.getString(cursor.getColumnIndex(Constant.KEY_COUNTRY)));
        location.setDivision(cursor.getString(cursor.getColumnIndex(Constant.KEY_DIVISION)));
        location.setLocation(cursor.getString(cursor.getColumnIndex(Constant.KEY_LOCATION)));
        location.setTown(cursor.getString(cursor.getColumnIndex(Constant.KEY_TOWN)));

        return location;
    }

    private Location cursorToRamadam(Cursor cursor){
        Location location = new Location();
        location.setDay(cursor.getString(cursor.getColumnIndex(Constant.KEY_DAY)));
        location.setClose(cursor.getString(cursor.getColumnIndex(Constant.KEY_CLOSE)));
        location.setOpen(cursor.getString(cursor.getColumnIndex(Constant.KEY_OPEN)));


        return location;
    }

    public List<Header> getHeaderList(String searchName){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM SWIYAM_HEADER ORDER BY country_name;";
        String []params = new String[]{};
       /* if(searchName != null && !searchName.isEmpty()){
            sql += "WHERE name like ? COLLATE NOCASE";
            params = new String[]{"%" + searchName + "%"};
        }*/
        Cursor cursor = db.rawQuery(sql, params);
        List<Header> headers = new ArrayList<Header>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Header header = cursorToHeader(cursor);
            headers.add(header);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        db.close();

        return headers;
    }


    public List<Location> getDivisionList(String searchName){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM SWIYAM GROUP BY division HAVING division>=1 ORDER BY division;";
        String []params = new String[]{};
       /* if(searchName != null && !searchName.isEmpty()){
            sql += "WHERE name like ? COLLATE NOCASE";
            params = new String[]{"%" + searchName + "%"};
        }*/
        Cursor cursor = db.rawQuery(sql, params);
        List<Location> locations = new ArrayList<Location>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Location location = cursorToLocation(cursor);
            locations.add(location);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        db.close();

        return locations;
    }


    public List<Location> getCityList(String division){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM SWIYAM WHERE division=? ORDER BY division;";

        String []params = new String[]{"" +division};
       /* if(searchName != null && !searchName.isEmpty()){
            sql += "WHERE name like ? COLLATE NOCASE";
            params = new String[]{"%" + searchName + "%"};
        }*/
        Cursor cursor = db.rawQuery(sql, params);
        List<Location> locations = new ArrayList<Location>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Location location = cursorToLocation(cursor);
            locations.add(location);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        db.close();

        return locations;
    }

    public List<Location> getRamadamList(String lid){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM TIMETABLE WHERE lid=? ORDER BY id;";

        String []params = new String[]{"" +lid};
       /* if(searchName != null && !searchName.isEmpty()){
            sql += "WHERE name like ? COLLATE NOCASE";
            params = new String[]{"%" + searchName + "%"};
        }*/
        Cursor cursor = db.rawQuery(sql, params);
        List<Location> locations = new ArrayList<Location>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Location location = cursorToRamadam(cursor);
            locations.add(location);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        db.close();

        return locations;
    }





    public void addLocation(String lid,String country, String division, String location, String town) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.KEY_ID, lid); // location ID
        values.put(Constant.KEY_COUNTRY, country); // Country
        values.put(Constant.KEY_DIVISION, division); // Division
        values.put(Constant.KEY_LOCATION, location); // Location
        values.put(Constant.KEY_TOWN, town); // Town

        // Inserting Row
        db.insert(Constant.TABLE_SWIYAM, null, values);
        Log.i("Table_Swiyam",lid+" "+country+" "+division+" "+location+" "+town);
        db.close(); // Closing database connection
    }


    public void addTimeTable(String lid,String day, String close, String open) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.KEY_LID, lid); // location ID
        values.put(Constant.KEY_DAY, day); // Day
        values.put(Constant.KEY_CLOSE, close); // Surhi
        values.put(Constant.KEY_OPEN, open); // Iftari

        // Inserting Row
        db.insert(Constant.TABLE_TIMETABLE, null, values);
       // Log.i("Time_Table",lid+" "+day+" "+close+" "+open);
        db.close(); // Closing database connection
    }

    public void addHeader(String name,String code,String url){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Constant.KEY_COUNTRY_NAME,name);
        values.put(Constant.KEY_COUNTRY_CODE,code);
        values.put(Constant.KEY_URL,url);

        db.insert(Constant.TABLE_SWIYAM_HEADER,null,values);
        db.close();
    }
    /**
     * Re create database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(Constant.TABLE_SWIYAM, null, null);
        db.delete(Constant.TABLE_TIMETABLE,null,null);
        db.close();
    }

    public void resetHeaderTable(){
        SQLiteDatabase db=this.getWritableDatabase();

        //Delete Header Table
        db.delete(Constant.TABLE_SWIYAM_HEADER,null,null);
        db.close();
    }

}

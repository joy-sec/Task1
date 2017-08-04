package net.apkkothon.tourkit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.apkkothon.tourkit.models.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 8/3/17.
 */

public class MyDBFunctions  extends SQLiteOpenHelper {

    private static final String DATABSE_NAME = "mydb";
    private static final String TABLE_NAME = "mytab";

    private static final String TAB_ID = "id";
    private static final String TAB_POST_ID = "post_id";
    private static final String TAB_NAME = "name";
    private static final String TAB_Details = "details";
    private static final String TAB_District = "district";
    private static final String TAB_IMAGE = "image";



    public MyDBFunctions(Context context) {
        super(context, DATABSE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String s = "CREATE TABLE "+TABLE_NAME+" ("+TAB_ID+" INTEGER PRIMARY KEY, "+TAB_NAME+" TEXT, "+TAB_Details+" TEXT, "+TAB_District+" TEXT, "+TAB_IMAGE+" TEXT, "+TAB_POST_ID+" TEXT)";
        db.execSQL(s);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // ---- ---- adding data to database --- -----


    public void addingDataToTable(Place dt){

        SQLiteDatabase sqd  = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TAB_NAME, dt.getPlaceName());
        cv.put(TAB_Details, dt.getPlaceDetails());
        cv.put(TAB_District,dt.getPlaceDistrict());
        cv.put(TAB_IMAGE,dt.getPlaceImage());
        cv.put(TAB_POST_ID,dt.getPostID());


        sqd.insert(TABLE_NAME, null, cv);
        sqd.close();

    }


    // --- ---- showing data ------ ----

    public List retriveData() {

        List<Place> list = new ArrayList<>();

        SQLiteDatabase sq = this.getReadableDatabase();

        String q = "SELECT * FROM " + TABLE_NAME;

        Cursor c = sq.rawQuery(q, null);

        c.moveToLast();

        if (c.moveToLast()) {
            int counter = 0;
            do {

                Place userModel = new Place();

                userModel.setPlaceName(c.getString(c.getColumnIndex(TAB_NAME)));
                userModel.setPlaceImage(c.getString(c.getColumnIndex(TAB_IMAGE)));
                userModel.setPlaceDistrict(c.getString(c.getColumnIndex(TAB_District)));
                userModel.setPlaceDetails(c.getString(c.getColumnIndex(TAB_Details)));
                userModel.setPostID(c.getString(c.getColumnIndex(TAB_POST_ID)));

                list.add(userModel);

                counter = counter + 1;

            } while (c.moveToPrevious());

        }

        return list;
    }


        /*
        String[] my_data() {

        SQLiteDatabase sq = this.getReadableDatabase();

        String q = "SELECT * FROM "+TABLE_NAME;

        Cursor c = sq.rawQuery(q, null);

        String[] recvied_data = new String[c.getCount()];

        c.moveToFirst();

        if(c.moveToFirst()){
            int counter = 0 ;
            do {
                recvied_data[counter] = c.getString(c.getColumnIndex(TAB_NAME+"")) +"\nBirthday: "+
                        c.getString(c.getColumnIndex(TAB_DAYS+""));
                counter = counter+1;

            } while(c.moveToNext());

        }

        return recvied_data;
    }



    String fetch_day(int id) {

        SQLiteDatabase sq = this.getReadableDatabase();

        String q = "SELECT "+TAB_DAYS+" FROM "+TABLE_NAME+" WHERE "+TAB_ID+" = "+id;

        Cursor c = sq.rawQuery(q, null);
        String s = "";

        c.moveToFirst();

        if(c.moveToFirst()) {
            s = c.getString(c.getColumnIndex(TAB_DAYS+""));
        }

        return s;

    }


    int update_birthday(int id, String bday) {

        SQLiteDatabase sq = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TAB_DAYS, bday);

        return sq.update(TABLE_NAME, cv, TAB_ID+" = ? ", new String[]{id+""});

    }

    */


    public int delete_data(String bday){

        SQLiteDatabase s = this.getWritableDatabase();
        return s.delete(TABLE_NAME, TAB_POST_ID+" = ?", new String[] {bday});

    }


}

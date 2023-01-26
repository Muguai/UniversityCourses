package com.example.GarbageBinTrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String BIN_TABLE = "BIN_TABLE";
    public static final String COLUMN_BIN_NAME = "BIN_NAME";
    public static final String COLUMN_BIN_TOPIC = "BIN_TOPIC";
    public static final String COLUMN_BIN_LASTPICKUP = "BIN_LASTPICKUP";
    public static final String COLUMN_BIN_PERCENT = "BIN_PERCENT";
    public static final String COLUMN_BIN_LATITUDE = "BIN_LATITUDE";
    public static final String COLUMN_BIN_LONGITUDE = "BIN_LONGITUDE";
    public static final String COLUMN_BIN_EMPTYBINVALUE = "BIN_EMPTYBINVALUE";
    public static final String COLUMN_BIN_AVGPICKUP = "BIN_AVGPICKUP";
    public static final String COLUMN_BIN_CALIBRATED = "BIN_CALIBRATED";
    public static final String COLUMN_BIN_NOTIFIED = "BIN_NOTIFIED";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "GarbageBin.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + BIN_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_BIN_NAME + " TEXT, " + COLUMN_BIN_TOPIC + " TEXT, " + COLUMN_BIN_LASTPICKUP + " TEXT, " + COLUMN_BIN_PERCENT + " REAL, " + COLUMN_BIN_LATITUDE + " REAL, " + COLUMN_BIN_LONGITUDE + " REAL, " + COLUMN_BIN_EMPTYBINVALUE + " REAL, " + COLUMN_BIN_AVGPICKUP + " REAL, " + COLUMN_BIN_CALIBRATED + " BOOL, " + COLUMN_BIN_NOTIFIED + " BOOL)";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean deleteOne(GarbageBin gb){

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + BIN_TABLE + " WHERE " + COLUMN_ID + " = " + gb.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean updateSpecificBin(GarbageBin gb){

        SQLiteDatabase db = this.getWritableDatabase();

        String strFilter = COLUMN_ID + "="+ (gb.getId());

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BIN_NAME, gb.getName());
        cv.put(COLUMN_BIN_TOPIC, gb.getTopic());
        cv.put(COLUMN_BIN_LASTPICKUP, gb.getLastPickupDate());
        cv.put(COLUMN_BIN_PERCENT, gb.getPercent());
        cv.put(COLUMN_BIN_LATITUDE, gb.getLatitude());
        cv.put(COLUMN_BIN_LONGITUDE, gb.getLongitude());
        cv.put(COLUMN_BIN_EMPTYBINVALUE, gb.getEmptyBinValue());
        cv.put(COLUMN_BIN_AVGPICKUP, gb.getAveragePickupProcentThisMonth());
        cv.put(COLUMN_BIN_CALIBRATED, gb.isCalibrated());
        cv.put(COLUMN_BIN_NOTIFIED, gb.isPickupNotification());


        long result = db.update(BIN_TABLE,cv, strFilter, null);
        if(result == -1) {
            return false;
        }else{
            return true;
        }

    }

    public boolean addOne(GarbageBin gb){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BIN_NAME, gb.getName());
        cv.put(COLUMN_BIN_TOPIC, gb.getTopic());
        cv.put(COLUMN_BIN_LASTPICKUP, gb.getLastPickupDate());
        cv.put(COLUMN_BIN_PERCENT, gb.getPercent());
        cv.put(COLUMN_BIN_LATITUDE, gb.getLatitude());
        cv.put(COLUMN_BIN_LONGITUDE, gb.getLongitude());
        cv.put(COLUMN_BIN_EMPTYBINVALUE, gb.getEmptyBinValue());
        cv.put(COLUMN_BIN_AVGPICKUP, gb.getAveragePickupProcentThisMonth());
        cv.put(COLUMN_BIN_CALIBRATED, gb.isCalibrated());
        cv.put(COLUMN_BIN_NOTIFIED, gb.isPickupNotification());

        long insert = db.insert(BIN_TABLE, null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }

    }

    public List<GarbageBin> getEveryBin() {

        List<GarbageBin> returnList = new ArrayList<>();

        String querySting = "SELECT * FROM " + BIN_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(querySting, null);

        if(cursor.moveToFirst()) {

            do{
                int Id = cursor.getInt(0);
                String name = cursor.getString(1);
                String topic = cursor.getString(2);
                String lastpickup = cursor.getString(3);
                float percent = cursor.getFloat(4);
                float latitude = cursor.getFloat(5);
                float longitude = cursor.getFloat(6);
                float emptyBinValue = cursor.getFloat(7);
                float avgPercent = cursor.getFloat(8);
                boolean calibrated = cursor.getInt(9) == 1 ? true: false;
                boolean notified = cursor.getInt(10) == 1 ? true: false;

                GarbageBin gb = new GarbageBin(Id, topic, name, longitude, latitude,percent);
                gb.setCalibrated(calibrated);
                gb.setPickupNotification(notified);
                gb.setEmptyBinValue(emptyBinValue);
                gb.setAveragePickupProcentThisMonth(avgPercent);
                gb.setLastPickupDate(lastpickup);
                returnList.add(gb);

            }while(cursor.moveToNext());

        }else{

        }

        cursor.close();
        db.close();
        return returnList;
    }
}

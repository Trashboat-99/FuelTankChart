package com.example.stephanie.fueltankchart;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Stephanie on 7/2/17.
 * This class is for creating the table with the fuel chart values
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "FuelTankChart.db";
    private static String TABLE = "tankDiameter";

    public static String COL_ID = "ID";
    public static String COL_VALUE = "";
    private static String DB_PATH = "";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    //constructor
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        if(android.os.Build.VERSION.SDK_INT >= 17){
            //DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;

    }
    /** this method creates an empty database locally and rewrites it with your own database**/
    public void createDatabase() throws IOException{
        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing if the db already exists
        }
        else{
            //by calling this method an empty db will be created into the default system path
            //of your application. This will allow the local database to be overwritten with this database
            this.getWritableDatabase();
            try{
                //Copy the database from assets
                copyDataBase();
            } catch (IOException e){
                throw new Error("Error copying database");
            }
        }
    }
    //a method to check that the database exists here: /data/data/your package/databases/Db Name
    private boolean checkDataBase(){
        File dbFile = new File(DB_PATH + DB_NAME);

        return dbFile.exists();
    }
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
        //open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        //path to the just created empty db
        String outFileName = DB_PATH+ DB_NAME;

        //open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] myBuffer = new byte[1024];
        int length;
        while((length = myInput.read(myBuffer))>0){
            myOutput.write(myBuffer, 0, length);
        }

        //close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException{
        //open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        return myDataBase !=null;
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //search method
    public DataModel findValue(String tankDiameter, String tankCapacity, int inchesOfFuel) {
        if(tankDiameter.equals("96''")){
            TABLE = "FUEL_CHART_96";
            //need try/catch for error handling to check if table exists
        }
        else if(tankDiameter.equals("120''")){
            TABLE = "FUEL_CHART_120";
            //need try/catch for error handling to check if table exists
        }
        COL_VALUE = "COL_"+tankCapacity; //set this column to the value of the tank capacity
        String VALUE = Integer.toString(inchesOfFuel);
        //select the row from the table where the id column is equal to the id value = inchesOfFuel
        String query = "SELECT "+COL_VALUE+" FROM " + TABLE + " WHERE " + COL_ID + " = "+VALUE;

        Cursor cursor = myDataBase.rawQuery(query, null); //the cursor contains the value of the row
        DataModel result = new DataModel();
        if(cursor != null){
            cursor.moveToNext();
            result.setValue(cursor.getString(0));
        }
        else{
            result.setValue("No Results Found");
        }

        return result;
    }

}

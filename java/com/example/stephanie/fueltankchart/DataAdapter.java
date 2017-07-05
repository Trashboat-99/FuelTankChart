package com.example.stephanie.fueltankchart;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Stephanie on 7/4/17.
 */

public class DataAdapter {
    private final Context myContext;
    private SQLiteDatabase myDB;
    private DatabaseHelper myDBHelper;

    //constructor
    public DataAdapter(Context context){
        this.myContext = context;
        myDBHelper = new DatabaseHelper(myContext);
    }
    //create the database
    public DataAdapter createDatabase() throws SQLException{
        try
        {
            myDBHelper.createDatabase();
        }
        catch (IOException mIOException)
        {

            throw new Error("Unable To Create Database");
        }
        return this;
    }
    //open the database
    public DataAdapter open() throws SQLException {
        try {
            myDBHelper.openDataBase();
            //myDBHelper.close();
            myDB = myDBHelper.getReadableDatabase();
        } catch (SQLException mySQLException) {
            throw mySQLException;
        }
        return this;
    }
    public void close()
    {
        myDBHelper.close();
    }
    //search method
    public String findValue(String tankDiameter, String tankCapacity, int inchesOfFuel) {
        String result;
        try {
            myDBHelper.findValue(tankDiameter, tankCapacity, inchesOfFuel);
        }
        catch(SQLException ex){
            throw new Error("Error: SQL Exception");
        }
        result = myDBHelper.findValue(tankDiameter, tankCapacity, inchesOfFuel).getValue();
        return result;
    }

}

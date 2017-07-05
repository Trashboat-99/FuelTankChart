package com.example.stephanie.fueltankchart;

import android.provider.ContactsContract;

/**
 * Created by Stephanie on 7/4/17.
 */

public class DataModel {
    //String tankDiameter, tankCapacity, inchesOfFuel;
    private String value;
    //empty constructor
    public DataModel(){

    }
    public DataModel(String value){
        this.value = value;
    }
    /**
    //constructor
    public DataModel(String tankDiameter, String tankCapacity, String inchesOfFuel){
    this.tankDiameter = tankDiameter;
    this.tankCapacity = tankCapacity;
    this.inchesOfFuel = inchesOfFuel;
    }
    public String getTankDiameter(){
        return this.tankDiameter;
    }
    public void setTankDiameter(String tankDiameter){
        this.tankDiameter = tankDiameter;
    }
    public void setTankCapacity(String tankCapacity){
        this.tankCapacity = tankCapacity;
    }
    public String getTankCapacity(){
        return  this.tankCapacity;
    }
    public void setInchesOfFuel(String inchesOfFuel){
        this.inchesOfFuel = inchesOfFuel;
    }
    public String getInchesOfFuel(){
        return this.inchesOfFuel;
    } **/
    public void setValue(String value)
    {
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}

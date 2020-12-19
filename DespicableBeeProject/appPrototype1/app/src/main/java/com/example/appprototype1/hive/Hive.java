package com.example.appprototype1.hive;

public class Hive {

    String hiveNumber;
    String apiaryName;
    String size;
    String type;
    String strength;
    String numberOfBoxes;
    Boolean hasQueen;
    String markedColor;
    String additionalNotes;

    public Hive(){

    }

    public Hive(String apiaryName, String hiveNumber, String size, String type){
        this.hiveNumber = hiveNumber;
        this.apiaryName = apiaryName;
        this.size = size;
        this.type = type;
    }

    public String getHiveNumber() {return hiveNumber;}

    public String getApiaryName() {return apiaryName;}
    public String getSize(){ return size;}

    public String getType(){ return type;}


}

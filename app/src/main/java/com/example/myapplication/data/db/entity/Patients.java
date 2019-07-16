package com.example.myapplication.data.db.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.myapplication.data.db.converter.DateConverter;

import java.util.Date;



@Entity(tableName = "patients")
@TypeConverters(DateConverter.class)
public class Patients {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String address;
    public Date birthday;
    public String phone;
    public String email;
    public String height;
    public String weight;
    public String bloodGroup;



    @Ignore
    public Patients() {
        this.name = "";
        this.address = "";
        this.birthday = null;
        this.phone = "";
        this.email = "";
        this.height="";
        this.weight="";
        this.bloodGroup="";
    }



    public Patients(long id, String name, String address, Date birthday, String phone, String email, String height, String weight, String bloodGroup) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.bloodGroup = bloodGroup;
    }
}

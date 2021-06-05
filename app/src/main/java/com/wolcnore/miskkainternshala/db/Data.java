package com.wolcnore.miskkainternshala.db;

import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.wolcnore.miskkainternshala.Pojo;

import java.util.List;

@Entity
public class Data {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @TypeConverters(DataConverter.class) // add here
    @ColumnInfo(name = "list")
    public List<Pojo> list;

    public Data(List<Pojo> list) {
        this.list = list;
    }
}

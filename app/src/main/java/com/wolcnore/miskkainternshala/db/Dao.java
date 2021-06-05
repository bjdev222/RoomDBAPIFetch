package com.wolcnore.miskkainternshala.db;

import androidx.room.Insert;
import androidx.room.Query;

import com.wolcnore.miskkainternshala.Pojo;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Query("SELECT * FROM data")
    Data getData();




    @Query("DELETE FROM data")
    int deleteData();



    @Insert
    void insertAll(Data... users);
}

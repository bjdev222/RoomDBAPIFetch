package com.wolcnore.miskkainternshala.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Data.class},version = 1)

public abstract class AppDatabase extends RoomDatabase {

    public abstract Dao userDao();
    private static AppDatabase INSTANCE;
    public static AppDatabase getDBInstance(Context context) {
        if (INSTANCE == null) {

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DB NAME")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}

package com.example.olgam.bucketlist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = BucketListItem.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BucketListItemDao bucketListItemDao();

    private final static  String NAME_DATABASE = "bucketlistitem_db";

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {

        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context, AppDatabase.class, NAME_DATABASE).build();
        }
        return sInstance;
    }
}

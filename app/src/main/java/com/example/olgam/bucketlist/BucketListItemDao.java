package com.example.olgam.bucketlist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BucketListItemDao {
    @Query("SELECT * FROM bucketlistitem")
    public LiveData<List<BucketListItem>> getAllItems();

    @Insert
    public void insertItems(BucketListItem items);

    @Delete
    public void deleteItems(BucketListItem items);

    @Update
    void updateItems(BucketListItem items);

}

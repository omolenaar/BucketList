package com.example.olgam.bucketlist;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BucketListRepository {
    private AppDatabase mAppDatabase;
    private BucketListItemDao mBucketListItemDao;
    private LiveData<List<BucketListItem>> mItems;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public BucketListRepository (Context context){
        mAppDatabase = AppDatabase.getInstance(context);
        mBucketListItemDao = mAppDatabase.bucketListItemDao();
        mItems = mBucketListItemDao.getAllItems();
    }

    public LiveData<List<BucketListItem>> getAllItems() {
        return mItems;
    }

    public void insert(final BucketListItem item) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mBucketListItemDao.insertItems(item);
            }
        });
    }

    public void update(final BucketListItem item) {
        mExecutor.execute((new Runnable() {
            @Override
            public void run() {
                mBucketListItemDao.updateItems(item);
            }
        }));
    }

    public void delete(final BucketListItem item) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mBucketListItemDao.deleteItems(item);
            }
        });
    }
}

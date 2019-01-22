package com.example.olgam.bucketlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.util.List;

public class MainViewModel extends ViewModel {
    private BucketListRepository mRepository;
    private LiveData<List<BucketListItem>> mItems;

    public MainViewModel (Context context) {
        mRepository = new BucketListRepository(context);
        mItems = mRepository.getAllItems();
    }

    public LiveData<List<BucketListItem>> getmItems () {
        return mItems;
    }

    public void insert (BucketListItem item) {
        mRepository.insert(item);
    }

    public void update (BucketListItem item) {mRepository.update(item); }

    public void delete (BucketListItem item) {mRepository.delete(item);}
}

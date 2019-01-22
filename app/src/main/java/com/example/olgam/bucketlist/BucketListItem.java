package com.example.olgam.bucketlist;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "bucketlistitem")
public class BucketListItem {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String itemTitle;
    private String itemText;
    private boolean crossedOff;

    public BucketListItem (String itemTitle, String itemText, boolean crossedOff) {
        this.itemTitle = itemTitle;
        this.itemText = itemText;
        this.crossedOff = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public boolean isCrossedOff() {
        return crossedOff;
    }

    public void setCrossedOff(boolean crossedOff) {
        this.crossedOff = crossedOff;
    }


}

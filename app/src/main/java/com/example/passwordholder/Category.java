package com.example.passwordholder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey//(autoGenerate = true)
    public int uid;


    @ColumnInfo(name = "item_category")
    public String itemCategory;

    @ColumnInfo(name = "item_detail")
    public String itemDetail;

    public Category(int uid,String itemCategory, String itemDetail)
    {
        this.itemCategory = itemCategory;
        this.itemDetail = itemDetail;
        this.uid = uid;
    }

    public Category()
    {

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(String itemDetail) {
        this.itemDetail = itemDetail;
    }

    @Override
    public String toString() {
        return "UID: "+ uid+"\n item category: "+itemCategory+"\n item detail: "+itemDetail;
    }
}
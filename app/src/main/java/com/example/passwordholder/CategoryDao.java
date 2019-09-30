package com.example.passwordholder;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void addCategory(Category category);

    //test the speed of the queries, so that it creates less double arraylist
    //creates an arraylist at the amount of categories, and the alphabetize them, them using
    //the location of said category, it can add all of them linkedsets
    //since we can already have it ordered alphabetically we would be fine
    //linkedhashmap

    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Query("SELECT * FROM category ORDER BY item_category ASC")
    List<Category> getAllSorted();

    @Query("SELECT * FROM category ORDER BY item_category ASC, item_detail ASC")
    List<Category> getFullSorted();
    //did not have full sorted

    @Query("SELECT * FROM category WHERE uid IN (:userIds)")
    List<Category> loadAllByIds(int[] userIds);


    @Query("SELECT * FROM category WHERE item_category LIKE :first AND " +
            "item_detail LIKE :last LIMIT 1")
    Category findByName(String first, String last);

    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);



}
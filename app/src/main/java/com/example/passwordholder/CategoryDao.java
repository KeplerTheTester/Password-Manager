package com.example.passwordholder;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Query("SELECT * FROM category WHERE uid IN (:userIds)")
    List<Category> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM category WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    Category findByName(String first, String last);

    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);
}
package com.example.runningapp.db.entity;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


//kullanıcı bilgileriyle alakalı interface data access overlay veriye erişim katmanı MVVM
@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM user_table LIMIT 1")
    User getUser();

    @Query("DELETE FROM user_table")
    void deleteAll(); // Kullanıcı verilerini silmek için metot
}
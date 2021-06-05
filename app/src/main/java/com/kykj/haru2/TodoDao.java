package com.kykj.haru2;


import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM Todo ORDER BY Year DESC")
    List<Todo> getAll();


    @Query("SELECT * FROM Todo WHERE id = :id")
    List<Todo> Selectid(int id);

    @Query("SELECT * FROM Todo")
    Cursor getSelectAll();

    @Query("DElETE FROM Todo WHERE id = :id")
    void Selectdelete(int id);

    @Query("UPDATE Todo SET weather = :weather, star_one = :star_one, star_two = :star_two, star_three = :star_three ,content = :content WHERE id = :id ")
    void SelectUpdate(int id, String weather, Double star_one, Double star_two, Double star_three ,String content);

    @Query("UPDATE Todo SET imgname1 = :Imgname1, Imgname2 = :Imgname2, Imgname3 =:Imgname3 where id = :id ")
    void UpdateImg(int id, String Imgname1, String Imgname2, String Imgname3);


    @Query("SELECT count(id) FROM Todo")
    Integer Countselect();


    @Query("DELETE FROM Todo")
    void all_delete();

    @Insert
    void insert(Todo todo);
}

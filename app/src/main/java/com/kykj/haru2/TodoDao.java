package com.kykj.haru2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

@Dao
public interface TodoDao {
    //메인에서 보여줄 쿼리
    @Query("SELECT * FROM Todo ORDER BY Year DESC")
    List<Todo> getAll();

    //수정하기을 하기위한 셀렉트
    @Query("SELECT * FROM Todo WHERE id = :id")
    List<Todo> Selectid(int id);
    //메모삭제 쿼리
    @Query("DElETE FROM Todo WHERE id = :id")
    void Selectdelete(int id);
    //업데이트 사진은 제외
    @Query("UPDATE Todo SET year = :year, weather = :weather, star_one = :star_one, star_two = :star_two, star_three = :star_three ,content = :content WHERE id = :id ")
    void SelectUpdate(int id, String year ,String weather, Double star_one, Double star_two, Double star_three ,String content);
    //사진 업데이트
    @Query("UPDATE Todo SET imgname1 = :Imgname1, Imgname2 = :Imgname2, Imgname3 =:Imgname3 where id = :id ")
    void UpdateImg(int id, String Imgname1, String Imgname2, String Imgname3);

    //설정 부분의 ㅇㅇ일 표시
    @Query("SELECT count(id) FROM Todo")
    Integer Countselect();

    //설정 부분의 전체 초기화
    @Query("DELETE FROM Todo")
    void all_delete();
    //ForFragment의 모든 정보 인설트
    @Insert
    void insert(Todo todo);
}

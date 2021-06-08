package com.kykj.haru2;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Todo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private float star_one;
    private float star_two;
    private float star_three;
    private String year;
    private String weather;
    private String content;
    @ColumnInfo(name = "imgname1")
    @Nullable
    private String imgname1;
    @ColumnInfo(name = "imgname2")
    @Nullable
    private String imgname2;
    @ColumnInfo(name = "imgname3")
    @Nullable
    private String imgname3;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getContent() {
        return content;
    }

    public float getStar_one() {
        return star_one;
    }

    public void setStar_one(float star_one) {
        this.star_one = star_one;
    }

    public float getStar_two() {
        return star_two;
    }

    public void setStar_two(float star_two) {
        this.star_two = star_two;
    }

    public float getStar_three() {
        return star_three;
    }

    public void setStar_three(float star_three) {
        this.star_three = star_three;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getImgname1() {
        return imgname1;
    }

    public void setImgname1(@Nullable String imgname1) {
        this.imgname1 = imgname1;
    }

    @Nullable
    public String getImgname2() {
        return imgname2;
    }

    public void setImgname2(@Nullable String imgname2) {
        this.imgname2 = imgname2;
    }

    @Nullable
    public String getImgname3() {
        return imgname3;
    }

    public void setImgname3(@Nullable String imgname3) {
        this.imgname3 = imgname3;
    }



    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", star_one=" + star_one +
                ", star_two=" + star_two +
                ", star_three=" + star_three +
                ", year='" + year + '\'' +
                ", weather='" + weather + '\'' +
                ", content='" + content + '\'' +
                ", imgname1='" + imgname1 + '\'' +
                ", imgname2='" + imgname2 + '\'' +
                ", imgname3='" + imgname3 + '\'' +
                '}';
    }


}

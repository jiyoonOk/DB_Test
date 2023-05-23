package android.anonymous.db_test.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    public String user_name;

    //TODO : 지환이한테 물어보기

    public float user_kcal;
    public float user_height;
    public float user_weight;
    public int user_purpose;
    public int user_pwd;
    public int user_work;

    //TODO : 생성자 만들기.

    // TODO : getter and setter methods...
}
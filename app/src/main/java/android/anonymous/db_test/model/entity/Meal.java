package android.anonymous.db_test.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// 테이블 이름 : meal, 외래키 : foodIndex, 인덱스 : foodIndex
@Entity(tableName = "meal", indices = {@Index("mealDate")})
public class Meal {
    @PrimaryKey(autoGenerate = true)
    private int mealIndex;
    private String mealDate;
    private int mealCnt;

    public Meal(String mealDate, int mealCnt) {
        this.mealDate = mealDate;
        this.mealCnt = mealCnt;
    }

    // getter and setter methods...
    public int getMealIndex() {return mealIndex;}
    public void setMealIndex(int mealIndex) {this.mealIndex = mealIndex;}
    public String getMealDate() {return mealDate;}
    public int getMealCnt() {return mealCnt;}
}

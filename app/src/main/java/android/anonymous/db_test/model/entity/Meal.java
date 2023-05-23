package android.anonymous.db_test.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal", foreignKeys = @ForeignKey(entity = Food.class, parentColumns = "foodIndex", childColumns = "foodIndex"))
public class Meal {
    @PrimaryKey(autoGenerate = true)
    private int mealIndex;

    private String mealDate;
    private int mealCnt;
    private int mealFoodAmount;

    @ColumnInfo(name = "foodIndex")
    private int foodIndex;

    private int checked;

    public Meal(String mealDate, int mealCnt, int mealFoodAmount, int foodIndex, int checked) {
        this.mealDate = mealDate;
        this.mealCnt = mealCnt;
        this.mealFoodAmount = mealFoodAmount;
        this.foodIndex = foodIndex;
        this.checked = checked;
    }

    // getter and setter methods...
    public int getMealIndex() {return mealIndex;}
    public String getMealDate() {return mealDate;}
    public int getMealCnt() {return mealCnt;}
    public int getMealFoodAmount() {return mealFoodAmount;}
    public void setMealFoodAmount(int mealFoodAmount) {this.mealFoodAmount = mealFoodAmount;}
    public int getFoodIndex() {return foodIndex;}
    public int getChecked() {return checked;}
    public void setChecked(int checked) {this.checked = checked;}

    //test
    public void setMealIndex(int mealIndex) {this.mealIndex = mealIndex;}
}

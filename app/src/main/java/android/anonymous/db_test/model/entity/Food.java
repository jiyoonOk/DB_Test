package android.anonymous.db_test.model.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "food")
public class Food {
    @PrimaryKey(autoGenerate = true)
    private int foodIndex;

    private String foodName;
    private float foodKcal;
    private float foodCarbohydrates;
    private float foodProtein;
    private float foodFat;
    private int foodLike = 0;   // default value: 0

    public Food(String foodName, float foodKcal, float foodCarbohydrates, float foodProtein, float foodFat, int foodLike) {
        this.foodName = foodName;
        this.foodKcal = foodKcal;
        this.foodCarbohydrates = foodCarbohydrates;
        this.foodProtein = foodProtein;
        this.foodFat = foodFat;
        this.foodLike = foodLike;
    }
    //생성자 오버로딩
    @Ignore
    public Food(String foodName, float foodKcal, float foodCarbohydrates, float foodProtein, float foodFat) {
        this.foodName = foodName;
        this.foodKcal = foodKcal;
        this.foodCarbohydrates = foodCarbohydrates;
        this.foodProtein = foodProtein;
        this.foodFat = foodFat;
    }

    // getter methods...
    public int getFoodIndex() { return foodIndex; }
    public String getFoodName() { return foodName; }
    public float getFoodKcal() { return foodKcal; }
    public float getFoodCarbohydrates() { return foodCarbohydrates; }
    public float getFoodProtein() { return foodProtein; }
    public float getFoodFat() { return foodFat; }
    public int getFoodLike() { return foodLike; }

    //test
    public void setFoodIndex(int foodIndex) {this.foodIndex = foodIndex;}
}

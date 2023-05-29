package android.anonymous.db_test.model.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "food")
public class Food {
    @PrimaryKey(autoGenerate = true)
    private int foodIndex;

    private String foodName;
    private float food1serving;
    private float foodKcal;
    private float foodCarbohydrates;
    private float foodProtein;
    private float foodFat;
    private String foodCompany;
    private int foodLike = 0;   // default value: 0

    public Food(String foodName, float food1serving, float foodKcal, float foodCarbohydrates, float foodProtein, float foodFat, String foodCompany, int foodLike) {
        this.foodName = foodName;
        this.food1serving = food1serving;
        this.foodKcal = foodKcal;
        this.foodCarbohydrates = foodCarbohydrates;
        this.foodProtein = foodProtein;
        this.foodFat = foodFat;
        this.foodCompany = foodCompany;
        this.foodLike = foodLike;
    }
    //생성자 오버로딩
    @Ignore
    public Food(String foodName, float food1serving, float foodKcal, float foodCarbohydrates, float foodProtein, float foodFat, String foodCompany) {
        this.foodName = foodName;
        this.food1serving = food1serving;
        this.foodKcal = foodKcal;
        this.foodCarbohydrates = foodCarbohydrates;
        this.foodProtein = foodProtein;
        this.foodFat = foodFat;
        this.foodCompany = foodCompany;
    }

    // getter methods...
    public int getFoodIndex() { return foodIndex; }
    public String getFoodName() { return foodName; }
    public float getFood1serving() { return food1serving; }
    public float getFoodKcal() { return foodKcal; }
    public float getFoodCarbohydrates() { return foodCarbohydrates; }
    public float getFoodProtein() { return foodProtein; }
    public float getFoodFat() { return foodFat; }
    public String getFoodCompany() { return foodCompany; }
    public int getFoodLike() { return foodLike; }

    // 인덱스를 수정하는 setter method
    public void setFoodIndex(int foodIndex) {this.foodIndex = foodIndex;}

    // 모든 필드를 출력하는 toString() 메소드
    @Override
    public String toString() {
        return "FoodIndex: " + foodIndex + "\n" + "FoodName: " + foodName + "\n" + "Food1serving: " + food1serving + "\n" +
                "FoodKcal: " + foodKcal + "\n" + "FoodCarbohydrates: " + foodCarbohydrates + "\n" + "FoodProtein: " + foodProtein + "\n" +
                "FoodFat: " + foodFat + "\n" + "FoodCompany: " + foodCompany + "\n" + "FoodLike: " + foodLike;
    }
}

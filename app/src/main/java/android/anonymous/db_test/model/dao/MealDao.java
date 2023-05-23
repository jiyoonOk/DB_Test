package android.anonymous.db_test.model.dao;

import android.anonymous.db_test.model.entity.Meal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MealDao {
    @Insert
    void insertMeal(Meal meal);

    @Update
    void updateMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);

    // 모든 Meal을 가져옵니다.
    @Query("SELECT * FROM meal")
    LiveData<List<Meal>> getAllMeals();

    // 특정 날짜의 Meal을 가져옵니다.
    @Query("SELECT * FROM meal WHERE mealDate = :mealDate")
    LiveData<List<Meal>> getMealsByDate(String mealDate);

    // 특정 음식의 Meal을 가져옵니다.
    @Query("SELECT * FROM meal WHERE foodIndex = :foodIndex")
    LiveData<List<Meal>> getMealsByFoodIndex(int foodIndex);

    // 체크가 된 Meal을 가져옵니다.
    @Query("SELECT * FROM meal WHERE checked = 1")
    LiveData<List<Meal>> getCheckedMeals();
}

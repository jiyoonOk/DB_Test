package android.anonymous.db_test.model.repository;

import android.anonymous.db_test.model.dao.FoodDao;
import android.anonymous.db_test.model.database.AppDatabase;
import android.anonymous.db_test.model.entity.Food;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.Executors;

public class FoodRepository {
private static FoodRepository instance = null;
    private final FoodDao foodDao;
    private final AppDatabase db;

    public static FoodRepository getInstance(Context context) {
        if (instance == null) {
            synchronized(FoodRepository.class) {
                if(instance == null) {
                    instance = new FoodRepository(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public FoodRepository(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, "app-database").build();
        foodDao = db.foodDao();
    }

    // 이 클래스는 새로운 스레드에서 데이터베이스 작업을 수행.
    // 이는 Room 라이브러리의 중요한 특징. 메인 스레드에서 데이터베이스 작업을 하면 UI가 멈추는 현상이 발생할 수 있기 때문.
    // 이로 인해 사용자 경험이 저하 가능성. 따라서 Room 라이브러리는 메인 스레드에서 데이터베이스 작업을 하지 못하도록 막음.
    public void insertFood(Food food) {
        Executors.newSingleThreadExecutor().execute(() -> {
            foodDao.insertFood(food);
        });
    }

    public void updateFood(Food food) {
        Executors.newSingleThreadExecutor().execute(() -> {
            foodDao.updateFood(food);
        });
    }

    public void deleteFood(Food food) {
        Executors.newSingleThreadExecutor().execute(() -> {
            foodDao.deleteFood(food);
        });
    }

    // LiveData를 이용하여 데이터를 가져옵니다.
    public LiveData<List<Food>> getAllFoods() {
        return foodDao.getAllFoods();
    }

    // 특정 음식의 좋아요 여부를 가져옵니다.
    public LiveData<List<Food>> getLikedFoods() {
        return foodDao.getLikedFoods();
    }

    public LiveData<List<Food>> getFoodByName(String foodName) {
        return foodDao.getFoodByName(foodName);
    }
}
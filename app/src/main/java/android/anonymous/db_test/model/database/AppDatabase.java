package android.anonymous.db_test.model.database;

import static android.anonymous.db_test.model.constants.Constants.DATABASE_NAME;

import android.anonymous.db_test.model.dao.FoodDao;
import android.anonymous.db_test.model.dao.MealDao;
import android.anonymous.db_test.model.dao.MealFoodDao;
import android.anonymous.db_test.model.dao.UserDao;
import android.anonymous.db_test.model.entity.Food;
import android.anonymous.db_test.model.entity.Meal;
import android.anonymous.db_test.model.entity.MealFood;
import android.anonymous.db_test.model.entity.User;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

// version = 1 : MealFood 테이블 추가 및 Meal 테이블 수정에 따른 버전 업
@Database(entities = {Meal.class, Food.class, User.class, MealFood.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract MealDao mealDao();
    public abstract FoodDao foodDao();
    public abstract UserDao userDao();
    public abstract MealFoodDao mealFoodDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    /*private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // MealFood 테이블을 추가합니다.
            database.execSQL("CREATE TABLE mealFood (mealIndex INTEGER, foodIndex INTEGER, mealFoodAmount INTEGER, checked INTEGER, " +
                    "PRIMARY KEY(mealIndex, foodIndex)," +
                    "FOREIGN KEY(mealIndex) REFERENCES meal(mealIndex)," +
                    "FOREIGN KEY(foodIndex) REFERENCES food(foodIndex))");

            // Meal 테이블에서 더 이상 사용하지 않는 칼럼을 제거합니다.
            database.execSQL("CREATE TABLE new_meal (mealIndex INTEGER PRIMARY KEY NOT NULL, mealDate TEXT, mealCnt INTEGER)");
            database.execSQL("INSERT INTO new_meal (mealIndex, mealDate, mealCnt) SELECT mealIndex, mealDate, mealCnt FROM meal");
            database.execSQL("DROP TABLE meal");
            database.execSQL("ALTER TABLE new_meal RENAME TO meal");
        }
    };*/
}

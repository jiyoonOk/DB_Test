package android.anonymous.db_test.model.database;

import android.anonymous.db_test.model.dao.FoodDao;
import android.anonymous.db_test.model.dao.MealDao;
import android.anonymous.db_test.model.dao.UserDao;
import android.anonymous.db_test.model.entity.Food;
import android.anonymous.db_test.model.entity.Meal;
import android.anonymous.db_test.model.entity.User;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

// version = 1 : 처음 생성할 때 버전 1로 생성. 이후에 업데이트할 때마다 버전을 올려줘야 함.
@Database(entities = {Meal.class, Food.class, User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "app_database";
    private static AppDatabase instance;

    public abstract MealDao mealDao();      // MealDao를 사용하기 위한 메소드.
    public abstract FoodDao foodDao();
    public abstract UserDao userDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // food 테이블에 foodLike 컬럼을 추가.
            database.execSQL("ALTER TABLE food ADD COLUMN foodLike INTEGER DEFAULT 0");
        }
    };
}

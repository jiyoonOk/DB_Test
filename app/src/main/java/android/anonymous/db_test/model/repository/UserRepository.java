package android.anonymous.db_test.model.repository;

import static android.anonymous.db_test.model.constants.Constants.DATABASE_NAME;

import android.anonymous.db_test.model.dao.UserDao;
import android.anonymous.db_test.model.database.AppDatabase;
import android.anonymous.db_test.model.entity.User;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private static UserRepository instance = null;
    private final UserDao userDao;
    private final AppDatabase db;
    private final ExecutorService executorService;  // DB 작업을 위한 스레드 풀.

    public static UserRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public UserRepository(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
        userDao = db.userDao();
        executorService = Executors.newFixedThreadPool(4);
    }

    public void insertUser(User user) {
        executorService.execute(() -> { userDao.insertUser(user); });
    }

    public void updateUser(User user) {
        executorService.execute(() -> { userDao.updateUser(user); });
    }

    public void deleteUser(User user) {
        executorService.execute(() -> { userDao.deleteUser(user); });
    }

    // userName이랑 일치하는 유저를 가져옴.
    public LiveData<User> getUserByName(String userName) {
        return userDao.getUserByName(userName);
    }
}
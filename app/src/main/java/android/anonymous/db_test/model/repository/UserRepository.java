package android.anonymous.db_test.model.repository;

import android.anonymous.db_test.model.dao.UserDao;
import android.anonymous.db_test.model.database.AppDatabase;
import android.anonymous.db_test.model.entity.User;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.concurrent.Executors;

public class UserRepository {
    private final UserDao userDao;
    private final AppDatabase db;

    public UserRepository(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, "app-database").build();
        userDao = db.userDao();
    }

    public void insertUser(User user) {
        Executors.newSingleThreadExecutor().execute(() -> {
            userDao.insertUser(user);
        });
    }

    public void updateUser(User user) {
        Executors.newSingleThreadExecutor().execute(() -> {
            userDao.updateUser(user);
        });
    }

    public void deleteUser(User user) {
        Executors.newSingleThreadExecutor().execute(() -> {
            userDao.deleteUser(user);
        });
    }

    public LiveData<User> getUserByName(String userName) {
        return userDao.getUserByName(userName);
    }
}
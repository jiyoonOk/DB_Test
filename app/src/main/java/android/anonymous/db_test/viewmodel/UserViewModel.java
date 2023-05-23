package android.anonymous.db_test.viewmodel;

import android.app.Application;
import android.anonymous.db_test.model.entity.User;
import android.anonymous.db_test.model.repository.UserRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository repository;

    public UserViewModel (Application application) {
        super(application);
        repository = new UserRepository(application);

    }

    public void insert(User user) { repository.insertUser(user); }

    public void update(User user) { repository.updateUser(user); }

    public void delete(User user) { repository.deleteUser(user); }
}
package android.anonymous.db_test.viewmodel;

import android.app.Application;
import android.anonymous.db_test.model.entity.Meal;
import android.anonymous.db_test.model.repository.MealRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MealViewModel extends AndroidViewModel {
    private final MealRepository repository;
    private final LiveData<List<Meal>> allMeals;

    public MealViewModel (Application application) {
        super(application);
        repository = MealRepository.getInstance(application);

        /*repository = new MealRepository(application);*/
        allMeals = repository.getAllMeals();
    }

    public LiveData<List<Meal>> getAllMeals() { return allMeals; }

    public void insert(Meal meal) { repository.insertMeal(meal); }

    public void update(Meal meal) { repository.updateMeal(meal); }

    public void delete(Meal meal) { repository.deleteMeal(meal); }
}

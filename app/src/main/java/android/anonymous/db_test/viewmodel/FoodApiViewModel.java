package android.anonymous.db_test.viewmodel;

import android.anonymous.db_test.model.api.FoodApiHelper;
import android.anonymous.db_test.model.entity.Food;
import android.anonymous.db_test.model.repository.FoodApiRepository;
import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.io.Closeable;
import java.util.List;

import retrofit2.Call;

public class FoodApiViewModel extends ViewModel {
    private FoodApiRepository foodApiRepository;

    public FoodApiViewModel(Application application) {
        super();
        foodApiRepository = FoodApiRepository.getInstance(application);
    }

    public Call<FoodApiHelper.ResponseClass> getFoodNtrItdntList(String foodName) {
        return foodApiRepository.getFoodNtrItdntList(foodName);
    }

    public LiveData<List<Food>> searchFood(String foodName) {
        return foodApiRepository.searchFood(foodName);
    }
}

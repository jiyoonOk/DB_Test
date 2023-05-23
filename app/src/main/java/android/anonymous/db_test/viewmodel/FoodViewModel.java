package android.anonymous.db_test.viewmodel;

import android.anonymous.db_test.model.entity.Food;
import android.anonymous.db_test.model.repository.FoodRepository;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {
    private final FoodRepository repository;
    private final LiveData<List<Food>> allFoods;
    private final MutableLiveData<Food> clickedFood = new MutableLiveData<>();

    public FoodViewModel (Application application) {
        super(application);
        repository = FoodRepository.getInstance(application);
        //repository = new FoodRepository(application);
        allFoods = repository.getAllFoods();
    }

    public LiveData<List<Food>> getAllFoods() { return allFoods; }

    public void insert(Food food) { repository.insertFood(food); }

    public void update(Food food) { repository.updateFood(food); }

    public void delete(Food food) { repository.deleteFood(food); }

    // 특정 음식의 좋아요 여부를 가져옵니다.
    public LiveData<List<Food>> getLikedFoods() { return repository.getLikedFoods(); }


    public void setClickedFood(Food food) {clickedFood.setValue(food);}
    public LiveData<Food> getClickedFood() {return clickedFood;}
}
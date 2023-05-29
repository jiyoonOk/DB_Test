package android.anonymous.db_test.viewmodel;

import android.anonymous.db_test.model.entity.Food;
import android.anonymous.db_test.model.repository.FoodApiRepository;
import android.anonymous.db_test.model.repository.FoodRepository;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {
    // private final로 선언을 왜 해? -> 생성자에서 초기화를 해줘야 하기 때문에
    private final FoodRepository repository;
    private final LiveData<List<Food>> allFoods;
    private final MutableLiveData<Food> selectedFood = new MutableLiveData<>(); // 선택된 음식을 나타내는 LiveData 추가
    private final MutableLiveData<Food> foodLiveData = new MutableLiveData<>(); // 특정 음식의 좋아요 여부를 나타내는 LiveData 추가

    public FoodViewModel (Application application) {
        super(application);
        repository = FoodRepository.getInstance(application);
        allFoods = repository.getAllFoods();
    }

    // DB에서 모든 음식 정보를 가져옴.
    public LiveData<List<Food>> getAllFoods() { return allFoods; }

    public void insert(Food food) {
        new Thread(() -> {
            try {
                Food existingFood = repository.getFoodByNameSync(food.getFoodName());
                if (existingFood == null) {
                    repository.insertFood(food);
                }
            } catch (Exception e) {
                // 예외를 로그에 기록하거나 사용자에게 오류 메시지를 표시합니다.
            }
        }).start();
    }

    public void update(Food food) { repository.updateFood(food); }

    public void delete(Food food) { repository.deleteFood(food); }

    // 특정 음식의 좋아요 여부를 가져옵니다.
    public LiveData<List<Food>> getLikedFoods() { return repository.getUserLikedFoods(); }
    // 특정 음식의 좋아요 여부를 업데이트합니다.
    public void setClickedFood(Food food) {foodLiveData.setValue(food);}


    public void selectFood(Food food) { selectedFood.setValue(food); } // 선택된 음식을 설정하는 메소드 추가
    public LiveData<Food> getSelectedFood() { return selectedFood; } // 선택된 음식을 가져오는 메소드 추가
}


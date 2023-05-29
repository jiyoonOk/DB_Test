package android.anonymous.db_test.viewmodel;

import android.anonymous.db_test.model.api.FoodApiHelper;
import android.anonymous.db_test.model.entity.Food;
import android.anonymous.db_test.model.repository.FoodApiRepository;
import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Closeable;
import java.util.List;

import retrofit2.Call;

public class FoodApiViewModel extends ViewModel {
    private FoodApiRepository foodApiRepository;
    private final LiveData<List<Food>> searchResults;
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    // 생성자
    public FoodApiViewModel(Application application) {
        super();
        foodApiRepository = FoodApiRepository.getInstance(application);
        searchResults = foodApiRepository.getSearchResults();
    }

    // 사용자가 특정 음식 이름을 입력하면 그 음식의 영양 정보를 반환함.
    public Call<FoodApiHelper.ResponseClass> getFoodNtrItdntList(String foodName) {
        return foodApiRepository.getFoodNtrItdntList(foodName);
    }

    // 사용자가 음식 이름을 입력하면 그와 관련된 모든 음식을 검색 결과로 반환함.
    public LiveData<List<Food>> searchFood(String foodName) {
        return foodApiRepository.searchFood(foodName);
    }

    // 검색결과와 에러메시지를 가져옴.
    public LiveData<List<Food>> getSearchResults() {
        return searchResults;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    
}

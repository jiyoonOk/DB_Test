package android.anonymous.db_test.model.repository;

import android.anonymous.db_test.model.api.FoodApiHelper;
import android.anonymous.db_test.model.entity.Food;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodApiRepository {
    private FoodApiHelper foodApiHelper;
    private static FoodApiRepository instance = null;
    private FoodApiHelper.ApiService apiService;
    private MutableLiveData<List<Food>> searchResults;

    private static final String SERVICE_KEY = "GcathdXBPe7Iq8hBQV+9AIQvT3ZolP7IReNXxzkVfHgHqsnf29JywOSU01mENSUdeeaKp6igmU7EU1Spj/cIuw==";



    public FoodApiRepository(Context context) {
        foodApiHelper = FoodApiHelper.getInstance(context);

        apiService = foodApiHelper.getApiService();
        searchResults = new MutableLiveData<>();
    }

    public static FoodApiRepository getInstance(Context context) {
        if (instance == null) {
            synchronized(FoodApiRepository.class) {
                if(instance == null) {
                    instance = new FoodApiRepository(context.getApplicationContext());
                }
            }
        }
        return instance;
    }


    public LiveData<List<Food>> searchFood(String foodName) {
        MutableLiveData<List<Food>> searchResults = new MutableLiveData<>();

        Call<FoodApiHelper.ResponseClass> call = apiService.getFoodNtrItdntList( SERVICE_KEY, foodName, null, null, null, null, "json" );
        call.enqueue(new Callback<FoodApiHelper.ResponseClass>() {    // Callback 인터페이스를 구현한 익명 클래스 정의. 응답을 받았을 때 호출되는 메소드를 정의.
            @Override
            // onResponse 메소드: 응답을 성공적으로 받았을 때 호출되는 메소드
            public void onResponse(Call<FoodApiHelper.ResponseClass> call, Response<FoodApiHelper.ResponseClass> response) {
                if (response.isSuccessful()) {
                    List<FoodApiHelper.ResponseClass.Body.Item> items = response.body().getBody().getItems();
                    List<Food> foods = new ArrayList<>();
                    for (FoodApiHelper.ResponseClass.Body.Item item : items) {
                        foods.add(new Food(item.getFood_name(), item.getFood_1serving(), item.getFood_kcal(), item.getFood_carbohydrates(), item.getFood_protein(), item.getFood_fat(), item.getFood_company()));
                    }
                    searchResults.setValue(foods);
                }
            }

            @Override
            public void onFailure(Call<FoodApiHelper.ResponseClass> call, Throwable t) {
                // Handle failure
            }
        });
        return searchResults;
    }

    public Call<FoodApiHelper.ResponseClass> getFoodNtrItdntList(String foodName) {
        return apiService.getFoodNtrItdntList( SERVICE_KEY, foodName, null, null, null, null, "json" );
    }

    /*public Call<FoodApiHelper.ResponseClass> getFoodNtrItdntList(String foodName) {
        return foodApiHelper.getFoodNtrItdntList(foodName);
    }*/
}

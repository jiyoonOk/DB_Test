
package android.anonymous.db_test.view;

import android.anonymous.db_test.R;
import android.anonymous.db_test.model.entity.Food;
import android.anonymous.db_test.view.FoodListAdapter;
import android.anonymous.db_test.viewmodel.FoodApiViewModel;
import android.anonymous.db_test.viewmodel.FoodApiViewModelFactory;
import android.anonymous.db_test.viewmodel.FoodViewModel;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FoodViewModel foodViewModel;
    private FoodApiViewModel foodApiViewModel;
    private EditText editTextFoodName;
    private Button buttonSaveFood;
    private Button buttonSearchFood;
    private FoodListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextFoodName = findViewById(R.id.editTextFoodName);
        buttonSaveFood = findViewById(R.id.buttonSaveFood);
        buttonSearchFood = findViewById(R.id.buttonSearchFood);

        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);

        // factory를 통해 ViewModel을 생성한다.
        FoodApiViewModelFactory factory = new FoodApiViewModelFactory(getApplication());
        foodApiViewModel = new ViewModelProvider(this, factory).get(FoodApiViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final FoodListAdapter adapter = new FoodListAdapter(new FoodListAdapter.FoodDiff(), foodViewModel);
        recyclerView.setAdapter(adapter);

        // 아이템 클릭 리스너 설정
        adapter.setOnItemClickListener(food -> {
            foodViewModel.insert(food);
        });

        buttonSearchFood.setOnClickListener(v -> {
            String foodName = editTextFoodName.getText().toString();
            foodApiViewModel.searchFood(foodName);

            LiveData<List<Food>> liveData = foodApiViewModel.searchFood(foodName);
            liveData.observe(this, foods -> {
                // 여기에서 foods를 사용하여 UI를 업데이트합니다.
                // 예를 들어, RecyclerView의 어댑터에 foods를 전달할 수 있습니다.
                adapter.submitList(foods);

            });

            buttonSaveFood.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            });


            foodViewModel.getAllFoods().observe(this, foods -> {
                adapter.submitList(foods);
            });
        });
    }
}


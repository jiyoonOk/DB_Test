
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
import android.widget.Toast;

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
        foodApiViewModel = new ViewModelProvider(this, factory).get(FoodApiViewModel.class);    // ViewModelProvider란 ViewModel 객체를 생성하거나 이미 생성된 ViewModel 객체를 반환. 액티비티/프레그먼트의 생명주기 관리 / 데이터 공유

        RecyclerView recyclerView = findViewById(R.id.recyclerView);    // 리사이클러뷰는 뷰홀더 패턴을 사용하여 뷰를 재활용. 뷰홀더 패턴은 뷰를 화면에 표시하기 위해 레이아웃을 로드하고, 뷰를 채우고, 뷰를 반환하는 객체를 사용하는 것. 뷰홀더 패턴은 뷰홀더와 어댑터로 구성. 뷰홀더는 뷰를 저장하는 객체. 어댑터는 뷰홀더를 생성하고, 뷰홀더에 데이터를 바인딩하고, 뷰홀더를 리사이클러뷰에 제공하는 역할.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FoodListAdapter(new FoodListAdapter.FoodDiff(), foodViewModel, true);
        recyclerView.setAdapter(adapter);

        // 아이템 클릭 리스너 설정
        adapter.setOnItemClickListener(food -> {
            foodViewModel.insert(food);
            foodViewModel.getAllFoods().observe(this, foods -> {
                adapter.submitList(foods);
            });
        });

        buttonSearchFood.setOnClickListener(v -> {
            String foodName = editTextFoodName.getText().toString();
            foodApiViewModel.searchFood(foodName).observe(this, foods -> {
                if (!foods.isEmpty() && foods.get(0).getFoodName().startsWith("Error: ")) {
                    String errorMessage = foods.get(0).getFoodName().substring(6);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    adapter.submitList(foods);
                }
            });
        });

        buttonSaveFood.setOnClickListener(view -> {
            foodViewModel.getAllFoods().observe(this, foods -> {
                adapter.submitList(foods);
            });
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });
    }
}


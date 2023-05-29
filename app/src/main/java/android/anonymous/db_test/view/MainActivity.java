
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

        // ViewModelProvider를 사용하여 ViewModel 인스턴스를 가져옵니다.
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        foodApiViewModel = new ViewModelProvider(this, new FoodApiViewModelFactory(getApplication())).get(FoodApiViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);    // 리사이클러뷰는 뷰홀더 패턴을 사용하여 뷰를 재활용. 뷰홀더 패턴은 뷰를 화면에 표시하기 위해 레이아웃을 로드하고, 뷰를 채우고, 뷰를 반환하는 객체를 사용하는 것. 뷰홀더 패턴은 뷰홀더와 어댑터로 구성. 뷰홀더는 뷰를 저장하는 객체. 어댑터는 뷰홀더를 생성하고, 뷰홀더에 데이터를 바인딩하고, 뷰홀더를 리사이클러뷰에 제공하는 역할.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FoodListAdapter(new FoodListAdapter.FoodDiff(), foodViewModel, true);
        recyclerView.setAdapter(adapter);

        // LiveData를 사용하여 데이터를 관찰하고, 데이터가 변경될 때 View를 업데이트합니다.
        foodViewModel.getAllFoods().observe(this, foods -> {
            adapter.submitList(foods);
        });

        // 아이템 클릭 리스너 설정
        adapter.setOnItemClickListener(food -> {
            foodViewModel.insert(food);
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
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });
    }
}


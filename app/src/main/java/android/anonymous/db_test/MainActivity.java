
package android.anonymous.db_test;

import android.anonymous.db_test.model.entity.Food;
import android.anonymous.db_test.viewmodel.FoodViewModel;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private FoodViewModel foodViewModel;
    private EditText editTextFoodName;
    private Button buttonAddFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextFoodName = findViewById(R.id.editTextFoodName);
        buttonAddFood = findViewById(R.id.buttonAddFood);

        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);

        buttonAddFood.setOnClickListener(v -> {
            String foodName = editTextFoodName.getText().toString();
            foodViewModel.insert(new Food(foodName, 0, 0, 0, 0, 0)); // Dummy values
            editTextFoodName.setText("");
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final FoodListAdapter adapter = new FoodListAdapter(new FoodListAdapter.FoodDiff(), foodViewModel);
        recyclerView.setAdapter(adapter);

        foodViewModel.getAllFoods().observe(this, foods -> {
            adapter.submitList(foods);
        });
    }
}
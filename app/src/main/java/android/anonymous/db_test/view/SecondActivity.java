package android.anonymous.db_test.view;

import android.anonymous.db_test.R;
import android.anonymous.db_test.model.entity.Food;
import android.anonymous.db_test.viewmodel.FoodViewModel;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SecondActivity extends AppCompatActivity {

    private FoodViewModel foodViewModel;
    private RecyclerView recyclerViewSavedFoods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        recyclerViewSavedFoods = findViewById(R.id.recyclerViewSavedFoods);

        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewSavedFoods);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final FoodListAdapter adapter = new FoodListAdapter(new FoodListAdapter.FoodDiff(), foodViewModel, false);
        recyclerView.setAdapter(adapter);

        foodViewModel.getAllFoods().observe(this, foods -> {
            adapter.submitList(foods);
        });

        adapter.setOnItemClickListener(food -> {
            new AlertDialog.Builder(this)
                    .setTitle("Food Details")
                    .setMessage(food.toString())
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        });
    }
}
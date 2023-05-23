package android.anonymous.db_test;

import android.anonymous.db_test.viewmodel.FoodViewModel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.anonymous.db_test.R;
import android.anonymous.db_test.model.entity.Food;

public class FoodListAdapter extends ListAdapter<Food, FoodListAdapter.FoodViewHolder> {
    private final FoodViewModel viewModel;

    protected FoodListAdapter(@NonNull DiffUtil.ItemCallback<Food> diffCallback, FoodViewModel viewModel) {
        super(diffCallback);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return FoodViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food current = getItem(position);
        holder.bind(current.getFoodName());
        holder.itemView.setOnClickListener(v -> {
            viewModel.delete(current);
        });
    }

    static class FoodDiff extends DiffUtil.ItemCallback<Food> {

        @Override
        public boolean areItemsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
            return oldItem.getFoodName().equals(newItem.getFoodName());
        }
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        private final TextView foodItemView;

        private FoodViewHolder(View itemView) {
            super(itemView);
            foodItemView = itemView.findViewById(R.id.textView);
        }

        public void bind(String text) {
            foodItemView.setText(text);
        }

        static FoodViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item, parent, false);
            return new FoodViewHolder(view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Food food);
    }
}



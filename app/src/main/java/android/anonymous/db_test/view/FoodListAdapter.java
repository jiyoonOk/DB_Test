package android.anonymous.db_test.view;

import android.anonymous.db_test.databinding.RecyclerviewItemBinding;
import android.anonymous.db_test.viewmodel.FoodViewModel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.anonymous.db_test.R;
import android.anonymous.db_test.model.entity.Food;

import java.util.List;

public class FoodListAdapter extends ListAdapter<Food, FoodListAdapter.FoodViewHolder> {
    private final FoodViewModel viewModel;
    private boolean isMain; // 메인 액티비티인지 여부
    private OnItemClickListener listener;

    protected FoodListAdapter(@NonNull DiffUtil.ItemCallback<Food> diffCallback, FoodViewModel viewModel, boolean isMain) {
        super(diffCallback);
        this.viewModel = viewModel;
        this.isMain = isMain;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());   // 레이아웃 인플레이터는 XML 레이아웃 파일을 실제 뷰 객체로 인스턴스화 하는데 사용. LayoutInflater 객체는 시스템 서비스로 제공. getSystemService() 메서드를 통해 LayoutInflater 객체를 얻을 수 있다.
        RecyclerviewItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.recyclerview_item, parent, false);
        // ItemBinding 클래스는 레이아웃 파일의 루트 뷰를 바인딩 클래스의 루트 뷰로 사용한다. 루트뷰란 레이아웃 파일의 최상위 뷰를 의미한다. 여기서는 recyclerview_item.xml의 최상위 뷰인 LinearLayout이 루트 뷰가 된다.

        return new FoodViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food current = getItem(position);
        holder.bind(current);

        holder.binding.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMain) {
                    // 메인 액티비티에서는 DB에 저장
                    viewModel.insert(current);
                } else {
                    // 세컨드 액티비티에서는 아이템 클릭 리스너 호출
                    if (listener != null) {
                        listener.onItemClick(current);
                    }
                }
            }
        });
    }

    // DiffUtil 클래스는 리사이클러뷰의 아이템이 변경되었을 때, 리사이클러뷰가 변경된 아이템만 업데이트하도록 도와줌.
    static class FoodDiff extends DiffUtil.ItemCallback<Food> {

        @Override
        public boolean areItemsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {  // 두 객체가 같은 항목인지 여부를 결정.
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {   // 두 항목의 데이터가 같은지 여부를 결정.
            return oldItem.getFoodName().equals(newItem.getFoodName());
        }
    }

    // 뷰홀더 클래스 정의. 뷰홀더는 뷰를 저장하는 객체로, 리사이클러뷰에 표시되는 각각의 아이템에 대한 뷰를 저장. static으로 선언한 이유?
    // -> 뷰홀더 클래스는 어댑터 클래스 내부에 선언되는데, 어댑터 클래스는 외부에서 접근할 수 있는 public 클래스이므로 static으로 선언하면 어댑터 클래스 내부에서는 접근할 수 있고, 어댑터 클래스 외부에서는 접근할 수 없다.
    static class FoodViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerviewItemBinding binding;  // 뷰바인딩 객체

        private FoodViewHolder(RecyclerviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Food food) {
            binding.setFood(food);
            binding.executePendingBindings(); // 바인딩을 즉시 실행
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Food food);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

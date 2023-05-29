package android.anonymous.db_test.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

// 이 클래스를 통해 ViewModel 객체를 생성하면, ViewModel 객체는 Application 객체에 대한 참조를 가지게 되므로, Application의 생명주기에 따라서 동작하게 됨.
public class FoodApiViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;

    public FoodApiViewModelFactory(Application application) {
        mApplication = application;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new FoodApiViewModel(mApplication);
    }
}

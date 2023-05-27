package android.anonymous.db_test.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FoodApiViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;

    public FoodApiViewModelFactory(Application application) {
        mApplication = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new FoodApiViewModel(mApplication);
    }
}

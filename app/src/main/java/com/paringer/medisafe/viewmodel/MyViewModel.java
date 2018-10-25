package com.paringer.medisafe.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.paringer.medisafe.model.rest.ServiceGenerator;
import com.paringer.medisafe.model.rest.data.CountryItem;

import java.util.List;

public class MyViewModel extends ViewModel {
    private MutableLiveData<List<CountryItem>> mCountries;
    public LiveData<List<CountryItem> > getCountriesList() {
        if (mCountries == null) {
            mCountries = new MutableLiveData<List<CountryItem>>();
            loadCountries();
        }
        return mCountries;
    }

    private void loadCountries() {
        ServiceGenerator.refreshAsyncRx((list)-> mCountries.setValue(list),(e)->{});
    }
}
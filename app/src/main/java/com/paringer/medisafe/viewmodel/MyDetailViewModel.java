package com.paringer.medisafe.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.paringer.medisafe.model.rest.ServiceGenerator;
import com.paringer.medisafe.model.rest.data.CountryItem;

public class MyDetailViewModel extends ViewModel {
    private MutableLiveData<CountryItem> mCountry;
    public LiveData<CountryItem> getCountry(String name) {
        if (mCountry == null) {
            mCountry = new MutableLiveData<CountryItem>();
            loadCountry(name);
        }
        return mCountry;
    }
    public CountryItem setCountry(CountryItem country){


        if (this.mCountry == null){
            this.mCountry = new MutableLiveData<CountryItem>();
        }
        this.mCountry.setValue(country);
        return country;
    }

    private void loadCountry(String name) {
        ServiceGenerator.refreshAsyncByNameRx(name,(list)-> mCountry.setValue(list.get(0)),(e)->{},()->{});
    }
}
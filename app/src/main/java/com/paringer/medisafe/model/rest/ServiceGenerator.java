package com.paringer.medisafe.model.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paringer.medisafe.model.rest.data.CountryItem;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ServiceGenerator {
    public final static String BASE_API_URL = "https://restcountries.eu";
    private static Gson gson = new GsonBuilder().create();
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor);
    static Retrofit retrofit = null;

    private static OkHttpClient okHttpClient = okHttpClientBuilder.build();

    private static Retrofit getRetrofit() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static <T> T createService(Class<T> serviceClass){
        getRetrofit();
        return retrofit.create(serviceClass);
    }

    public static void refreshAsync(Callback<List<CountryItem> > callback){
        try{
            Retrofit retrofit = getRetrofit();
            Call<List<CountryItem> > countriesAllCall = retrofit
                .create(RestCountriesApi.class)
                .getCountriesAll();
            countriesAllCall.enqueue(callback);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void refreshAsyncRx(Action1<List<CountryItem> > onNext,Action1<Throwable> onError){
        try{
            Retrofit retrofit = getRetrofit();
            Observable<List<CountryItem> > countriesAllObservable = retrofit
                .create(RestCountriesApi.class)
                .getCountriesAllRx();
            countriesAllObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void refreshAsyncByNameRx(String name, Action1<List<CountryItem> > onNext, Action1<Throwable> onError, Action0 onCompleted){
        try{
            Retrofit retrofit = getRetrofit();
            Observable<List<CountryItem> > countriesAllObservable = retrofit
                .create(RestCountriesApi.class)
                .getCountriesByNameRx(name);
            countriesAllObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError, onCompleted);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
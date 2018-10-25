package com.paringer.medisafe.model.rest;

import java.util.List;

import com.paringer.medisafe.model.rest.data.CountryItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Zhenya on 23.10.2018.
 */

    public interface RestCountriesApi {

        @GET("/rest/v2/all")
        Call<List<CountryItem> > getCountriesAll();

        @GET("/rest/v2/all")
        Observable<List<CountryItem> > getCountriesAllRx();

        @GET("/rest/v2/name/{name}")
        Observable<List<CountryItem> > getCountriesByNameRx(@Path("name") String name);

}

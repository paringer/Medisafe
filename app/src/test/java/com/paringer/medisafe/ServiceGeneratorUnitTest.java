package com.paringer.medisafe;

import android.os.SystemClock;

import com.paringer.medisafe.model.rest.ServiceGenerator;
import com.paringer.medisafe.model.rest.data.CountryItem;

import org.junit.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ServiceGeneratorUnitTest {
    @Test
    public void refreshAsyncTest() throws Exception {
        final List<CountryItem> myList[] = new List[1];
        ServiceGenerator.refreshAsync(new Callback<List<CountryItem>>() {
            @Override
            public void onResponse(Call<List<CountryItem>> call, Response<List<CountryItem>> response) {
                myList[0] = response.body();
            }

            @Override
            public void onFailure(Call<List<CountryItem>> call, Throwable t) {
                myList[0] = null;
            }
        });
        Thread.sleep(3000);
        final List<CountryItem> list = myList[0];
        assertNotNull("rest request country by name failed", list);
        assertNotNull("rest request country by name failed", list.get(0));
        assertEquals("Ukraine", list.get(236).getName());
        assertEquals("Kiev", list.get(236).getCapital());
    }
}
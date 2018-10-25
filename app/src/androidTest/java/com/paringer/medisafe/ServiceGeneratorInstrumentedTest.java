package com.paringer.medisafe;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.paringer.medisafe.model.rest.ServiceGenerator;
import com.paringer.medisafe.model.rest.data.CountryItem;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ServiceGeneratorInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.paringer.medisafe", appContext.getPackageName());
    }

    @Test
    public void refreshAsyncRxTest() throws Exception {
        final List<CountryItem> myList[] = new List[1];
        ServiceGenerator.refreshAsyncRx((list)->myList[0]=list,(e)->{});
        Thread.sleep(3000);
        final List<CountryItem> list = myList[0];
        assertNotNull("rest request country by name failed", list);
        assertNotNull("rest request country by name failed", list.get(0));
        assertEquals("Ukraine", list.get(236).getName());
        assertEquals("Kiev", list.get(236).getCapital());
    }

    @Test
    public void refreshAsyncByNameRxTest() throws Exception {
        final List<CountryItem> myList[] = new List[1];
        ServiceGenerator.refreshAsyncByNameRx("Ukraine", (list)->myList[0]=list,(e)->{},()->{});
        Thread.sleep(3000);
        final List<CountryItem> list = myList[0];
        assertNotNull("rest request country by name failed", list);
        assertNotNull("rest request country by name failed", list.get(0));
        assertEquals("Ukraine", list.get(0).getName());
        assertEquals("Kiev", list.get(0).getCapital());
    }

}

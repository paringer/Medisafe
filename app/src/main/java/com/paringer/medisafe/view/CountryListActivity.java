package com.paringer.medisafe.view;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.adapters.TextViewBindingAdapter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.paringer.medisafe.R;
import com.paringer.medisafe.adapter.BindingAdapter;
import com.paringer.medisafe.model.rest.ServiceGenerator;
import com.paringer.medisafe.model.rest.data.CountryItem;
import com.paringer.medisafe.view.CountryDetailActivity;
import com.paringer.medisafe.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static rx.Observable.just;

/**
 * An activity representing a list of countryItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link CountryDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CountryListActivity extends AppCompatActivity implements TextViewBindingAdapter.OnTextChanged {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private List<CountryItem> mCountries = new ArrayList<>();
    private Unbinder unbinder;
    @BindView(R.id.countries_list)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.searchText)
    EditText searchText;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private PublishSubject<String> rxSearch;
//    private rx.Observable<String> rxSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);
        unbinder = ButterKnife.bind(this);
        rxSearch = PublishSubject.<String>create();
        rxSearch
            .debounce(1000, TimeUnit.MILLISECONDS)
            .map(s->s.trim())
            .distinctUntilChanged()
            .<String>switchMap( query->onSearch(query) )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        .subscribe();

        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        fab.setOnClickListener((x)->{});

        if (findViewById(R.id.country_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);
        model.getCountriesList().observe(this, this::onDataReceived);
        setupRecyclerView(recyclerView);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnTextChanged(R.id.searchText)
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s!= null)
            rxSearch.onNext(s.toString());
    }

    public rx.Observable<String> onSearch(String s) {
        if (s.isEmpty())
            ServiceGenerator.refreshAsyncRx(this::onDataReceived, this::onError);
        else
            ServiceGenerator.refreshAsyncByNameRx(s, this::onDataReceived, this::onError, () -> {});
        return rx.Observable.just(s);
    }

    @OnEditorAction(R.id.searchText)
    public boolean onEditorAction(TextView view, int action, KeyEvent event){
        if (action == EditorInfo.IME_ACTION_DONE
            || action == EditorInfo.IME_ACTION_SEARCH
            || action == EditorInfo.IME_NULL && event != null && event.getAction() == KeyEvent.ACTION_DOWN){
            rxSearch.onNext(String.valueOf(view.getText()));
            hideKeyboardFrom(this, view);
        }
        return true;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    void onError(Throwable throwable) {
    }


    void onDataReceived(List<CountryItem> list){
        List old = mCountries;
        mCountries = list;
        old.clear();
        if(recyclerView!= null) setupRecyclerView(recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new BindingAdapter(this, mCountries, mTwoPane));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
            RecyclerView.VERTICAL);
        dividerItemDecoration.setDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_blue_light)));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

}

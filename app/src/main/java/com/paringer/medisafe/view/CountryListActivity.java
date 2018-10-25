package com.paringer.medisafe.view;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;

import com.paringer.medisafe.R;
import com.paringer.medisafe.adapter.BindingAdapter;
import com.paringer.medisafe.model.rest.ServiceGenerator;
import com.paringer.medisafe.model.rest.data.CountryItem;
import com.paringer.medisafe.view.CountryDetailActivity;
import com.paringer.medisafe.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * An activity representing a list of countryItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link CountryDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CountryListActivity extends AppCompatActivity {

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
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);
        unbinder = ButterKnife.bind(this);

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

//        ServiceGenerator.refreshAsyncByNameRx("stan", this::onDataReceived, this::onError, this::onInitCompleted);
        MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);
        model.getCountriesList().observe(this, this::onDataReceived);
        setupRecyclerView(recyclerView);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    void onError(Throwable throwable) {
    }

    void onInitCompleted(){
        ServiceGenerator.refreshAsyncRx(this::onDataReceived, this::onError);
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
//        dividerItemDecoration.setDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_blue_dark)));
//        dividerItemDecoration.setDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_blue_bright)));
        dividerItemDecoration.setDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_blue_light)));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

}

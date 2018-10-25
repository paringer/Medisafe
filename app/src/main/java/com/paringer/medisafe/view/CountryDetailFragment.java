package com.paringer.medisafe.view;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paringer.medisafe.R;
import com.paringer.medisafe.databinding.CountryDetailBinding;
import com.paringer.medisafe.model.rest.ServiceGenerator;
import com.paringer.medisafe.model.rest.data.CountryItem;
import com.paringer.medisafe.viewmodel.MyDetailViewModel;
import com.paringer.medisafe.viewmodel.MyViewModel;

import java.util.List;

/**
 * A fragment representing a single CountryItem detail screen.
 * This fragment is either contained in a {@link CountryListActivity}
 * in two-pane mode (on tablets) or a {@link CountryDetailActivity}
 * on handsets.
 */
public class CountryDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private CountryDetailBinding binding;
    private CountryItem countryItem;
    private String mName;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CountryDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
//            mItem = StubContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            mName = getArguments().getString(ARG_ITEM_ID);
            ServiceGenerator.refreshAsyncByNameRx(mName, this::onDataReceived, (e) -> {}, () -> {});
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mName);
            }
        }
    }

    @NonNull
    private void onDataReceived(List<CountryItem> list) {
        if (binding != null && list != null && list.size() > 0) {
            countryItem = list.get(0);
            binding.setData(countryItem);
            MyDetailViewModel model = ViewModelProviders.of(getActivity()).get(MyDetailViewModel.class);
            model.setCountry(countryItem);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.country_detail, container, false);
        binding = CountryDetailBinding.inflate(inflater);
        View rootView = binding.getRoot();

        // Show the dummy content as text in a TextView.
        if (mName != null) {
            ((TextView) rootView.findViewById(R.id.detail)).setText(mName);
        }

        return rootView;
    }
}

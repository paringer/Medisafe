package com.paringer.medisafe.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paringer.medisafe.view.CountryDetailActivity;
import com.paringer.medisafe.view.CountryDetailFragment;
import com.paringer.medisafe.view.CountryListActivity;
import com.paringer.medisafe.R;
import com.paringer.medisafe.databinding.CountryItemContentBinding;
import com.paringer.medisafe.model.rest.data.CountryItem;

import java.util.List;

/**
 * Created by Zhenya on 24.10.2018.
 */
public class BindingAdapter
    extends RecyclerView.Adapter<BindingAdapter.ViewHolder> {

    private final CountryListActivity mParentActivity;
    private final List<CountryItem> mValues;
    private final boolean mTwoPane;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CountryItem item = (CountryItem) view.getTag();
            if (mTwoPane) {
                setupFragment(item);
            } else {
                startActivity(view.getContext(), item);
            }
        }
    };

    private void startActivity(Context context, CountryItem item) {
        Intent intent = new Intent(context, CountryDetailActivity.class);
        intent.putExtra(CountryDetailFragment.ARG_ITEM_ID, item.getName());
        context.startActivity(intent);
    }

    private void setupFragment(CountryItem item) {
        Bundle arguments = new Bundle();
        arguments.putString(CountryDetailFragment.ARG_ITEM_ID, item.getName());
        CountryDetailFragment fragment = new CountryDetailFragment();
        fragment.setArguments(arguments);
        mParentActivity.getSupportFragmentManager().beginTransaction()
            .replace(R.id.country_detail_container, fragment)
            .commit();
    }

    public BindingAdapter(CountryListActivity parent,
                          List<CountryItem> items,
                          boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(R.layout.country_item_content, parent, false);
        CountryItemContentBinding binding = CountryItemContentBinding.inflate(inflater);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);

        holder.itemView.setTag(mValues.get(position));
//        CountryItemContentBinding.bind(holder.itemView);
        holder.mBinding.setData(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;
        final CountryItemContentBinding mBinding;

        ViewHolder(View view, CountryItemContentBinding binding) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.nameText);
            mContentView = (TextView) view.findViewById(R.id.nativeNameText);
            mBinding = binding;
        }
    }
}

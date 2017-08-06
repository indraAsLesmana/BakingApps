package id.co.blogspot.tutor93.bakingapps.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.blogspot.tutor93.bakingapps.R;
import id.co.blogspot.tutor93.bakingapps.data.network.response.BakingResponse;
import id.co.blogspot.tutor93.bakingapps.main.dummy.DummyContent;
import id.co.blogspot.tutor93.bakingapps.main_detail.MainDetailActivity;
import id.co.blogspot.tutor93.bakingapps.main_detail.MainDetailFragment;

/**
 * Created by indraaguslesmana on 8/5/17.
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

    private final List<BakingResponse> mValues;
    private final boolean mTwoPane;

    public MainListAdapter(List<BakingResponse> items, boolean twopane) {
        mValues = items;
        mTwoPane = twopane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipecard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final BakingResponse mItem = mValues.get(position);
        holder.mIdView.setText(mItem.name);
        holder.mContentView.setText(String.valueOf(mItem.servings));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(MainDetailFragment.ARG_ITEM_ID, mValues.get(position));
                    MainDetailFragment fragment = new MainDetailFragment();
                    fragment.setArguments(arguments);
                    ((Activity) v.getContext()).getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MainDetailActivity.class);
                    intent.putExtra(MainDetailFragment.ARG_ITEM_ID, mValues.get(position));

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        @BindView(R.id.item_recipename) TextView mIdView;
        @BindView(R.id.content) TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }

}

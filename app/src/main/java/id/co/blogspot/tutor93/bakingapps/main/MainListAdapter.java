package id.co.blogspot.tutor93.bakingapps.main;

import android.content.Context;
import android.content.Intent;
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
import id.co.blogspot.tutor93.bakingapps.main_detail.ItemListActivity;

/**
 * Created by indraaguslesmana on 8/5/17.
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

    private final List<BakingResponse> mValues;

    public MainListAdapter(List<BakingResponse> items) {
        mValues = items;
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
        holder.mServing.setText(String.valueOf(mItem.servings));
        holder.mStep.setText(String.valueOf(mItem.steps.size()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ItemListActivity.class);
                intent.putExtra(ItemListActivity.ARG_RECIPES_ID, mValues.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        @BindView(R.id.detail_itemrecipename) TextView mIdView;
        @BindView(R.id.serving) TextView mServing;
        @BindView(R.id.step) TextView mStep;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }

}

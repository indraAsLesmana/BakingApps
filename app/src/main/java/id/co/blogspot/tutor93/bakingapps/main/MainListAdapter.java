package id.co.blogspot.tutor93.bakingapps.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.blogspot.tutor93.bakingapps.R;
import id.co.blogspot.tutor93.bakingapps.data.network.response.BakingResponse;
import id.co.blogspot.tutor93.bakingapps.main_detail.ItemDetailActivity;

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
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra(ItemDetailActivity.ARG_RECIPES_ID, mItem);
                context.startActivity(intent);
            }
        });

        if (!mItem.image.isEmpty())
            Glide.with(holder.mView.getContext())
                    .load(mItem.image)
                    .error(R.drawable.pizza)
                    .into(holder.imageRecipe);

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
        @BindView(R.id.image_recipe) ImageView imageRecipe;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }

}

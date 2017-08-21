package id.co.blogspot.tutor93.bakingapps.main_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import id.co.blogspot.tutor93.bakingapps.R;

import id.co.blogspot.tutor93.bakingapps.base.BaseActivity;
import id.co.blogspot.tutor93.bakingapps.data.model.Ingredient;
import id.co.blogspot.tutor93.bakingapps.data.model.Step;
import id.co.blogspot.tutor93.bakingapps.data.network.response.BakingResponse;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends BaseActivity {

    private boolean mTwoPane;
    public static final String ARG_RECIPES_ID = "recipe_id";
    private BakingResponse mItemBakingRespose;
    private ArrayList<TextView> mIngredientList = new ArrayList<>();
    private LinearLayout mIngridientListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        mIngridientListView = (LinearLayout) findViewById(R.id.ingridient_list);

        if (getIntent().getParcelableExtra(ARG_RECIPES_ID) != null)
            mItemBakingRespose = getIntent().getParcelableExtra(ARG_RECIPES_ID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!mItemBakingRespose.name.isEmpty() && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mItemBakingRespose.name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (mIngredientList.size() > 0) {
            for (TextView tvIngredientView : mIngredientList) {
                mIngridientListView.addView(tvIngredientView);
            }
        } else {
            if (mItemBakingRespose.ingredients != null && mItemBakingRespose.ingredients.size() > 0) {
                for (Ingredient ingredient : mItemBakingRespose.ingredients) {
                    TextView textView = new TextView(this);
                    textView.setTextColor(Color.BLACK);
                    textView.setPadding(16, 8, 16, 8);
                    textView.setText("- " + String.valueOf(ingredient.quantity) +
                            String.valueOf(ingredient.measure) + " " + ingredient.ingredient);
                    mIngridientListView.addView(textView);
                    mIngredientList.add(textView);
                }
            }
        }

        View recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) mTwoPane = true;

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(mItemBakingRespose.steps, mItemBakingRespose.name));
    }

    class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Step> mValues;
        private final String mRecipesName;

        SimpleItemRecyclerViewAdapter(List<Step> items, String recipesName) {
            mValues = items;
            mRecipesName = recipesName;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_stepcard, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mStepCount.setText(String.valueOf(
                    holder.mView.getContext().getString(R.string.step_count,
                            holder.mItem.id == 0 ? "Intro" : holder.mItem.id.toString())));
            holder.mShortdescription.setText(mValues.get(position).shortDescription);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putParcelable(ItemDetailFragment.ARG_ITEM_ID, holder.mItem);
                        arguments.putString(ItemDetailFragment.ARG_ITEM_NAME, mRecipesName);
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ItemDetailActivity.class);
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem);
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_NAME, mRecipesName);
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
            final TextView mStepCount;
            final TextView mShortdescription;
            Step mItem;

            ViewHolder(View view) {
                super(view);
                mView = view;
                mStepCount = (TextView) view.findViewById(R.id.step_count);
                mShortdescription = (TextView) view.findViewById(R.id.tv_shortdescription);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mShortdescription.getText() + "'";
            }
        }
    }
}

package id.co.blogspot.tutor93.bakingapps.main_detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import id.co.blogspot.tutor93.bakingapps.R;

import id.co.blogspot.tutor93.bakingapps.base.BaseActivity;
import id.co.blogspot.tutor93.bakingapps.data.model.Ingredient;
import id.co.blogspot.tutor93.bakingapps.data.model.Step;
import id.co.blogspot.tutor93.bakingapps.data.network.response.BakingResponse;
import id.co.blogspot.tutor93.bakingapps.widget.BakingAppsIntentService;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailActivity extends BaseActivity implements ItemDetailAdapter.ListItemClickListener, ItemDetailFragment.ListItemClickListener{

    private boolean mTwoPane;
    public static final String ARG_RECIPES_ID = "recipe_id";
    private BakingResponse mItemBakingRespose;
    private ArrayList<TextView> mIngredientList = new ArrayList<>();
    private LinearLayout mIngridientListView;
    private ItemDetailAdapter mItemDetailAdapter;

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
        setupRecyclerView((RecyclerView) recyclerView);
        if (findViewById(R.id.item_detail_container) != null) mTwoPane = true;

        //widget update here
        BakingAppsIntentService.startBakingService(this, mItemBakingRespose.ingredients);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mItemDetailAdapter = new ItemDetailAdapter(this);
        if (mItemBakingRespose != null)
            mItemDetailAdapter.setupData(mItemBakingRespose.steps, mItemBakingRespose.name);
        recyclerView.setAdapter(mItemDetailAdapter);
    }

    @Override
    public void onListItemClick(List<Step> stepsOut, int clickedItemIndex, String recipeName) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(ItemDetailFragment.ARG_ITEM_ID, (ArrayList<? extends Parcelable>) stepsOut);
            arguments.putString(ItemDetailFragment.ARG_ITEM_NAME, recipeName);
            arguments.putInt(ItemDetailFragment.ARG_ITEM_INDEX, clickedItemIndex);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, ItemEndActivity.class);
            intent.putParcelableArrayListExtra(ItemDetailFragment.ARG_ITEM_ID, (ArrayList<? extends Parcelable>) stepsOut);
            intent.putExtra(ItemDetailFragment.ARG_ITEM_NAME, recipeName);
            intent.putExtra(ItemDetailFragment.ARG_ITEM_INDEX, clickedItemIndex);
            this.startActivity(intent);
        }
    }
}

package id.co.blogspot.tutor93.bakingapps.main_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import id.co.blogspot.tutor93.bakingapps.main_detail.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends BaseActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
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

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(mItemBakingRespose.steps));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Step> mValues;

        public SimpleItemRecyclerViewAdapter(List<Step> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(String.valueOf(mValues.get(position).id));
            holder.mContentView.setText(mValues.get(position).description);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putParcelable(ItemDetailFragment.ARG_ITEM_ID, mItemBakingRespose);
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ItemDetailActivity.class);
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, mItemBakingRespose);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Step mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}

package id.co.blogspot.tutor93.bakingapps.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.blogspot.tutor93.bakingapps.R;

import id.co.blogspot.tutor93.bakingapps.base.BaseActivity;
import id.co.blogspot.tutor93.bakingapps.data.DataManager;
import id.co.blogspot.tutor93.bakingapps.data.network.response.BakingResponse;
import id.co.blogspot.tutor93.bakingapps.main_detail.MainDetailActivity;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MainDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MainListActivity extends BaseActivity implements MainContract.MainAction {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.item_list) RecyclerView recyclerView;

    private MainPresenter mMainPresenter;
    private List<BakingResponse> recipeItems;
    private MainListAdapter mainListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this);

        initView();
        mMainPresenter = new MainPresenter(DataManager.getInstance());
        mMainPresenter.attachView(this);
        mMainPresenter.onRecipesRequest();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        if (findViewById(R.id.item_detail_container) != null) { // large-screen layouts (res/values-w900dp).
            mTwoPane = true;
        }
        setupRecipesList();
    }

    private void setupRecipesList() {
        assert recyclerView != null;
        recipeItems = new ArrayList<>();
        mainListAdapter = new MainListAdapter(recipeItems, mTwoPane);
        recyclerView.setAdapter(mainListAdapter);
    }

    @Override
    public void showDetailList(List<BakingResponse> recipes) {
        recipeItems.addAll(recipes);
        mainListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showMessageLayout(boolean show) {

    }
}

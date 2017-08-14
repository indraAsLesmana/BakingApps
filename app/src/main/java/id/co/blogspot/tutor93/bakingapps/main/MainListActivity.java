package id.co.blogspot.tutor93.bakingapps.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

public class MainListActivity extends BaseActivity implements MainContract.MainAction {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.item_list) RecyclerView recyclerView;

    private MainPresenter mMainPresenter;
    private List<BakingResponse> recipeItems;
    private MainListAdapter mainListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        ButterKnife.bind(this, MainListActivity.this);

        initView();
        mMainPresenter = new MainPresenter(DataManager.getInstance());
        mMainPresenter.attachView(this);
        mMainPresenter.onRecipesRequest();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        setupRecipesList();
    }

    private void checkPhoneOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    private void setupRecipesList() {
        checkPhoneOrientation();

        recipeItems = new ArrayList<>();
        mainListAdapter = new MainListAdapter(recipeItems);
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

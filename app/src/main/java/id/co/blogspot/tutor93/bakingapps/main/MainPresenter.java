package id.co.blogspot.tutor93.bakingapps.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.co.blogspot.tutor93.bakingapps.base.BasePresenter;
import id.co.blogspot.tutor93.bakingapps.data.DataManager;
import id.co.blogspot.tutor93.bakingapps.data.network.RemoteCallback;
import id.co.blogspot.tutor93.bakingapps.data.network.response.BakingResponse;
import id.co.blogspot.tutor93.bakingapps.util.Helpers;

/**
 * Created by indraaguslesmana on 8/6/17.
 */

public class MainPresenter extends BasePresenter<MainContract.MainAction> implements MainContract.MainRequest{

    private DataManager mDataManager;
    private static final String TAG = "MainPresenter";

    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void onItemClickRequest() {

    }

    @Override
    public void onRecipesRequest() {
        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        mDataManager.getRecipes(new RemoteCallback<List<BakingResponse>>() {
            @Override
            public void onSuccess(List<BakingResponse> response) {
                mView.hideProgress();

                if (response != null){
                    mView.showDetailList(response);
                }else {
                    mView.showEmpty();
                }
            }

            @Override
            public void onUnauthorized() {
                mView.hideProgress();
                //nothing
            }

            @Override
            public void onFailed(Throwable throwable) {
                mView.hideProgress();
                Helpers.log(TAG, "failed network:", throwable);
                mView.showError(throwable.getMessage());
            }
        });
    }
}

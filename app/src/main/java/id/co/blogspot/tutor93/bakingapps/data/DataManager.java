package id.co.blogspot.tutor93.bakingapps.data;

import java.util.List;

import id.co.blogspot.tutor93.bakingapps.data.network.BakingService;
import id.co.blogspot.tutor93.bakingapps.data.network.BakingServiceFactory;
import id.co.blogspot.tutor93.bakingapps.data.network.RemoteCallback;
import id.co.blogspot.tutor93.bakingapps.data.network.response.BakingResponse;

/**
 * Created by indraaguslesmana on 8/4/17.
 */

public class DataManager {

    private static DataManager sInstance;

    private final BakingService mBakingService;

    public static DataManager getInstance() {
        if (sInstance == null) {
            sInstance = new DataManager();
        }
        return sInstance;
    }

    private DataManager() {
        mBakingService = BakingServiceFactory.makeBakingService();
    }

    public void getRecipes(RemoteCallback<List<BakingResponse>> listener) {
        mBakingService.getRecipes().enqueue(listener);
    }
}

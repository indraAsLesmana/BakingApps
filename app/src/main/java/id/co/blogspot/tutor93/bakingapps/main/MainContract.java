package id.co.blogspot.tutor93.bakingapps.main;

import java.util.List;

import id.co.blogspot.tutor93.bakingapps.base.RemoteView;
import id.co.blogspot.tutor93.bakingapps.data.network.response.BakingResponse;

/**
 * Created by indraaguslesmana on 8/4/17.
 */

public interface MainContract {

    interface MainRequest {

        void onItemClickRequest();

        void onRecipesRequest();
    }

    interface MainAction extends RemoteView{

        void showDetailList(List<BakingResponse> recipes);
    }
}

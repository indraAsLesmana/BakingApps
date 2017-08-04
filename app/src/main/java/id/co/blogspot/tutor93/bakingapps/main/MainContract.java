package id.co.blogspot.tutor93.bakingapps.main;

import id.co.blogspot.tutor93.bakingapps.base.RemoteView;

/**
 * Created by indraaguslesmana on 8/4/17.
 */

public interface MainContract {

    interface MainRequest {

        void onItemClickRequest();
    }

    interface MainAction extends RemoteView{

        void showDetailList();
    }
}

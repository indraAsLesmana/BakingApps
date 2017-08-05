package id.co.blogspot.tutor93.bakingapps.main_detail;

import id.co.blogspot.tutor93.bakingapps.base.RemoteView;

/**
 * Created by indraaguslesmana on 8/4/17.
 */

public interface MainDetailContract {

    interface MainDetailRequest {

        void onPlayVideoRequest();
    }

    interface MainDetailAction extends RemoteView {

        void showVideo();
    }
}

package id.co.blogspot.tutor93.bakingapps.util;

import android.app.Activity;
import android.util.Log;
import android.view.View;

/**
 * Created by indraaguslesmana on 8/6/17.
 */

public class Helpers {

    public static void log(String TAG, String message, Throwable throwable) {
        if(Constants.ENABLE_LOG) {
            Log.v(TAG, message, throwable);
        }
    }

    public static void hideSystemUI(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
}

package id.co.blogspot.tutor93.bakingapps.util;

import android.util.Log;

/**
 * Created by indraaguslesmana on 8/6/17.
 */

public class Helpers {

    public static void log(String TAG, String message, Throwable throwable) {
        if(Constants.ENABLE_LOG) {
            Log.v(TAG, message, throwable);
        }
    }
}

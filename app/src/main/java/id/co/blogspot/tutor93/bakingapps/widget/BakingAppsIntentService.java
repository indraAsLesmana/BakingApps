package id.co.blogspot.tutor93.bakingapps.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import id.co.blogspot.tutor93.bakingapps.data.model.Ingredient;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class BakingAppsIntentService extends IntentService {
    //action
    public static final String UPDATE_INGRIDIENT_LIST = "id.co.blogspot.tutor93.bakingapps.widget.action.UPDATE_INGRIDIENT";
    //data
    public static final String INGRIDIENT_LIST = "id.co.blogspot.tutor93.bakingapps.widget.extra.PARAM1";

    public static void startBakingService(Context context, List<Ingredient> ingridients) {
        Intent intent = new Intent(context, BakingAppsIntentService.class);
        intent.setAction(UPDATE_INGRIDIENT_LIST);
        intent.putParcelableArrayListExtra(INGRIDIENT_LIST, (ArrayList<? extends Parcelable>) ingridients);
        context.startService(intent);
    }

    public BakingAppsIntentService() {
        super("BakingAppsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (UPDATE_INGRIDIENT_LIST.equals(action)) {
                ArrayList<Ingredient> listIngridient = intent.getExtras().getParcelableArrayList(INGRIDIENT_LIST);
                handleActionUpdateIngridient(listIngridient);
            }
        }
    }

    private void handleActionUpdateIngridient(ArrayList<Ingredient> listIngridient) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppsWidget.class));

        BakingAppsWidget.setUpdateAppWidget(this, appWidgetManager, appWidgetIds, listIngridient);
    }
}

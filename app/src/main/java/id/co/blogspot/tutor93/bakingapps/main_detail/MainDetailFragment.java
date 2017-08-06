package id.co.blogspot.tutor93.bakingapps.main_detail;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.blogspot.tutor93.bakingapps.R;
import id.co.blogspot.tutor93.bakingapps.data.network.response.BakingResponse;
import id.co.blogspot.tutor93.bakingapps.main.MainListActivity;
import id.co.blogspot.tutor93.bakingapps.main.dummy.DummyContent;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link MainListActivity}
 * in two-pane mode (on tablets) or a {@link MainDetailActivity}
 * on handsets.
 */
public class MainDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private BakingResponse mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    @BindView(R.id.item_recipename) TextView mRecipeName;

    public MainDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
//            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_recipecard, container, false);
        ButterKnife.bind(this, rootView);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            mRecipeName.setText(mItem.servings);
        }

        return rootView;
    }
}

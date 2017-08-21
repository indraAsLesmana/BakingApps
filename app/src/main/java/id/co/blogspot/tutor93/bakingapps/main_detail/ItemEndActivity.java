package id.co.blogspot.tutor93.bakingapps.main_detail;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import id.co.blogspot.tutor93.bakingapps.R;
import id.co.blogspot.tutor93.bakingapps.base.BaseActivity;
import id.co.blogspot.tutor93.bakingapps.data.model.Step;

public class ItemEndActivity extends BaseActivity implements ItemDetailFragment.ListItemClickListener{

    String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        if (savedInstanceState != null) {
            recipeName = savedInstanceState.getString(ItemDetailFragment.ARG_ITEM_NAME);
        }else {
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(ItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getParcelableArrayListExtra(ItemDetailFragment.ARG_ITEM_ID));

            arguments.putInt(ItemDetailFragment.ARG_ITEM_INDEX,
                    getIntent().getIntExtra(ItemDetailFragment.ARG_ITEM_INDEX, 0));

            recipeName = getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_NAME);
            arguments.putString(ItemDetailFragment.ARG_ITEM_NAME, recipeName);

            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment, "DetailFragment")
                    .commit();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (recipeName != null) getSupportActionBar().setTitle(recipeName);
        }
    }

    @Override
    public void onListItemClick(List<Step> allSteps, int Index, String recipeName) {
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ItemDetailFragment.ARG_ITEM_ID,
                (ArrayList<? extends Parcelable>) allSteps);
        arguments.putInt(ItemDetailFragment.ARG_ITEM_INDEX, Index);
        arguments.putString(ItemDetailFragment.ARG_ITEM_NAME, recipeName);
        Fragment oldFragment = getSupportFragmentManager().findFragmentByTag("DetailFragment");
        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .detach(oldFragment)
                .replace(R.id.item_detail_container, fragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ItemDetailFragment.ARG_ITEM_NAME, recipeName);
    }
}

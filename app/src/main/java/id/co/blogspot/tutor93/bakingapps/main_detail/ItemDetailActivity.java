package id.co.blogspot.tutor93.bakingapps.main_detail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import id.co.blogspot.tutor93.bakingapps.R;
import id.co.blogspot.tutor93.bakingapps.base.BaseActivity;

public class ItemDetailActivity extends BaseActivity {

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(ItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getParcelableExtra(ItemDetailFragment.ARG_ITEM_ID));
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);

            if (getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_NAME) != null) {
                title = getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_NAME);
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (title != null) getSupportActionBar().setTitle(title);
        }
    }
}

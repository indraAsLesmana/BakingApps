package id.co.blogspot.tutor93.bakingapps.main_detail;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import id.co.blogspot.tutor93.bakingapps.R;
import id.co.blogspot.tutor93.bakingapps.data.model.Step;
import id.co.blogspot.tutor93.bakingapps.util.Helpers;

public class ItemDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    public static String ARG_ITEM_NAME = "item_recipes_name";
    public static String ARG_ITEM_INDEX = "item_index";
    private ArrayList<Step> mItemList = new ArrayList<>();
    private int mItemIndexSelected;
    private String mItemName;
    private AppCompatActivity mActivity;
    private SimpleExoPlayerView exoPlayerView;
    private SimpleExoPlayer player;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private DataSource.Factory mediaDataSourceFactory;
    private Handler mainHandler;
    private TextView detailStep;
    private static final String TAG = "ItemDetailFragment";
    View rootView;

    private ListItemClickListener itemClickListener;
    private View nav;

    public interface ListItemClickListener {
        void onListItemClick(List<Step> allSteps, int Index, String recipeName);
    }

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemList = getArguments().getParcelableArrayList(ARG_ITEM_ID);
        mItemIndexSelected = getArguments().getInt(ARG_ITEM_INDEX);
        mItemName = getArguments().getString(ARG_ITEM_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        initView(rootView);
        if (!isTabletMode()){
            itemClickListener = (ItemEndActivity) getActivity();
        }

        if (isLanscapeMode() && !isTabletMode()) makeVideoFullScreen(mActivity);

        Button mPrevStep = (Button) rootView.findViewById(R.id.btn_previus);
        Button mNextstep = (Button) rootView.findViewById(R.id.btn_next);

        if (!isTabletMode() && !isLanscapeMode()) {
            nav.setVisibility(View.VISIBLE);
            mPrevStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemList.get(mItemIndexSelected).id > 0) {
                        if (player != null) {
                            player.stop();
                        }
                        itemClickListener.onListItemClick(mItemList, mItemList.get(mItemIndexSelected).id - 1, mItemName);
                    } else {
                        Toast.makeText(getActivity(), "You're in the First step", Toast.LENGTH_SHORT).show();

                    }
                }
            });

            mNextstep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int lastIndex = mItemList.size() - 1;
                    if (mItemList.get(mItemIndexSelected).id < mItemList.get(lastIndex).id) {
                        if (player != null) {
                            player.stop();
                        }
                        itemClickListener.onListItemClick(mItemList, mItemList.get(mItemIndexSelected).id + 1, mItemName);
                    } else {
                        Toast.makeText(getContext(), "You're in the Final step", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }else {
            nav.setVisibility(View.GONE);
        }

        return rootView;
    }

    private void initView(View rootview) {
        mActivity = (AppCompatActivity) getActivity();

        nav = rootview.findViewById(R.id.layout_navigation);
        detailStep = (TextView) rootview.findViewById(R.id.detail_step);
        exoPlayerView = (SimpleExoPlayerView) rootview.findViewById(R.id.exoPlayerView);
        mediaDataSourceFactory = buildDataSourceFactory(true);

        mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        LoadControl loadControl = new DefaultLoadControl();

        player = ExoPlayerFactory.newSimpleInstance(mActivity, trackSelector, loadControl);
        exoPlayerView = (SimpleExoPlayerView) rootview.findViewById(R.id.exoPlayerView);
        exoPlayerView.setPlayer(player);
        exoPlayerView.setUseController(true);
        exoPlayerView.requestFocus();

        if (!mItemList.get(mItemIndexSelected).videoURL.isEmpty()) {
            String videoUrl = mItemList.get(mItemIndexSelected).videoURL;
            Uri uri = Uri.parse(videoUrl);
            MediaSource mediaSource = buildMediaSource(uri, "mp3");
            player.prepare(mediaSource);
        }

        if (!mItemList.get(mItemIndexSelected).description.isEmpty()) detailStep.setText(mItemList.get(mItemIndexSelected).description);


        player.addListener(new ExoPlayer.EventListener() {
            @Override public void onTimelineChanged(Timeline timeline, Object manifest) {
                Log.d(TAG, "onTimelineChanged: ");
            }

            @Override public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.d(TAG, "onTracksChanged: " + trackGroups.length);
            }

            @Override public void onLoadingChanged(boolean isLoading) {
                Log.d(TAG, "onLoadingChanged: " + isLoading);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.d(TAG, "onPlayerStateChanged: " + playWhenReady);
            }

            @Override public void onPlayerError(ExoPlaybackException error) {
                Log.e(TAG, "onPlayerError: ", error);
            }

            @Override public void onPositionDiscontinuity() {
                Log.d(TAG, "onPositionDiscontinuity: true");
            }
        });

    }

    private void makeVideoFullScreen(Activity context) {
        Helpers.hideSystemUI(context);
        exoPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        exoPlayerView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    private boolean isLanscapeMode() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private boolean isTabletMode() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    private MediaSource buildMediaSource(Uri uri, String overrideExtension) {
        int type = TextUtils.isEmpty(overrideExtension) ? Util.inferContentType(uri)
                : Util.inferContentType("." + overrideExtension);
        switch (type) {
            case C.TYPE_SS:
                return new SsMediaSource(uri, buildDataSourceFactory(false),
                        new DefaultSsChunkSource.Factory(mediaDataSourceFactory), mainHandler, null);
            case C.TYPE_DASH:
                return new DashMediaSource(uri, buildDataSourceFactory(false),
                        new DefaultDashChunkSource.Factory(mediaDataSourceFactory), mainHandler, null);
            case C.TYPE_HLS:
                return new HlsMediaSource(uri, mediaDataSourceFactory, mainHandler, null);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource(uri, mediaDataSourceFactory, new DefaultExtractorsFactory(),
                        mainHandler, null);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    public DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(mActivity, bandwidthMeter,
                buildHttpDataSourceFactory(bandwidthMeter));
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(Util.getUserAgent(mActivity, "ExoPlayerDemo"), bandwidthMeter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (player != null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLanscapeMode() && !isTabletMode()) makeVideoFullScreen(mActivity);
        initView(rootView);
    }
}

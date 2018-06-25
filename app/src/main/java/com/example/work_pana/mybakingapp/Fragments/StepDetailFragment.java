package com.example.work_pana.mybakingapp.Fragments;

import android.content.Context;

import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.work_pana.mybakingapp.Models.StepObject;
import com.example.work_pana.mybakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;

import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;

import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;

import com.google.android.exoplayer2.trackselection.TrackSelector;

import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;



import butterknife.BindView;
import butterknife.ButterKnife;


public class StepDetailFragment extends Fragment{

    private StepObject mStep;
    @BindView(R.id.step_detail_tv) TextView description;
    @BindView(R.id.no_step_video_tv) TextView noVideoTxt;
    @BindView(R.id.media_player)
    PlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;

    public StepDetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_step, container, false);
        ButterKnife.bind(this, rootView);

        if (savedInstanceState == null) {
            ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (!mStep.getVideoURL().equals("") && (networkInfo != null && networkInfo.isConnected())) {
                noVideoTxt.setVisibility(View.GONE);
                initializePlayer(Uri.parse(mStep.getVideoURL()));
            } else {
                mPlayerView.setVisibility(View.GONE);
                noVideoTxt.setVisibility(View.VISIBLE);
            }
        }

        if (savedInstanceState != null) {
            mStep = (StepObject) savedInstanceState.getSerializable("ser");
            if (!mStep.getVideoURL().equals("")) {
                noVideoTxt.setVisibility(View.GONE);
                initializePlayer(Uri.parse(mStep.getVideoURL()));
            } else {
                mPlayerView.setVisibility(View.GONE);
                noVideoTxt.setVisibility(View.VISIBLE);
            }
        }

        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/concertOne-regular.ttf");
        description.setText(mStep.getDescription());
        description.setTypeface(customFont);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), "Baking video");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (!mStep.getVideoURL().equals("") && mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("ser", mStep);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mStep != null)
            releasePlayer();
    }

    public void setStepsModel(StepObject stepsModel) {
        this.mStep = stepsModel;
    }
}
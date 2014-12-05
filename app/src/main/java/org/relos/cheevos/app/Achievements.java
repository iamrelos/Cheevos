package org.relos.cheevos.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import org.relos.cheevos.R;
import org.relos.cheevos.adapters.AchievementsAdapter;
import org.relos.cheevos.adapters.GameListAdapter;
import org.relos.cheevos.loaders.AchievementsLoader;
import org.relos.cheevos.loaders.GameListLoader;
import org.relos.cheevos.objects.Game;

import java.util.ArrayList;
import java.util.List;

public class Achievements extends ActionBarActivity implements LoaderManager.LoaderCallbacks<List<Game>> {
    private List<Game> mList;
    private SlidingPaneLayout mSlidingPane;
    private AchievementsAdapter mAdapter;
    private GridView mLvContent;
    private String mTitle;
    private String mUrl;
    private int mGameId;
    private boolean isAnAdmin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().getThemedContext();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        onGameDetails();

        if (getIntent().getExtras() != null) {
            mTitle = getIntent().getExtras().getString("title");
            mUrl = getIntent().getExtras().getString("url");
            mGameId = getIntent().getExtras().getInt("gameId");

            getSupportActionBar().setTitle(mTitle);

            mList = new ArrayList<Game>();

            mAdapter = new AchievementsAdapter(mList, this);

            mLvContent = (GridView) findViewById(R.id.lv_content);
            mLvContent.setAdapter(mAdapter);
            mLvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                }
            });

            if (isAnAdmin) {
                startLoader();
            }
        }
    }

    private void startLoader() {
        if (this.getSupportLoaderManager().getLoader(0) == null) {
            this.getSupportLoaderManager().initLoader(0, null, this);
        } else {
            this.getSupportLoaderManager().restartLoader(0, null, this);
        }
    }

    private void onGameDetails() {
        mSlidingPane = new SlidingPaneLayout(this);
        mSlidingPane = (SlidingPaneLayout) findViewById(R.id.slp_achievements);
        mSlidingPane.setParallaxDistance(30);
        mSlidingPane.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {

            @Override
            public void onPanelSlide(View view, float v) {
            }

            @Override
            public void onPanelOpened(View view) {
                // set actionbar title
                getSupportActionBar().setTitle(R.string.ab_game_details);
            }

            @Override
            public void onPanelClosed(View view) {
                // set actionbar title
                getSupportActionBar().setTitle(mTitle);
            }
        });
    }

    @Override
    public Loader<List<Game>> onCreateLoader(int i, Bundle bundle) {
        return new AchievementsLoader(this, mList, mUrl, mGameId);
    }

    @Override
    public void onLoadFinished(Loader<List<Game>> listLoader, List<Game> games) {
        mList = games;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Game>> listLoader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_achievements, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // option item click listener
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.menu_game_details:
                // show/close browse menu
                if (!mSlidingPane.isOpen()) {
                    mSlidingPane.openPane();
                } else {
                    mSlidingPane.closePane();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        // show/close browse menu
        if (!mSlidingPane.isOpen()) {
            super.onBackPressed();
            overridePendingTransition(R.anim.anim_null, R.anim.anim_slide_out_right);
        } else {
            mSlidingPane.closePane();
        }
    }
}
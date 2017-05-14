package com.yanko20.blocspot.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.yanko20.blocspot.BlocSpotApp;
import com.yanko20.blocspot.R;
import com.yanko20.blocspot.adapters.PoiFragmentPageAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String logtag = "MainActivity.class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(logtag, "MainActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleIntent(getIntent());
        Toolbar toolbar = (Toolbar) findViewById(R.id.blocspot_toolbar);
        setSupportActionBar(toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PoiFragmentPageAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        final MenuItem filterMenuItem = menu.findItem(R.id.filter);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                filterMenuItem.setVisible(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                filterMenuItem.setVisible(true);
                return true;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            Bundle bundle = new Bundle();
            bundle.putString(CategoryDialogFragment.MODE_KEY,
                    CategoryDialogFragment.FILTER_MODE);
            CategoryDialogFragment categoryDialogFragment = new CategoryDialogFragment();
            categoryDialogFragment.setArguments(bundle);
            categoryDialogFragment.show(getFragmentManager(), CategoryDialogFragment.DIALOG_TAG);
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent makeNotificationIntent(Context geofenceService, String msg) {
        Log.d(BlocSpotApp.TAG, "makeNotificationIntent msg: " + msg);
        return new Intent(geofenceService, MainActivity.class);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, "Search query: " + query, Toast.LENGTH_SHORT).show();
        }
    }

}


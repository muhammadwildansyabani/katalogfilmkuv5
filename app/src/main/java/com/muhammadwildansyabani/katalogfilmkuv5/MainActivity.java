package com.muhammadwildansyabani.katalogfilmkuv5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.androidnetworking.AndroidNetworking;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.muhammadwildansyabani.katalogfilmkuv5.favorite.FavoritePagerAdapter;
import com.muhammadwildansyabani.katalogfilmkuv5.main.movie.MoviesFragment;
import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.TvShowFragment;
import com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model.MainPagerAdapter;
import com.muhammadwildansyabani.katalogfilmkuv5.reminder.ReminderSettingActivity;
import com.muhammadwildansyabani.katalogfilmkuv5.repository.AppDatabase;
import com.muhammadwildansyabani.katalogfilmkuv5.utilities.Constant;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static AppDatabase database;
    private MoviesFragment getMovieFragment;
    private TvShowFragment getTvShowFragment;

    private final long delayMilliSeconds = 800;
    private long lastEditText = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "movies").build();

        final TabLayout mainTabLayout = findViewById(R.id.main_tab_layout);
        final ViewPager mainViewPager = findViewById(R.id.main_view_pager);
        final BottomNavigationView bottomNavigationView =
                findViewById(R.id.main_bottom_navigation_view);


        final MainPagerAdapter pagerAdapter =
                new MainPagerAdapter(getSupportFragmentManager(), mainTabLayout);

        getMovieFragment = new MoviesFragment();
        getTvShowFragment = new TvShowFragment();
        pagerAdapter.addFragment(getMovieFragment, getResources().getString(R.string.tab_title_movie));
        pagerAdapter.addFragment(getTvShowFragment, getResources().getString(R.string.tab_title_tv_show));

        mainViewPager.setAdapter(pagerAdapter);
        mainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        mainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mainTabLayout));
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.action_favorite){
                            mainViewPager.setAdapter(new FavoritePagerAdapter(getSupportFragmentManager(),2));
                            setTitle(R.string.favorite_title);
                        }else {
                            mainViewPager.setAdapter(pagerAdapter);
                            setTitle(R.string.app_name);
                        }
                        return false;
                    }
                });
        AndroidNetworking.initialize(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem searchViewItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_hint_text));
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.language_setting){
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }else if(item.getItemId() == R.id.alarm_setting){
            Intent intent = new Intent(this, ReminderSettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public static AppDatabase getDatabase() {
        return database;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String keyword) {
        if(keyword.isEmpty()){
            showFullFilmList();
        }else
            searchFilmBy(keyword);
        return true;
    }

    private void searchFilmBy(String keyword){
        final String urlSearchMovie = Constant.getUrlOf(
                Constant.URL_TYPE_SEARCH,
                Constant.URL_MOVIES,
                keyword,
                this
        );
        final String urlSearchTv = Constant.getUrlOf(
                Constant.URL_TYPE_SEARCH,
                Constant.URL_TV,
                keyword,
                this
        );
        lastEditText = System.currentTimeMillis();
        Runnable userFinishTypingChecker = new Runnable() {
            public void run() {
                if (userStopTyping()) {
                    getMovieFragment.getMoviesViewModel().setMovie(getMovieFragment, urlSearchMovie);
                    getTvShowFragment.getTvShowViewModel().setTvShow(getTvShowFragment, urlSearchTv);
                }
            }
        };
        Handler handler = new Handler();
        handler.removeCallbacks(userFinishTypingChecker);
        handler.postDelayed(userFinishTypingChecker, delayMilliSeconds);
    }

    private void showFullFilmList(){
        getMovieFragment.getPresenter().prepareData(getMovieFragment.getMoviesViewModel());
        getTvShowFragment.getPresenter().prepareData(getTvShowFragment.getTvShowViewModel());
    }

    private boolean userStopTyping(){
        return System.currentTimeMillis() > (lastEditText + delayMilliSeconds - 100);
    }
}

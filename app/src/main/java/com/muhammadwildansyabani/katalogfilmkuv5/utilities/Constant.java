package com.muhammadwildansyabani.katalogfilmkuv5.utilities;

import android.content.Context;

import androidx.annotation.NonNull;

import com.muhammadwildansyabani.katalogfilmkuv5.R;

public class Constant {
    private static final String API_KEY = "f9679f846092e11e620a0e18c74f2b52";
    public static final int SPLASH_SCREEN_DURATION = 2000;
    public static final String IMAGE_SIZE_W500 = "w500";
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/";
    public static final String LOCAL_IMAGE_FILE_PATH = "imageDir";
    public static final String PREFERENCE_NAME = "myPreferences";

    public static final String URL_TYPE_SEARCH = "search";
    public static final String URL_TYPE_DISCOVER = "discover";
    public static final String URL_TYPE_DETAIL = "detail";
    public static final String URL_TYPE_NEW_RELEASE = "release";
    public static final String URL_MOVIES = "movie";
    public static final String URL_TV = "tv";

    public static final String save_daily_reminder_value = "daily reminder value";
    public static final String save_release_reminder_value = "release reminder value";

    public static final String KEY_TOTAL_EPISODE = "number_of_episodes";
    public static final String MOVIES_KEY_RUNTIME = "runtime";
    public static final String TV_KEY_RUNTIME = "episode_run_time";

    public static final String GET_FAVORITE_MOVIES = "get favorite movie list";
    public static final String INSERT_ALL_MOVIES = "insert movie";
    public static final String DELETE_MOVIE = "Delete movie";
    public static final String FIND_MOVIE = "find movie by id";

    public static final String GET_FAVORITE_TV_SHOW = "get favorite tv show list";
    public static final String INSERT_ALL_TV_SHOW = "insert tv show";
    public static final String DELETE_TV_SHOW = "Delete tv show";
    public static final String FIND_TV_SHOW = "find tv show by id";

    public static final String INSERT = "insert";
    public static final String SUCCESS_INSERT = "success insert";
    public static final String FAILED_INSERT = "failed insert";
    public static final String SUCCESS_DELETE = "success delete";
    public static final String FAILED_DELETE = "failed delete";

    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_RUNTIME = "runtime";
    public static final String COLUMN_SCORE = "score";

    @NonNull
    public static String getUrlOf(@NonNull String type, String category , String keyword, Context context){
        switch (type){
            case URL_TYPE_DISCOVER :
                return "https://api.themoviedb.org/3/discover/"+ category
                        + "?language=" + context.getResources().getString(R.string.default_language)
                        + "&api_key=" + API_KEY;
            case URL_TYPE_SEARCH :
                return "https://api.themoviedb.org/3/search/"+ category
                        + "?api_key=" + API_KEY
                        + "&language=" + context.getResources().getString(R.string.default_language)
                        + "&query=" + keyword;
            case URL_TYPE_NEW_RELEASE:
                return "https://api.themoviedb.org/3/discover/movie"
                        + "?api_key=" + API_KEY
                        + "&primary_release_date.gte=" + keyword
                        + "&primary_release_date.lte=" + keyword;
            default:
                return "https://api.themoviedb.org/3/"+ category + "/" + keyword +
                        "?language=" + context.getResources().getString(R.string.default_language) +
                        "&api_key=" + Constant.API_KEY;
        }
    }
}

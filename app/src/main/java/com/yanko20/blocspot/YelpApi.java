package com.yanko20.blocspot;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

// TODO: 5/14/2017
/*
Start app
    BlockSpotApp class
        setupYelp() - getYelpApi
            start in background with Async
            notify listeners when Finished
    ManiActivity class
        onFinish() - listener
            search icon visible = true - if icon available
        onCreateOptionsMenu()
            listen to search text input and return results for every character
    Create new fragment to display search results in list dynamically
 */

public class YelpApi {
    private static final String logTag = YelpApi.class.getSimpleName();
    private static final String clientId = "F8WWmkqzwz9BiKtu89gnVg";
    private static final String clientSecret = "SBgKN1iQHjzmyftXj7LnluX9KEHhncPr3dwIIyckPjbBGwAU9AGNXLh1TwOpAuiz";
    private YelpFusionApi yelpFusionApi;

    public YelpApi() {
        final YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                Log.d(logTag, "doInBackground");



                try {
                    yelpFusionApi = apiFactory.createAPI(clientId, clientSecret);
                    Map<String, String> params = new HashMap<>();
                    params.put("term", "indian food");
                    params.put("location", "New York");
                    Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(params);
                    Response<SearchResponse> response = call.execute();
                    ArrayList<Business> businesses = response.body().getBusinesses();
                    for (Business business : businesses) {
                        Log.d(logTag, "Business: " + business.getName());
                    }

                } catch (IOException e) {
                    Toast.makeText(BlocSpotApp.getAppContext(), "Yelp API Error", Toast.LENGTH_LONG);
                }
                return null;
            }
        }.execute();
    }

}

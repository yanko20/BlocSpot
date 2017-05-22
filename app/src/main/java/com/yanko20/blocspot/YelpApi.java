package com.yanko20.blocspot;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;// TODO: 5/14/2017
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
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
// format: x-www-form-urlencoded
//{
//        "grant_type": "client_credentials",
//        "client_id": "F8WWmkqzwz9BiKtu89gnVg",
//        "client_secret": "SBgKN1iQHjzmyftXj7LnluX9KEHhncPr3dwIIyckPjbBGwAU9AGNXLh1TwOpAuiz"
//}
// todo https://developer.android.com/training/volley/simple.html#simple


public class YelpApi {
    private static final String logTag = YelpApi.class.getSimpleName() + ".class";
    private static final String clientId = "F8WWmkqzwz9BiKtu89gnVg";
    private static final String clientSecret = "SBgKN1iQHjzmyftXj7LnluX9KEHhncPr3dwIIyckPjbBGwAU9AGNXLh1TwOpAuiz";
    private String url = "https://api.yelp.com/oauth2/token";


    public YelpApi() {
        JSONObject jsonReqeust = new JSONObject();
        try {
            jsonReqeust.put("grant_type", "client_credentials");
            jsonReqeust.put("clientId", clientId);
            jsonReqeust.put("clientSecret", clientSecret);
        } catch (JSONException e) {
            Log.e(logTag, e.getMessage());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonReqeust, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(logTag, response.getString("access_token"));
                } catch (JSONException e) {
                    Log.e(logTag, e.getMessage());
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(logTag, error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(BlocSpotApp.getAppContext());
        requestQueue.add(jsonObjectRequest);

    }

}

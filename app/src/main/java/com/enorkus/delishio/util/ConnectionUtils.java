package com.enorkus.delishio.util;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionUtils {

    private static final String PIXABAY_BASE_URL = "https://pixabay.com/api/";
    private static final String API_KEY = "";
    private static final String PARAM_KEY = "key";
    private static final String PARAM_QUERY = "q";

    public static URL buildImageSearchURL(String searchParam) {
        Uri uri = Uri.parse(PIXABAY_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_KEY, API_KEY)
                .appendQueryParameter(PARAM_QUERY, searchParam)
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

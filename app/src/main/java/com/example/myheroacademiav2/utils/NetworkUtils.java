package com.example.myheroacademiav2.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    final static String BASE_URL = "https://akabab.github.io/superhero-api/api";
    final static String ALL_HEROES_PARAM = "all";
    final static String SINGLE_HERO_PARAM = "id";
    final static String URL_END = ".json";

    public static URL generateUrl() {
        Uri buildUri = Uri.withAppendedPath(Uri.parse(BASE_URL), ALL_HEROES_PARAM + URL_END)
                .buildUpon().build();

        return UriToURL(buildUri);
    }

    public static URL generateUrl(String heroID) {
        Uri buildUri = Uri
                .withAppendedPath(Uri.parse(BASE_URL), SINGLE_HERO_PARAM)
                .buildUpon().build();

        Uri buildUri2 = Uri.withAppendedPath(buildUri, heroID + URL_END);

        return UriToURL(buildUri2);
    }

    private static URL UriToURL(Uri uri) {
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream is = urlConnection.getInputStream();

        Scanner scan = new Scanner(is);
        scan.useDelimiter("\\A");
        try {
            if (scan.hasNext()) {
                return scan.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
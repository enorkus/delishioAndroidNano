package com.enorkus.delishio.asynctask;

import android.os.AsyncTask;

import com.enorkus.delishio.entity.Picture;
import com.enorkus.delishio.util.AsyncTaskResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ImageSearchTask extends AsyncTask<URL, Void, String> {

    private static final String JSON_STREAM_DELIMITER = "\\A";

    private AsyncTaskResponse taskResponse;

    public ImageSearchTask(AsyncTaskResponse taskResponse) {
        this.taskResponse = taskResponse;
    }

    @Override
    protected String doInBackground(URL... urls) {
        try {
            return getResponse(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getResponse(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream stream = connection.getInputStream();
            Scanner scan = new Scanner(stream);
            scan.useDelimiter(JSON_STREAM_DELIMITER);
            if (scan.hasNext()) {
                return scan.next();
            }
            return null;
        } finally {
            connection.disconnect();
        }
    }

    @Override
    protected void onPostExecute(String response) {
        JSONObject results = null;
        try {
            results = new JSONObject(response);
            JSONArray resultsArray = results.getJSONArray("hits");
            Picture[] picturesArray = new Gson().fromJson(resultsArray.toString(), Picture[].class);
            List<Picture> pictures = new ArrayList<>(Arrays.asList(picturesArray));
            taskResponse.getAsyncResponseOnFinish(pictures);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

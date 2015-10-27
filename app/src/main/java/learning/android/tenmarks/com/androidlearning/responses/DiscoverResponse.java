package learning.android.tenmarks.com.androidlearning.responses;

import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import learning.android.tenmarks.com.androidlearning.GsonRequest;

/**
 * Created by Talha on 10/23/15.
 */
public class DiscoverResponse {

    public int page;
    public List<DiscoverResult> results;
    public int total_pages;
    public int total_results;

    public static class DiscoverResult {
        public boolean adult;
        public String backdrop_path;
        public long id;
        public String original_language;
        public String original_title;
        public String overview;
        public String release_date;
        public String poster_path;
        public float popularity;
        public String title;
        public boolean video;
        public float vote_average;
        public long vote_count;
    }


    public static Request<DiscoverResponse> getDiscoverRequest(final Response.Listener<DiscoverResponse> listener,
                                                        Response.ErrorListener errorListener) {
        // you can get the value for api_key by creating an account on themoviedb.org
        // but for now you are okay to use the one I have placed here
        String url = Uri.parse("https://api.themoviedb.org/3/discover/movie?api_key=c7a35451a3f598ff42521cb07938f36a")
                .buildUpon()
                .appendQueryParameter("append_to_response", "images")
                .appendQueryParameter("include_image_language", "en")
                .appendQueryParameter("page","1").toString();

        Log.d("DiscoverResponse", "Url is: " + url);

        Request<DiscoverResponse> requestt = new GsonRequest<>(Request.Method.GET, url, DiscoverResponse.class, null, listener, errorListener);
        return requestt;


        /*Request<DiscoverResponse> request = new Request<DiscoverResponse>(Request.Method.GET, url, errorListener) {
            *//*@Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("api_key", "c7a35451a3f598ff42521cb07938f36a");
                return params;
            }*//*

            @Override
            protected Response<DiscoverResponse> parseNetworkResponse(NetworkResponse response) {
                // String responseHeaders = (response.headers == null) ? null : response.headers.get("Content-Encoding");
                StringBuilder output = new StringBuilder();
                GZIPInputStream gStream = null;
                InputStream inStream = null;
                InputStreamReader inputStreamReader = null;
                BufferedReader bufferedReader = null;
                try {
                    // inStream = new GZIPInputStream(new ByteArrayInputStream(response.data));
                    inStream = new InputStream() {

                    }
                    inputStreamReader = new InputStreamReader(gStream);
                    bufferedReader = new BufferedReader(inputStreamReader);
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        output.append(line);
                    }

                    Gson gson = new Gson();
                    return Response.success(gson.fromJson(output.toString(), DiscoverResponse.class),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (IOException ioExp) {
                    ioExp.printStackTrace();
                } finally {
                    try {
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }

                        if (inputStreamReader != null) {
                            inputStreamReader.close();
                        }

                        if (gStream != null) {
                            gStream.close();
                        }
                    } catch (IOException exp) {
                        exp.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void deliverResponse(DiscoverResponse response) {
                listener.onResponse(response);
            }
        };
        request.setShouldCache(false);
        return request;*/
    }
}

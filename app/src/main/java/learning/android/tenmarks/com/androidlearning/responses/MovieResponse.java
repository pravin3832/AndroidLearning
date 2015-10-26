package learning.android.tenmarks.com.androidlearning.responses;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;

import learning.android.tenmarks.com.androidlearning.GsonRequest;

/**
 * Created by Talha on 10/26/15.
 */
public class MovieResponse {


        public boolean adult;
        public String backdrop_path;
        /*"belongs_to_collection": {
        "id": 328,
                "name": "Jurassic Park Collection",
                "poster_path": "/jcUXVtJ6s0NG0EaxllQCAUtqdr0.jpg",
                "backdrop_path": "/pJjIH9QN0OkHFV9eue6XfRVnPkr.jpg"
        }*/
        public long budget;
            /*"genres": [
            {
                "id": 28,
                "name": "Action"
            },
            {
                "id": 12,
                "name": "Adventure"
            },
            {
                "id": 878,
                "name": "Science Fiction"
            },
            {
                "id": 53,
                "name": "Thriller"
            }
            ],*/
        public String homepage;
        public long id;
        public String imdb_id;
        public String original_language;
        public String original_title;
        public String overview;
        public float popularity;
        public String poster_path;
        /*"production_companies": [
            {
                "name": "Universal Studios",
                "id": 13
            },
            {
                "name": "Amblin Entertainment",
                "id": 56
            },
            {
                "name": "Legendary Pictures",
                "id": 923
            }
            ],
        "production_countries": [
            {
                "iso_3166_1": "US",
                "name": "United States of America"
            }
            ],*/
        public String release_date;
        public long revenue;
        public int runtime;
            /*"spoken_languages": [
            {
                "iso_639_1": "en",
                "name": "English"
            }
            ],*/
        public String status;
        public String tagline;
        public String title;
        public boolean video;
        public float vote_average;
        public int vote_count;

        public static Request<MovieResponse> getMovieDetailRequest(long id,
                                                                   final Response.Listener<MovieResponse> listener,
                                                                   Response.ErrorListener errorListener) {
            //api.themoviedb.org/3/movie/135397?api_key=c7a35451a3f598ff42521cb07938f36a
            String url = Uri.parse("https://api.themoviedb.org/3/movie")
                    .buildUpon()
                    .appendPath(Long.toString(id))
                    .appendQueryParameter("api_key", "c7a35451a3f598ff42521cb07938f36a")
                    .toString();
            Log.d("MovieResponse", "Url is: " + url);
            Request<MovieResponse> request = new GsonRequest<>(Request.Method.GET, url, MovieResponse.class, null, listener, errorListener);
            return request;
        }

}

package maniteja.com.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maniteja on 9/4/2016.
 */
public class MovieListFragment extends Fragment {
    public static final String LOG_TAG=MovieListFragment.class.getSimpleName();
    MovieAdapter movieAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Inflate layout
        View rootView=inflater.inflate(R.layout.fargment_movie_list,container,false);
        final List<Movie> movieList =new FetchMoviesTask().getMovieList();
        //Intialize adapter
        movieAdapter=new MovieAdapter(getContext(),R.layout.image_view,movieList);
        sortMovieListByPopularity();
        GridView gridView=(GridView)rootView.findViewById(R.id.gridView_movies);

        //Listener on GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("EXTRA_MOVIE",movieList.get(position));
                startActivity(intent);

            }
        });
        gridView.setAdapter(movieAdapter);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_movie_list_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.sort_by_popular:
                sortMovieListByPopularity();
                return true;
            case R.id.sort_by_topRated:
                sortMovieListByTopRating();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void sortMovieListByPopularity()
    {
        String baseUrl="http://api.themoviedb.org/3/movie/popular";
        Uri popularMoviesUri=Uri.parse(baseUrl).buildUpon().appendQueryParameter("api_key",getString(R.string.api_key)).build();
        String popularUrl =popularMoviesUri.toString();
        Log.d(LOG_TAG,popularUrl);
        new FetchMoviesTask().execute(popularUrl);

    }
    public void sortMovieListByTopRating()
    {
        String baseUrl="http://api.themoviedb.org/3/movie/top_rated";
        Uri TopRatedMoviesUri=Uri.parse(baseUrl).buildUpon().appendQueryParameter("api_key",getString(R.string.api_key)).build();
        String topRatedUrl=TopRatedMoviesUri.toString();
        new FetchMoviesTask().execute(topRatedUrl);

    }

    //TODO: insert internet connectivity code in asynctask later
    public boolean checkInternetConnectivity()
    {
        ConnectivityManager manager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
        if(networkInfo!=null&networkInfo.isConnected())
        {
            return true;
        }
        return false;
    }

    private class FetchMoviesTask extends AsyncTask<String,Void,String>
    {
        private List<Movie> movieList=new ArrayList<Movie>();

        @Override
        protected String doInBackground(String... params) {
            String urlToFetch=params[0];
            try {
                return fetchMovieList(urlToFetch);
            }catch (IOException e){
                return "unable to fetch movies.Invalid Url.";

            }

        }

        public List<Movie> getMovieList()
        {
            return movieList;
        }

        @Override
        protected void onPostExecute(String moviesJson) {
            super.onPostExecute(moviesJson);
            try {
                movieList=getMovieObjects(moviesJson);
                movieAdapter.clear();
                movieAdapter.addAll(movieList);

            }catch (JSONException e)
            {
                e.printStackTrace();
            }


        }

        private List<Movie> getMovieObjects(String string)throws JSONException
        {

            List<Movie> movieList=new ArrayList<Movie>();
            JSONObject popularMoviesObject=new JSONObject(string);
            JSONArray resultsArray=popularMoviesObject.getJSONArray("results");
            for (int i=0;i<resultsArray.length();i++)
            {
                JSONObject object=resultsArray.getJSONObject(i);
                String posterPath=object.getString("poster_path");
                String title=object.getString("original_title");
                String overview =object.getString("overview");
                String releaseDate=object.getString("release_date");
                Double rating=object.getDouble("vote_average");
                Movie movie=new Movie(posterPath,title,releaseDate,overview,rating);
                movieList.add(movie);


            }
            return movieList;
        }

        private String  fetchMovieList(String url) throws IOException
        {
            InputStream inputStream=null;
            URL url1=new URL(url);

            try {
                HttpURLConnection connection=(HttpURLConnection)url1.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(15000);
                connection.connect();
                int response=connection.getResponseCode();
                inputStream=connection.getInputStream();
                String streamAsString = streamToString(inputStream);
                return streamAsString;


            }finally {
                if(inputStream!=null)
                {
                    inputStream.close();
                }
            }

        }

        private String streamToString(InputStream inputStream)throws IOException,UnsupportedEncodingException{
            BufferedReader reader=null;
            StringBuffer buffer=new StringBuffer();
            reader=new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line=reader.readLine())!=null)
            {
                buffer.append(line+"/n");

            }
            Log.d(LOG_TAG,new String(buffer));
            return new String(buffer);

        }
    }
}

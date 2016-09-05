package maniteja.com.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by maniteja on 9/4/2016.
 */
public class MovieListFragment extends Fragment {
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
        new FetchMoviesTask().execute(popularMoviesUri);

    }
    public void sortMovieListByTopRating()
    {
        String baseUrl="http://api.themoviedb.org/3/movie/top_rated";
        Uri TopRatedMoviesUri=Uri.parse(baseUrl).buildUpon().appendQueryParameter("api_key",getString(R.string.api_key)).build();
        new FetchMoviesTask().execute(TopRatedMoviesUri);

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

    private class FetchMoviesTask extends AsyncTask<Uri,Void,String>
    {
        @Override
        protected String doInBackground(Uri... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}

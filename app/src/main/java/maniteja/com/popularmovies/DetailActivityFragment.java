package maniteja.com.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private String baseUrl="http://image.tmdb.org/t/p";
    private String imageQuality="w342";

    private TextView titleView;
    private TextView releaseDateView;
    private TextView ratingView;
    private TextView overviewView;
    private ImageView posterView;


    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_detail, container, false);
        //Get views
        titleView=(TextView)rootView.findViewById(R.id.movieTitle);
        releaseDateView=(TextView)rootView.findViewById(R.id.releseDate);
        ratingView=(TextView)rootView.findViewById(R.id.rating);
        overviewView=(TextView)rootView.findViewById(R.id.overview);
        posterView=(ImageView)rootView.findViewById(R.id.thumbnail);


        //Get data from intent
        Intent movieIntent=getActivity().getIntent();
        Bundle bundle=movieIntent.getExtras();
        Movie movie=bundle.getParcelable("EXTRA_MOVIE");

        //Bind data to views
        titleView.setText(movie.getTitle());
        releaseDateView.setText(movie.getReleaseDate());
        ratingView.setText(movie.getRating().toString());
        overviewView.setText(movie.getOverview());

        //load image
        Uri pathUri=Uri.parse(baseUrl).buildUpon().appendPath(imageQuality).build();
        String path=pathUri.toString();
        String imagePathUrl=path.concat(movie.getPosterPath());
        Picasso.with(getContext()).load(imagePathUrl).into(posterView);



        return rootView;
    }








}

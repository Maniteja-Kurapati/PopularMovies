package maniteja.com.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by maniteja on 9/4/2016.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    public static final String LOG_TAG=MovieAdapter.class.getSimpleName();

    private List<Movie> mMovieList;
    private Context mContext;
    private String baseUrl="http://image.tmdb.org/t/p";
    private String imageQuality="w500";


    public MovieAdapter(Context context, int resource, List<Movie> movieList) {
        super(context, resource, movieList);
        mMovieList=movieList;
        mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.image_view,parent,false);
        }



        Uri pathUri=Uri.parse(baseUrl).buildUpon().appendPath(imageQuality).build();
        String path=pathUri.toString();
        String imagePathUrl=path.concat(mMovieList.get(position).getPosterPath().toString());

        Log.d(LOG_TAG,imagePathUrl);
        ImageView imageView=(ImageView)convertView.findViewById(R.id.imageView_in_layout);
        Picasso.with(mContext).load(imagePathUrl).into(imageView);
        return imageView;
    }
}

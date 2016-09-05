package maniteja.com.popularmovies;

/**
 * Created by maniteja on 9/4/2016.
 */
public class Movie {

    private String posterPath;
    private String title;
    private String releaseDate;
    private String overview;
    private float rating;

    public Movie(String posterPath, String title, String releaseDate, String overview, float rating) {
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.rating = rating;
    }



    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


}

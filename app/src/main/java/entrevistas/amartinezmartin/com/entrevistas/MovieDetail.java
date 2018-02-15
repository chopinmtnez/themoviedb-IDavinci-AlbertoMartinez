package entrevistas.amartinezmartin.com.entrevistas;

/**
 * Created by alberto on 15/2/18.
 */

public class MovieDetail {
    String name;
    String description;
    String genres;
    String homepage;
    int id_;
    String imageurl;

    public MovieDetail(String name, String description, int id_, String imageurl, String homepage,String genres) {
        this.name = name;
        this.description = description;
        this.homepage = homepage;
        this.genres = genres;
        this.id_ = id_;
        this.imageurl=imageurl;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getId_(){
        return id_;
    }

    public String getImageUrl(){
        return imageurl;
    }

    public String getGenres(){
        return genres;
    }

    public String getHomepage(){
        return homepage;
    }
}

package entrevistas.amartinezmartin.com.entrevistas;

/**
 * Created by alberto on 14/2/18.
 */

public class Movie {
    String name;
    String description;
    int id_;
    String imageurl;

    public Movie(String name, String description, int id_, String imageurl) {
        this.name = name;
        this.description = description;
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
}

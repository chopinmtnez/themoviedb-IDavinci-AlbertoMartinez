package entrevistas.amartinezmartin.com.entrevistas;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by alberto on 14/2/18.
 */

class Utils {
    static Movie parser(JSONObject results) throws JSONException {
        JSONArray array = new JSONArray();
        array.put(results);
        return parser(array).get(0);
    }

    static ArrayList<Movie> parser(JSONArray results) throws JSONException {
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i=0; i < results.length(); i++) {
            String title = "";
            String description = "";
            String imgurl = "";
            int id = 0;
            Movie movie;
            JSONObject result = results.getJSONObject(i);
            if(result.has("title")) title = result.getString("title");
            if(result.has("overview")) description = result.getString("overview");
            if(result.has("backdrop_path")) imgurl = result.getString("backdrop_path");
            if(result.has("id")) id = result.getInt("id");
            if(id!=0){
                movie = new Movie(title,description, id, imgurl);
                movies.add(movie);
            }
        }
        return movies;
    }

    static MovieDetail parserDetail(JSONObject result) throws JSONException {
        String title = "";
        String description = "";
        String imgurl = "";
        String homepage = "";
        StringBuilder genres = new StringBuilder();
        int id = 0;
        Movie movie;
        if(result.has("title")) title = result.getString("title");
        if(result.has("overview")) description = result.getString("overview");
        if(result.has("poster_path")) imgurl = result.getString("poster_path");
        if(result.has("homepage")) homepage = result.getString("homepage");
        if(result.has("id")) id = result.getInt("id");
        if(result.has("genres")){
            genres = new StringBuilder("Genres: ");
            for (int i=0; i < result.getJSONArray("genres").length(); i++) {
                JSONObject genero = (JSONObject) result.getJSONArray("genres").get(i);
                genres.append(genero.getString("name"));
                if(i<result.getJSONArray("genres").length()-1) genres.append(", ");
            }
        }
        return new MovieDetail(title,description,id,imgurl,homepage,genres.toString());
    }

    public static boolean addMovie(Context context, String id) {
        String[] movies = readMovies(context);
        if(!Arrays.asList(movies).contains(id)) {
            StringBuilder aguardar = new StringBuilder();
            for (String movie : movies) {
                aguardar.append(movie).append(",");
            }
            aguardar.append(id).append(",");
            try {
                FileOutputStream fileout = context.openFileOutput(context.getString(R.string.filemovies), MODE_PRIVATE);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                outputWriter.write(aguardar.toString());
                outputWriter.close();
                return true;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean removeMovie(Context context, String id) {
        String[] movies = readMovies(context);
        if(Arrays.asList(movies).contains(id)){
            StringBuilder aguardar = new StringBuilder();
            for (String movie : movies) {
                if(!Objects.equals(movie, id)) aguardar.append(movie).append(",");
            }
            try {
                FileOutputStream fileout = context.openFileOutput(context.getString(R.string.filemovies), MODE_PRIVATE);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                outputWriter.write(aguardar.toString());
                outputWriter.close();
                return true;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }


    public static String[] readMovies(Context context) {
        try {
            FileInputStream fileIn=context.openFileInput(context.getString(R.string.filemovies));
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[100];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();

            return s.split(",");

        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{};
        }
    }
}

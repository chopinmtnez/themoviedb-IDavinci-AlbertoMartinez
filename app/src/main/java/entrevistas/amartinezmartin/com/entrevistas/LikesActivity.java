package entrevistas.amartinezmartin.com.entrevistas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by alberto on 14/2/18.
 */
public class LikesActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    AlbertoAdapterLikes mAdapter;
    RequestQueue mRequestQueue;
    Context mContext;
    String[] movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Movies Saved");
        setSupportActionBar(toolbar);

        movies = Utils.readMovies(this);

        RequestQueue queue = Volley.newRequestQueue(this);

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        final ArrayList<Movie> moviestoshow = new ArrayList<>();

        for(int i=0; i<movies.length; i++) {
            final int finalI = i;
            if(movies[i].equals("")) continue;
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, getString(R.string.url_get_movie_detail).replace("{movie_id}", movies[i]) + getString(R.string.api_key), null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                moviestoshow.add(Utils.parser(response));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(finalI == movies.length-1) {
                                mAdapter = new AlbertoAdapterLikes(moviestoshow);
                                mRecyclerView.setAdapter(mAdapter);
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(mContext, getString(R.string.errormessage), Toast.LENGTH_LONG).show();
                        }
                    });
            queue.add(jsObjRequest);

        }

        mRecyclerView = (RecyclerView) findViewById(R.id.listaitems);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(new AlbertoAdapterLikes(new ArrayList<Movie>()));

    }
}
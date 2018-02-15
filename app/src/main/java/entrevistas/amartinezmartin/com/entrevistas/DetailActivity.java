package entrevistas.amartinezmartin.com.entrevistas;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;


/**
 * Created by alberto on 14/2/18.
 */
public class DetailActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    AlbertoAdapterDetail mAdapter;
    RequestQueue mRequestQueue;
    Context mContext;

    int id_movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detail");
        setSupportActionBar(toolbar);

        RequestQueue queue = Volley.newRequestQueue(this);

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        id_movie = getIntent().getIntExtra("movie_id",0);

        if(id_movie==0) finish();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, getString(R.string.url_get_movie_detail).replace("{movie_id}",String.valueOf(id_movie))+getString(R.string.api_key), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mAdapter = new AlbertoAdapterDetail(Utils.parserDetail(response));
                            mRecyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext,getString(R.string.errormessage),Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(jsObjRequest);

        mRecyclerView = (RecyclerView) findViewById(R.id.listaitems);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(new AlbertoAdapter(new ArrayList<Movie>()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_likes:
                Intent intent = new Intent(this,LikesActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_save:
                if(Utils.addMovie(this,String.valueOf(id_movie)))
                    Toast.makeText(this,getString(R.string.saved),Toast.LENGTH_LONG).show();
                else Toast.makeText(this,getString(R.string.alreadysaved),Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

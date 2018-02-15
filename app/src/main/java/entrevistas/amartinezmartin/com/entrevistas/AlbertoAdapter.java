package entrevistas.amartinezmartin.com.entrevistas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by alberto on 14/2/18.
 */

public class AlbertoAdapter extends RecyclerView.Adapter<AlbertoAdapter.AlbertoViewHolder> {
    private ArrayList<Movie> mDatasetMovies;
    public Context mContext;

    public static class AlbertoViewHolder extends RecyclerView.ViewHolder {
        public TextView mMovieName;
        public TextView mMovieDescription;
        public ImageView mMoviePhoto;
        public AlbertoViewHolder(View view) {
            super(view);
            mMovieName = view.findViewById(R.id.movie_name);
            mMovieDescription = view.findViewById(R.id.movie_description);
            mMoviePhoto = view.findViewById(R.id.movie_photo);
        }
    }

    public AlbertoAdapter(ArrayList<Movie> mMovies) {
        mDatasetMovies = mMovies;
    }

    @Override
    public AlbertoAdapter.AlbertoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_main, parent, false);
        return new AlbertoViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final AlbertoViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("movie_id", mDatasetMovies.get(holder.getAdapterPosition()).getId_());
                mContext.startActivity(intent);
            }
        });
        holder.mMovieName.setText(mDatasetMovies.get(position).getName());
        holder.mMovieDescription.setText(mDatasetMovies.get(position).getDescription());
        Picasso.with(mContext).load(mContext.getString(R.string.path_photos)+mDatasetMovies.get(position).getImageUrl()).into(holder.mMoviePhoto);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDatasetMovies.size();
    }
}
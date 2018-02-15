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

public class AlbertoAdapterDetail extends RecyclerView.Adapter<AlbertoAdapterDetail.AlbertoViewHolder> {
    private MovieDetail mMovieDetail;
    public Context mContext;

    public static class AlbertoViewHolder extends RecyclerView.ViewHolder {
        public TextView mMovieName;
        public TextView mMovieDescription;
        public TextView mGenres;
        public TextView mHomePage;
        public ImageView mMoviePhoto;

        public AlbertoViewHolder(View view) {
            super(view);
            mMovieName = view.findViewById(R.id.movie_name);
            mMovieDescription = view.findViewById(R.id.movie_description);
            mMoviePhoto = view.findViewById(R.id.movie_photo);
            mGenres = view.findViewById(R.id.movie_genres);
            mHomePage = view.findViewById(R.id.movie_homepage);
        }
    }

    public AlbertoAdapterDetail(MovieDetail movieDetail) {
        mMovieDetail = movieDetail;
    }

    @Override
    public AlbertoAdapterDetail.AlbertoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_detail, parent, false);
        return new AlbertoViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final AlbertoViewHolder holder, int position) {
        holder.mMovieName.setText(mMovieDetail.getName());
        holder.mMovieDescription.setText(mMovieDetail.getDescription());
        holder.mHomePage.setText(mMovieDetail.getHomepage());
        holder.mGenres.setText(mMovieDetail.getGenres());
        Picasso.with(mContext).load(mContext.getString(R.string.path_photos)+mMovieDetail.getImageUrl()).into(holder.mMoviePhoto);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(mMovieDetail.getId_()!=0) return 1;
        else return 0;
    }
}
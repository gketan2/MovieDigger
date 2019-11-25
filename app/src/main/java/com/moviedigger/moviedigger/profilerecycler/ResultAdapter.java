package com.moviedigger.moviedigger.profilerecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.moviedigger.moviedigger.R;
import com.moviedigger.moviedigger.tmdbcalls.MoviePoster;
import com.moviedigger.moviedigger.tmdbcalls.TmdbResponse;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultViewHolder> {

    private ArrayList<ResultData> dataList = new ArrayList<>();
    private Context mContext;

    MoviePoster moviePoster;

    private ArrayList<Float> userRatingList = new ArrayList<>();

    public ResultAdapter(ArrayList<ResultData> list, Context context){
        dataList.clear();
        dataList.addAll(list);
        mContext = context;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card,parent,false);
        ResultViewHolder viewHolder = new ResultViewHolder(v);

        for(int i=0;i<dataList.size();i++)
            getDataFromTmdb(i);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ResultViewHolder viewHolder, final int position){

        viewHolder.ratingBar.setVisibility(View.GONE);
        viewHolder.user_rating.setVisibility(View.VISIBLE);



        String url = dataList.get(position).getImageUrl();

        String rating = dataList.get(position).getImdbRating();
        String genres = dataList.get(position).getGenres();
        float userRating = dataList.get(position).getUserRating();

        viewHolder.movie_name.setText(dataList.get(position).getMovieName());
        viewHolder.user_rating.setText("Your Rating: "+userRating);
        if(rating != null && genres != null){
            viewHolder.rating.setText("Rating : " +rating);
            viewHolder.genres.setText("Genres: "+genres);
        }
        if (url != null) {
            Glide.with(mContext)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.movieImage);
        } else {
            Glide.clear(viewHolder.movieImage);
            viewHolder.movieImage.setImageDrawable(null);
        }


    }

    @Override
    public int getItemCount(){
        return dataList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }


    public void add(ArrayList<ResultData> dataList){
        if(moviePoster != null)
            moviePoster.cancel(true);
        this.dataList.clear();
        notifyDataSetChanged();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();

    }

    private void getDataFromTmdb(final int position){
            moviePoster = new MoviePoster(dataList.get(position).getMovieId(), new TmdbResponse() {
                @Override
                public void processFinish(ArrayList<String> output) {
                    if(output != null && output.size() !=0   && getItemCount() > position){
                        dataList.get(position).setImageUrl(output.get(0));
                        dataList.get(position).setImdbRating(output.get(1));
                        dataList.get(position).setGenres(output.get(2));
                        notifyDataSetChanged();
                    }

                }
            });
            moviePoster.execute();
    }

    public ArrayList<Float> getUserRatingList(){

        int size = getItemCount();
        userRatingList.clear();
        for(int i = 0; i < size ; i++){
            Float rate = dataList.get(i).getUserRating();
            userRatingList.add(rate);
        }
        return userRatingList;
    }


    public void clear(){
        if(moviePoster != null)
            moviePoster.cancel(true);
        dataList.clear();
        notifyDataSetChanged();
    }
}

package com.moviedigger.moviedigger.resultrecycler;

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
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultViewHolder> {

    private List<ResultData> dataList = new ArrayList<ResultData>();
    private Context mContext;

    MoviePoster moviePoster;

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


        String rating = dataList.get(position).getImdbRating();
        String url = dataList.get(position).getImageUrl();
        String genres = dataList.get(position).getGenres();

        viewHolder.movie_name.setText(dataList.get(position).getMovieName());
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

    public void clear(){
        if(moviePoster != null)
            moviePoster.cancel(true);
        dataList.clear();
        notifyDataSetChanged();
    }

}

package com.moviedigger.moviedigger.resultrecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.moviedigger.moviedigger.R;
import com.moviedigger.moviedigger.tmdbcalls.MoviePoster;
import com.moviedigger.moviedigger.tmdbcalls.TmdbResponse;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultViewHolder> {

    private List<ResultData> dataList ;
    private Context mContext;

    public ResultAdapter(List<ResultData> list, Context context){
        dataList = list;
        mContext = context;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        ///////////////////GET VIEW
        //int w = parent.getWidth();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card,parent,false);
        ResultViewHolder viewHolder = new ResultViewHolder(v);

//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w/4,w/4);
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
//        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
//        viewHolder.movieImage.setLayoutParams(layoutParams);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ResultViewHolder viewHolder, final int position){


        viewHolder.movie_name.setText(dataList.get(position).movieName);

        MoviePoster moviePoster = new MoviePoster(dataList.get(position).movieId, new TmdbResponse() {
            @Override
            public void processFinish(List<String> output) {

                if(output != null && output.size() !=0 ){
                    dataList.get(position).setImageUrl(output.get(0));
                    dataList.get(position).setImdbRating(output.get(1));

                    viewHolder.rating.setText(dataList.get(position).imdbRating);
                    Toast.makeText(mContext,dataList.get(position).getImageUrl(),Toast.LENGTH_SHORT).show();
                    System.out.println(dataList.get(position).getImageUrl());

                    Glide.with(mContext)
                            .load(dataList.get(position).imageUrl)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(viewHolder.movieImage);
                }

            }
        });

        moviePoster.execute();

    }

    @Override
    public int getItemCount(){
        return dataList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }



    public void add(List<ResultData> dataList){
        this.dataList.clear();
        this.dataList = dataList;
        notifyDataSetChanged();
    }

}

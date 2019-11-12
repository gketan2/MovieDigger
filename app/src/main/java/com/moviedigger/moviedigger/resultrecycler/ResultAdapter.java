package com.moviedigger.moviedigger.resultrecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.moviedigger.moviedigger.R;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultViewHolder> {

    List<ResultData> dataList = new ArrayList<ResultData>();
    Context mContext;

    public ResultAdapter(List<ResultData> list, Context context){
        dataList = list;
        mContext = context;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item,parent,false);
        ResultViewHolder viewHolder = new ResultViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ResultViewHolder viewHolder, final int position){

//        viewHolder.examName
//                .setText(list.get(position).name);
//        viewHolder.examDate
//                .setText(list.get(position).date);
//        viewHolder.examMessage
//                .setText(list.get(position).message);
    }

    @Override
    public int getItemCount(){
        return dataList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }
}

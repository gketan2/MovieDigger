package com.moviedigger.moviedigger.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moviedigger.moviedigger.R;
import com.pchmn.materialchips.ChipsInput;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment{

    private ChipsInput chipsInput;
    private List<SearchChip> contactList = new ArrayList<>();

    public SearchFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(String x : getResources().getStringArray(R.array.genres))
            contactList.add(new SearchChip(x));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  v =  inflater.inflate(R.layout.fragment_search, container, false);

        chipsInput = v.findViewById(R.id.chips_input);

        chipsInput.setFilterableList(contactList);

        //List<SearchChip> contactsSelected = (List<SearchChip>) chipsInput.getSelectedChipList();

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

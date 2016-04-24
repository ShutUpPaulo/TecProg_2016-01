package com.mathheals.euvou.controller.showPlaceRanking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mathheals.euvou.R;

public class ShowPlaceRanking extends android.support.v4.app.Fragment{

    public ShowPlaceRanking(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        return inflater.inflate(R.layout.fragment_show_place_ranking, container, false);
    }

}

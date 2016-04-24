package com.mathheals.euvou.controller.showPlaceRanking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mathheals.euvou.R;
import java.util.List;
import model.Place;

public class PlaceAdapter extends ArrayAdapter<Place>{

    public PlaceAdapter(Context context, List<Place> places){
        super(context, 0,places);
    }

    private static class ViewHolder{
        TextView placeName;
        TextView placeEvaluation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Place place = getItem(position);
        ViewHolder viewHolder;

        if(convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        }
        else{
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.fragment_show_place_rank, parent, false);

            viewHolder.placeName = (TextView) convertView.findViewById(R.id.placeName);
            viewHolder.placeEvaluation = (TextView) convertView.findViewById(R.id.placeEvaluation);

            convertView.setTag(viewHolder);
        }

        String placeNameText = resumePlaceName(place.getName());

        viewHolder.placeName.setText(placeNameText);
        viewHolder.placeEvaluation.setText(place.getEvaluate().toString());

        return convertView;
    }

    private String resumePlaceName(String placeName){
        String placeNameText;

        if(placeName.length() <= 40){
            placeNameText = placeName;
        }
        else{
            placeNameText = placeName.substring(0, 39).concat("...");
        }

        return placeNameText;

    }
}

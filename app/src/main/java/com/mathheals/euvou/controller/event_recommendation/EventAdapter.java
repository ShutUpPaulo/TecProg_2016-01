package com.mathheals.euvou.controller.event_recommendation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mathheals.euvou.R;

import java.util.List;

import model.Event;

/**
 * Created by igor on 27/11/15.
 */
public class EventAdapter extends ArrayAdapter<Event> {

    private static final String STRING_EMPTY = "";

    public EventAdapter(Context context, List<Event> events) {
        super(context, 0, events);
    }

    private static class ViewHolder {
        TextView eventName;
        TextView eventEvaluation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Event event = getItem(position);
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.fragment_recommend_event, parent, false);
            viewHolder.eventName = (TextView) convertView.findViewById(R.id.eventName);
            viewHolder.eventEvaluation = (TextView) convertView.findViewById(R.id.eventEvaluation);
            convertView.setTag(viewHolder);
        }

        Integer maximumEventNameLength = 40;
        String shortenedNameEvent = STRING_EMPTY;
        if(event.getNameEvent().length() > maximumEventNameLength){
            shortenedNameEvent = event.getNameEvent().substring(0, 39).concat("...");
        }else{
            shortenedNameEvent = event.getNameEvent();
        }

        viewHolder.eventName.setText(shortenedNameEvent);
        viewHolder.eventEvaluation.setText(event.getEvaluation().toString());

        return convertView;
    }
}

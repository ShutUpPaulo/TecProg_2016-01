/**
 * File: EventAdapter.java
 * Purpose: Adapt event information to improve visualization in the application screen
 */

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

public class EventAdapter extends ArrayAdapter<Event>{

    private static final String STRING_EMPTY = "";

    /**
     * Constructs an event adapter with a given context and list of events
     * @param context Context of event adapter
     * @param events List of given events
     */
    public EventAdapter(Context context, List<Event> events){
        super(context, 0, events);
    }

    /**
     * Class with text view for event name and evaluation
     */
    private static class ViewHolder{
        TextView eventName = null;
        TextView eventEvaluation = null;
    }

    /**
     * Returns View in given position.
     * @param position Position of the View
     * @param convertView Old view that will be reused
     * @param parent Parent that this view will be attached to
     * @return Updated view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        assert parent != null;

        Event event = getItem(position);
        assert event != null;

        ViewHolder viewHolder = getViewHolderByViewAndViewGroup(convertView, parent);

        assert viewHolder != null;

        String shortenedNameEvent = shortenEventName(event.getNameEvent());

        viewHolder.eventName.setText(shortenedNameEvent);
        viewHolder.eventEvaluation.setText(event.getEvaluation().toString());

        return convertView;
    }

    /**
     * Shorts eventName to 40 characters or less
     * @param eventName Event name that will be shortened
     * @return Shortened event name
     */
    private String shortenEventName(String eventName){
        Integer maximumEventNameLength = 40;

        String shortenedNameEvent = STRING_EMPTY;
        if(eventName.length() > maximumEventNameLength){
            shortenedNameEvent = eventName.substring(0, 39).concat("...");
        }else{
            shortenedNameEvent = eventName;
        }

        return shortenedNameEvent;
    }

    /**
     * Gets view holder by view and view group
     * @param convertView
     * @param parent View group
     * @return
     */
    private ViewHolder getViewHolderByViewAndViewGroup(View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if (convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        }else{
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.fragment_recommend_event, parent, false);
            viewHolder.eventName = (TextView) convertView.findViewById(R.id.eventName);
            viewHolder.eventEvaluation = (TextView) convertView.findViewById(R.id.eventEvaluation);
            convertView.setTag(viewHolder);
        }

        return viewHolder;
    }
}

package com.mathheals.euvou.controller.showPlaceRanking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.show_place.ShowPlaceInfo;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.util.ArrayList;
import dao.PlaceDAO;
import exception.PlaceException;
import model.Place;

public class ShowPlaceRank extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener{

    private ArrayList<Place> places;

    /**
     * Required constructor to instantiate a fragment object
     */
    public ShowPlaceRank(){

    }

    /**
     * Calls the parent onCreate to setup the activity view that contains this fragment
     * @param savedInstanceState - If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    /**
     * Creates and returns the view hierarchy associated with the fragment
     * @param inflater - Object used to inflate any views in the fragment
     * @param container - If non-null, is the parent view that the fragment should be attached to
     * @param savedInstanceState - If non-null, this fragment is being re-constructed from a
     *                           previous saved state as given here
     * @return View - View of the ShowPlaceRank fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View rankingView = inflater.inflate(R.layout.fragment_show_place_rank, container, false);

        //Sets the listener that allows to get when the user click in a list item
        ListView listToBeFilled = (ListView) rankingView.findViewById(R.id.listViewPlacesTotall);
        listToBeFilled.setOnItemClickListener(this);

        fillList(listToBeFilled);

        return rankingView;
    }

    /**
     * Fills the list of places
     * @param listToBeFilled - List to be filled with places
     */
    private void fillList(ListView listToBeFilled){

        JSONObject placesData = new PlaceDAO(getActivity()).searchAllPlaces();
        places = new ArrayList<Place>();
        populateArrayOfPlaces(placesData,places);

        //Sets the required adapter to adapt items of the places array in items of the places list
        PlaceAdapter placeAdapter = new PlaceAdapter(getActivity(),places);
        listToBeFilled.setAdapter(placeAdapter);
    }

    /**
     * Populates the array of places with place data
     * @param placesData - Places data obtained through database
     * @param places - Array to be populated
     */
    private void populateArrayOfPlaces(JSONObject placesData, ArrayList<Place> places){

        try{
            for(int i = 0; i < placesData.length(); i++){
                int idPlace = placesData.getJSONObject("" + i).getInt("idPlace");
                String namePlace = placesData.getJSONObject("" + i).getString("namePlace");
                Place aux = new Place(idPlace,namePlace,
                        placesData.getJSONObject("" + i).getString("evaluate"),
                        placesData.getJSONObject("" + i).getString("longitude"),
                        placesData.getJSONObject("" + i).getString("latitude"),
                        placesData.getJSONObject("" + i).getString("operation"),
                        placesData.getJSONObject("" + i).getString("description"),
                        placesData.getJSONObject("" + i).getString("address"),
                        placesData.getJSONObject("" + i).getString("phonePlace")
                );

                places.add(aux);
            }
        }catch(JSONException e){
            e.printStackTrace();

        }catch(PlaceException e){
            e.printStackTrace();

        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    /**
     * Callback method to be invoked when an item in AdapterView has been clicked
     * @param parent - The AdapterView where the click happened
     * @param view - The view within the AdapterView that was clicked
     * @param position - The position of the view in the adapter
     * @param id - The row identifier of the item that was clicked
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        startShowPlaceInfoActivity(position);
    }

    /**
     * Starts the activity with the information of a place
     * @param position - Position of the place at places array
     */
    private void startShowPlaceInfoActivity(int position){
        Intent intent = new Intent(getActivity(), ShowPlaceInfo.class);
        intent.putExtras(getPlaceInfoAsBundle(position));
        startActivity(intent);
    }

    /**
     * Sets the bundle with place information to allow send this information to an activity
     * @param position - Position of the place at places array
     * @return Bundle - Bundle with the place information to be sent to an activity
     */
    private Bundle getPlaceInfoAsBundle(int position){
        Bundle placeInfo = new Bundle();

        placeInfo.putString("name", places.get(position).getPlaceName());
        placeInfo.putString("phone", places.get(position).getPlacePhone());
        placeInfo.putString("address", places.get(position).getPlaceAddress());
        placeInfo.putString("description", places.get(position).getPlaceDescription());
        placeInfo.putDouble("latitude", places.get(position).getPlacetLatitude());
        placeInfo.putDouble("longitude", places.get(position).getPlaceLongitude());
        placeInfo.putString("operation", places.get(position).getOperation());
        placeInfo.putInt("idPlace", places.get(position).getId());

        return placeInfo;
    }
}
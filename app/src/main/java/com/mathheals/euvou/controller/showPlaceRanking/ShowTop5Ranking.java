/**
 * File: ShowTop5Ranking.java
 * Purpose: Show the five places with better evaluation
 */

package com.mathheals.euvou.controller.showPlaceRanking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.event_recommendation.RecommendEvent;
import com.mathheals.euvou.controller.show_place.ShowPlaceInfo;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.util.ArrayList;
import dao.PlaceDAO;
import exception.PlaceException;
import model.Place;

public class ShowTop5Ranking extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener,
        View.OnClickListener{

    private ArrayList<Place> places;

    public ShowTop5Ranking(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View top5RankingView = inflater.inflate(R.layout.fragment_show_top5_rank, container, false);

        addRecommendedEventsFragment();

        Button completeRankingButton = (Button) top5RankingView.findViewById(R.id.more);
        completeRankingButton.setOnClickListener(this);

        ListView listView = (ListView) top5RankingView.findViewById(R.id.listViewPlaces5);
        listView.setOnItemClickListener(this);

        fillList(listView);

        return  top5RankingView;
    }

    private void addRecommendedEventsFragment(){
        Fragment recommendEventFragment = new RecommendEvent();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.recommendedEventList, recommendEventFragment).commit();
    }

    private void fillList(ListView listToBeFilled){
        assert listToBeFilled != null;

        JSONObject placesData = new PlaceDAO(getActivity()).searchTop5Places();
        places = new ArrayList<Place>();
        populateArrayOfPlaces(placesData,places);

        //Sets the required adapter to adapt items of the places array in items of the places list
        PlaceAdapter placeAdapter = new PlaceAdapter(getActivity(),places);

        assert placeAdapter != null;

        listToBeFilled.setAdapter(placeAdapter);

        assert listToBeFilled.getAdapter() == placeAdapter;
    }

    private void populateArrayOfPlaces(JSONObject placesData, ArrayList<Place> places){
        try{
            int placesDataSize = placesData.length();

            for(int i = 0; i < placesDataSize; i++){

                int idPlace = placesData.getJSONObject("" + i).getInt("idPlace");
                String placeName = placesData.getJSONObject("" + i).getString("namePlace");
                String placeEvaluate = placesData.getJSONObject("" + i).getString("evaluate");
                String placeLongitude = placesData.getJSONObject("" + i).getString("longitude");
                String placeLatitude = placesData.getJSONObject("" + i).getString("latitude");
                String placeOperationTime = placesData.getJSONObject("" + i).getString("operation");
                String placeDescription = placesData.getJSONObject("" + i).getString("description");
                String placeAddress = placesData.getJSONObject("" + i).getString("address");
                String placePhone = placesData.getJSONObject("" + i).getString("phonePlace");

                Place aux = new Place(idPlace, placeName, placeEvaluate, placeLongitude,
                        placeLatitude, placeOperationTime, placeDescription, placeAddress,
                        placePhone);

                int placesArrayListSize = places.size();

                places.add(aux);

                assert places.size() == placesArrayListSize + 1;
                assert places.get(placesArrayListSize) == aux;

            }
        }catch (JSONException e){
            e.printStackTrace();
        } catch (PlaceException e){
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        startShowInfoActivity(position);

    }

    private void startShowInfoActivity(int id){
        Intent intent = new Intent(getActivity(), ShowPlaceInfo.class);
        intent.putExtras(getPlaceInfoAsBundle(id));
        startActivity(intent);
    }

    private Bundle getPlaceInfoAsBundle(int id){
        Bundle placeInfo = new Bundle();
        Toast.makeText(getActivity(), "" + id, Toast.LENGTH_LONG);
        placeInfo.putString("name", places.get(id).getPlaceName());
        placeInfo.putString("phone", places.get(id).getPlacePhone());
        placeInfo.putString("address", places.get(id).getPlaceAddress());
        placeInfo.putString("description", places.get(id).getPlaceDescription());
        placeInfo.putDouble("latitude", places.get(id).getPlacetLatitude());
        placeInfo.putDouble("longitude", places.get(id).getPlaceLongitude());
        placeInfo.putString("operation", places.get(id).getOperation());
        placeInfo.putInt("idPlace", places.get(id).getId());
        return placeInfo;
    }

    @Override
    public void onClick(View v){
        android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, new ShowPlaceRank());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}
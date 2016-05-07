package com.mathheals.euvou.controller.showPlaceRanking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
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

    private ListView listView;
    private ArrayList<Place> places;

    public ShowPlaceRank(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View vw = inflater.inflate(R.layout.fragment_show_place_rank, container, false);

        listView = (ListView) vw.findViewById(R.id.listViewPlacesTotall);
        listView.setOnItemClickListener(this);

        fillList();

        return  vw;
    }

    private void fillList(){

            JSONObject result = new PlaceDAO(getActivity()).searchAllPlaces();
            places = new ArrayList<>();
            populateArrayOfPlaces(result,places);

            PlaceAdapter placeAdapter = new PlaceAdapter(getActivity(),places);
            listView.setAdapter(placeAdapter);
    }

    private void populateArrayOfPlaces(JSONObject result, ArrayList<Place> places){

        try{
            for(int i = 0; i < result.length(); i++){
                int idPlace = result.getJSONObject("" + i).getInt("idPlace");
                String namePlace = result.getJSONObject("" + i).getString("namePlace");
                Place aux = new Place(idPlace,namePlace,
                        result.getJSONObject("" + i).getString("evaluate"),
                        result.getJSONObject("" + i).getString("longitude"),
                        result.getJSONObject("" + i).getString("latitude"),
                        result.getJSONObject("" + i).getString("operation"),
                        result.getJSONObject("" + i).getString("description"),
                        result.getJSONObject("" + i).getString("address"),
                        result.getJSONObject("" + i).getString("phonePlace")
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

        Toast.makeText(getActivity(),"" + id,Toast.LENGTH_LONG);

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
}
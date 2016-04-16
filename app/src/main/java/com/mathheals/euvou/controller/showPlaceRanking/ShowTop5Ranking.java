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
import com.mathheals.euvou.controller.utility.LoginUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import dao.PlaceDAO;
import exception.PlaceException;
import model.Place;

public class ShowTop5Ranking extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener{

    private ListView listView;
    private ArrayList<Place> places;

    public ShowTop5Ranking() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vw = inflater.inflate(R.layout.fragment_show_top5_ranking, container, false);
        // Inflate the layout for this fragment
        listView = (ListView) vw.findViewById(R.id.listViewPlaces5);
        listView.setOnItemClickListener(this);
        fillList();
        return  vw;
    }

    private void fillList() {
        try {
            int id = (new LoginUtility(getActivity())).getUserId();
            JSONObject result = new PlaceDAO(getActivity()).searchTop5Places();
            //List<Map<String, String>> placeList= new ArrayList<Map<String, String>>();
            places = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                int idPlace = result.getJSONObject("" + i).getInt("idPlace");
                String namePlace = result.getJSONObject("" + i).getString("namePlace");
                Place aux = new Place(idPlace,
                        namePlace,
                        result.getJSONObject("" + i).getString("evaluate"),
                        result.getJSONObject("" + i).getString("longitude"),
                        result.getJSONObject("" + i).getString("latitude"),
                        result.getJSONObject("" + i).getString("operation"),
                        result.getJSONObject("" + i).getString("description"),
                        result.getJSONObject("" + i).getString("address"),
                        result.getJSONObject("" + i).getString("phonePlace")
                );
                places.add(aux);
                //placeList.add(placeRank(place.getJSONObject("" + i).getString("namePlace"), place.getJSONObject("" + i).getString("evaluate")));

            }
            PlaceAdapter placeAdapter = new PlaceAdapter(getActivity(),places);
            /*SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),placeList,
                    android.R.layout.simple_list_item_2,
                    new String[]{"Nome","Nota"}, new int[]{android.R.id.text1});

            Toast.makeText(getActivity(), simpleAdapter.toString(), Toast.LENGTH_SHORT).show();
*/
            listView.setAdapter(placeAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (PlaceException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startShowInfoActivity(position);

    }

    private void startShowInfoActivity(int id) {
        Intent intent = new Intent(getActivity(), ShowPlaceInfo.class);
        intent.putExtras(getPlaceInfoAsBundle(id));
        startActivity(intent);
    }

    private Bundle getPlaceInfoAsBundle(int id) {
        Bundle placeInfo = new Bundle();
        Toast.makeText(getActivity(), "" + id, Toast.LENGTH_LONG);
        placeInfo.putString("name", places.get(id).getName());
        placeInfo.putString("phone", places.get(id).getPhone());
        placeInfo.putString("address", places.get(id).getAddress());
        placeInfo.putString("description", places.get(id).getDescription());
        placeInfo.putDouble("latitude", places.get(id).getLatitude());
        placeInfo.putDouble("longitude", places.get(id).getLongitude());
        placeInfo.putString("operation", places.get(id).getOperation());
        placeInfo.putInt("idPlace", places.get(id).getId());
        return placeInfo;
    }

}
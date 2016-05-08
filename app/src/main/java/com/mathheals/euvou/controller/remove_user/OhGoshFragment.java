/**
 * File: OhGoshFragment.java
 * Purpose: Set "OhGosh" message fragment on view
 */


package com.mathheals.euvou.controller.remove_user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mathheals.euvou.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OhGoshFragment extends android.support.v4.app.Fragment {


    public OhGoshFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        assert inflater != null;

        View ohGoshFragmentView = inflater.inflate(R.layout.fragment_oh_gosh, container, false);

        return ohGoshFragmentView;
    }


}

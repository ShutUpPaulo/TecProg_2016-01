package com.mathheals.euvou.controller.remove_user;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mathheals.euvou.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemoveUserFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    public RemoveUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_remove_user, container, false);

        Button deactivateButton = (Button)view.findViewById(R.id.button_disable_account_id);
        deactivateButton.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {

        FragmentActivity activity = this.getActivity();
        android.support.v4.app.FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        switch(view.getId()) {
            case R.id.button_disable_account_id:
                fragmentTransaction.replace(R.id.content_frame, new OhGoshFragment());
                fragmentTransaction.add(R.id.content_frame, new DisableAccountFragment(), String.valueOf(R.string.DISABLE_ACCOUNT_FRAGMENT_TAG));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.addToBackStack(String.valueOf(R.string.DISABLE_ACCOUNT_FRAGMENT_TAG));
                fragmentTransaction.commit();
                break;

            default:
                //nothing to do
                break;
        }
    }
}

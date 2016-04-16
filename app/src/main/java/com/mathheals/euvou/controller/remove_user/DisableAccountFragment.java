package com.mathheals.euvou.controller.remove_user;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mathheals.euvou.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisableAccountFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    public DisableAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_disable_account, container, false);

        Button yesButton = (Button)view.findViewById(R.id.button_yes_id);
        yesButton.setOnClickListener(this);

        Button noButton = (Button)view.findViewById(R.id.button_no_id);
        noButton.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        FragmentActivity activity = this.getActivity();
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Context homePageContext = activity.getBaseContext();

        switch(view.getId()) {
            case R.id.button_yes_id:
                fragmentManager.popBackStack();
                RemoveUserVIewMessages.showWelcomeBackMessage(homePageContext);
                return;
            case R.id.button_no_id:
                android.support.v4.app.Fragment disableAccountFragment = activity.getSupportFragmentManager().findFragmentByTag(String.valueOf(R.string.DISABLE_ACCOUNT_FRAGMENT_TAG));
                fragmentTransaction.remove(disableAccountFragment);
                fragmentTransaction.add(R.id.content_frame, new DisableAccountLoginConfirmation());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
        }
    }
}

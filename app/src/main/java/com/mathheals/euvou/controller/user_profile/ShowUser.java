package com.mathheals.euvou.controller.user_profile;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.mathheals.euvou.R;
import com.mathheals.euvou.controller.utility.LoginUtility;
import org.json.JSONException;
import org.json.JSONObject;
import dao.UserDAO;
import dao.UserEvaluationDAO;
import exception.UserEvaluationException;
import model.UserEvaluation;

public class ShowUser extends android.support.v4.app.Fragment implements RatingBar.OnRatingBarChangeListener{

    private UserEvaluation userEvaluation;
    private RatingBar ratingBar;
    private View showUserView;
    private String userEvaluatedId;
    private int currentUserId;
    private boolean isUserLoggedIn;

    public ShowUser(){
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        setShowUserView(inflater.inflate(R.layout.show_user, container, false));

        UserDAO userDAO = new UserDAO(getActivity());

        userEvaluatedId = this.getArguments().getString("id");

        setCurrentUserId(new LoginUtility(getActivity()).getUserId());

        JSONObject userData = null;

        try{
            userData = new JSONObject(userDAO.searchUserById(Integer.parseInt(userEvaluatedId)));

        }catch (JSONException e){
            e.printStackTrace();
        }

        try{
            String nameUserDB = userData.getJSONObject("0").getString("nameUser");
            String birthDateDB = userData.getJSONObject("0").getString("birthDate");
            String mailDB = userData.getJSONObject("0").getString("email");

            TextView name= (TextView) showUserView.findViewById(R.id.labelName);
            TextView date = (TextView) showUserView.findViewById(R.id.labelBirthDate);
            TextView mail = (TextView) showUserView.findViewById(R.id.labelMail);

            name.setText(nameUserDB);
            date.setText(birthDateDB);
            mail.setText(mailDB);

        }catch (JSONException e){
            e.printStackTrace();
        }

        Integer LOGGED_OUT = -1; //-1 is the flag for user logged out

        setIsUserLoggedIn(currentUserId != LOGGED_OUT);

        setRatingMessage(isUserLoggedIn);

        setRatingBarIfNeeded();

        return showUserView;
    }

    private void setIsUserLoggedIn(boolean isUserLoggedIn){
        this.isUserLoggedIn = isUserLoggedIn;
    }

    private void setRatingMessage(boolean isUserLoggedIn){

        final String LOGGED_IN_MESSAGE = "Sua avaliação:";
        final String LOGGED_OUT_MESSAGE = "Faça login para avaliar este usuário!";

        String message;

        if(isUserLoggedIn){
            message =  LOGGED_IN_MESSAGE;
        }
        else{
            message = LOGGED_OUT_MESSAGE;
        }

        TextView ratingMessage = (TextView) showUserView.findViewById(R.id.rate_user_text);

        ratingMessage.setText(message);
    }

    private void setShowUserView(View showUserView){
        this.showUserView = showUserView;
    }

    private void setRatingBarIfNeeded(){
        if(isUserLoggedIn){
            setRatingBar();
        }
        else{
            //RatingBar is not enable for users logged out
        }
    }

    private void setCurrentUserId(int currentUserId){
        this.currentUserId = currentUserId;
    }

    private void setRatingBar(){

        ratingBar = (RatingBar) showUserView.findViewById(R.id.ratingBar);
        ratingBar.setVisibility(View.VISIBLE);

        UserEvaluationDAO userEvaluationDAO = new UserEvaluationDAO();

        JSONObject evaluationJSON = userEvaluationDAO.searchUserEvaluation(
                Integer.parseInt(userEvaluatedId), currentUserId);

        if(evaluationJSON != null){
            Float evaluation = null;

            try{
                evaluation = new Float(evaluationJSON.getJSONObject("0").getDouble("grade"));

            }catch (JSONException e){
                e.printStackTrace();
            }

            ratingBar.setRating(evaluation);
        }

        ratingBar.setOnRatingBarChangeListener(this);

        setRatingBarStyle();
    }

    private UserEvaluation getUserEvaluation(){
        return userEvaluation;
    }

    private void setUserEvaluation(Float rating, Integer userId, Integer userEvaluatedId){
        try{
            this.userEvaluation = new UserEvaluation(rating, userId, userEvaluatedId);

            String SUCCESSFUL_EVALUATION_MESSAGE = "Avaliação cadastrada com sucesso";

            Toast.makeText(getActivity().getBaseContext(), SUCCESSFUL_EVALUATION_MESSAGE,
                    Toast.LENGTH_LONG).show();

        }catch(UserEvaluationException exception){

            switch(exception.getMessage()){
                case UserEvaluation.EVALUATION_IS_INVALID:
                    Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();

                case UserEvaluation.USER_EVALUATED_ID_IS_INVALID:
                    Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();

                case UserEvaluation.USER_ID_IS_INVALID:
                    Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setRatingBarStyle(){
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();

        stars.getDrawable(2).setColorFilter(ContextCompat.
                getColor(getContext(), R.color.turquesa_app), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser){

        setUserEvaluation(rating, currentUserId, Integer.valueOf(userEvaluatedId));

        UserEvaluationDAO userEvaluationDAO = new UserEvaluationDAO();

        userEvaluationDAO.evaluateUser(getUserEvaluation());
    }
}

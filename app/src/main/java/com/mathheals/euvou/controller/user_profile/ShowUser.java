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

public class ShowUser extends android.support.v4.app.Fragment implements
        RatingBar.OnRatingBarChangeListener{

    private final int INVALID_IDENTIFIER = -1; //Flag for used logged out
    private String userEvaluatedId = null;
    private int currentUserId = INVALID_IDENTIFIER;
    private UserEvaluation userEvaluation;

    /**
     * Required constructor to instantiate a fragment object
     */
    public ShowUser(){

    }

    /**
     * Creates and returns the view hierarchy associated with the fragment
     * @param inflater - Object used to inflate any views in the fragment
     * @param container - If non-null, is the parent view that the fragment should be attached to
     * @param savedInstanceState - If non-null, this fragment is being re-constructed from a
     *                           previous saved state as given here
     * @return View - View of the ShowPlaceRank fragment
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View showUserView = inflater.inflate(R.layout.show_user, container, false);

        //Gets the identifier of the user logged in
        LoginUtility loginUtility = new LoginUtility(this.getActivity());
        currentUserId = loginUtility.getUserId();

        boolean isUserLoggedIn = loginUtility.hasUserLoggedIn();

        getUserInfoFromDataBase(showUserView);

        setUpRatingBar(isUserLoggedIn, showUserView);

        return showUserView;
    }

    /**
     * Triggers actions that must be done when the rating changes
     * @param ratingBar - The RatingBar whose rating has changed
     * @param rating - The current rating (>=0 ... <=5)
     * @param fromUser - True if the rating change was initiated by a user's touch gesture or
     *                   arrow key/horizontal trackbell movement
     */
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser){

        setUserEvaluation(rating, currentUserId, Integer.valueOf(userEvaluatedId));

        //Saves the user evaluation set at database
        UserEvaluationDAO userEvaluationDAO = new UserEvaluationDAO(getActivity());
        userEvaluationDAO.evaluateUser(userEvaluation);
    }

    /**
     * Instantiates an UserEvaluation object
     * @param rating - Evaluation given to the user (>=0 ... <=5)
     * @param userId - Identifier of the evaluator user
     * @param userEvaluatedId - Identifier of the user evaluated
     */
    private void setUserEvaluation(Float rating, Integer userId,
                                   Integer userEvaluatedId){
        try{
            //Tries to instantiate an UserEvaluation object
            this.userEvaluation = new UserEvaluation(rating, userId, userEvaluatedId);

            //Shows a successful message if the object was instantiated
            final String SUCCESSFUL_EVALUATION_MESSAGE = "Avaliação cadastrada com sucesso";
            Toast.makeText(getActivity().getBaseContext(), SUCCESSFUL_EVALUATION_MESSAGE,
                    Toast.LENGTH_LONG).show();

        }catch(UserEvaluationException exception){

            //Sets the error message if the evaluation is invalid
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

    /**
     * Get the user name, birth date and mail from database
     */
    private void getUserInfoFromDataBase(final View showUserView){
        try{
            //Gets the JSONObject with the user data according to the user identifier
            UserDAO userDAO = new UserDAO(getActivity());
            userEvaluatedId = this.getArguments().getString("id");
            JSONObject userData = new JSONObject(userDAO.searchUserById(Integer
                    .parseInt(userEvaluatedId)));

            //Gets each user attribute from the JSONObject obtained above
            String userName = userData.getJSONObject("0").getString("nameUser");
            String userBirthDate = userData.getJSONObject("0").getString("birthDate");
            String userMail = userData.getJSONObject("0").getString("email");

            //Shows the user data on the text view
            showUserInformationOnTextView(showUserView, userName, userBirthDate, userMail);

        }catch(JSONException jsonException){
            jsonException.printStackTrace();
        }
    }

    /**
     * If the user already has an evaluation, this method sets it at ratingBar
     * @param ratingBar - RatingBar to sets the evaluation
     */
    private void setEvaluationAtRatingBar(RatingBar ratingBar){

        //Searches the user evaluation at database
        UserEvaluationDAO userEvaluationDAO = new UserEvaluationDAO(getActivity());
        JSONObject userEvaluationAtDataBase = userEvaluationDAO.searchUserEvaluation(
                Integer.parseInt(userEvaluatedId), currentUserId);

        if(userEvaluationAtDataBase != null){
            try{
                //Gets the user evaluation from database and sets it at rating bar
                Float currentUserEvaluation = new Float(userEvaluationAtDataBase.getJSONObject("0")
                        .getDouble("grade"));
                ratingBar.setRating(currentUserEvaluation);

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        else{
            //If the user don't have an evaluation, it don't need to be set at ratingBar
        }
    }

    /**
     * Sets the TextViews to show the user information
     * @param showUserView - View that contains the TextViews
     * @param userName - Name to be set at the text view that shows the user name
     * @param userBirthDate - Date to be set at the text view that shows the user birth date
     * @param userMail - Mail to be set at the text view that shows the user mail
     */
    private void showUserInformationOnTextView(View showUserView, String userName,
                                               String userBirthDate, String userMail){

        //Gets the text views of the fragment view
        TextView userNameTextView = (TextView) showUserView.findViewById(R.id.labelName);
        TextView userDateTextView = (TextView) showUserView.findViewById(R.id.labelBirthDate);
        TextView userMailTextView = (TextView) showUserView.findViewById(R.id.labelMail);

        //Sets the text of text views for the data of the parameters
        userNameTextView.setText(userName);
        userDateTextView.setText(userBirthDate);
        userMailTextView.setText(userMail);
    }

    /**
     * Sets the message of the ratingBar label based on user login status
     * @param showUserView - View that contains the ratingBar label
     * @param message - Message to be displayed at the label
     */
    private void setRatingMessage(View showUserView, String message){
        TextView ratingMessageTextView = (TextView) showUserView.findViewById(R.id.rate_user_text);
        ratingMessageTextView.setText(message);
    }

    /**
     * Sets the necessary configurations of the ratingBar based on user login status
     * @param isUserLoggedIn - User login status
     * @param showUserView - View that contains the ratingBar
     */
    private void setUpRatingBar(boolean isUserLoggedIn, View showUserView){
        if(isUserLoggedIn){
            //Sets the rating bar message
            final String LOGGED_IN_MESSAGE = "Sua avaliação:";
            setRatingMessage(showUserView, LOGGED_IN_MESSAGE);

            //Sets the listener and visibility for the rating bar
            RatingBar ratingBar = (RatingBar) showUserView.findViewById(R.id.ratingBar);
            ratingBar.setOnRatingBarChangeListener(this);
            ratingBar.setVisibility(View.VISIBLE);

            setRatingBarStyle(ratingBar);

            setEvaluationAtRatingBar(ratingBar);
        }
        else{
            final String LOGGED_OUT_MESSAGE = "Faça login para avaliar este usuário!";
            setRatingMessage(showUserView, LOGGED_OUT_MESSAGE);
        }
    }

    /**
     * Sets the style of the ratingBar
     * @param ratingBar - RatingBar to have its style set
     */
    private void setRatingBarStyle(RatingBar ratingBar){
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();

        //Sets the color of the rating bar stars
        stars.getDrawable(2).setColorFilter(ContextCompat.
                getColor(getContext(), R.color.turquesa_app), PorterDuff.Mode.SRC_ATOP);
    }
}

/**
 * File: RemoveUserVIewMessages.java
 * Purpose: Set the messages on remove user view
 */


package com.mathheals.euvou.controller.remove_user;

import android.content.Context;
import android.widget.Toast;

import com.mathheals.euvou.R;

public class RemoveUserViewMessages{
    /**
     * Shows "Welcome Back" message
     * @param context Context needed in makeTest method of Toast class
     */
    public static void showWelcomeBackMessage(Context context){
        final String WELCOME_BACK_MESSAGE =
                context.getResources().getString(R.string.welcome_back);
        Toast.makeText(context, WELCOME_BACK_MESSAGE, Toast.LENGTH_LONG).show();
    }

    /**
     * Shows "Account Deactivate" message
     * @param context Context needed in makeTest method of Toast class
     */
    public static void showAccountDeactivateMessage(Context context){
        final String BYE_BYE_MESSAGE =
                context.getResources().getString(R.string.deactivated_account);
        Toast.makeText(context, BYE_BYE_MESSAGE, Toast.LENGTH_LONG).show();
    }
}

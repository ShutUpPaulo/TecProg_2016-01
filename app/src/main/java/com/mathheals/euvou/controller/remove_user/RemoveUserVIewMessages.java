package com.mathheals.euvou.controller.remove_user;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by marlonmendes on 25/09/15.
 */
public class RemoveUserVIewMessages {
    private static final String BYE_BYE_MESSAGE = "Conta desativada :(";
    private static final String WELCOME_BACK_MESSAGE = "Seja bem vindo novamente!";


    public static void showWelcomeBackMessage(Context context) {
        Toast.makeText(context, WELCOME_BACK_MESSAGE, Toast.LENGTH_LONG).show();
    }
    public static void showAccountDeactivateMessage(Context context) {
        Toast.makeText(context, BYE_BYE_MESSAGE, Toast.LENGTH_LONG).show();
    }
}

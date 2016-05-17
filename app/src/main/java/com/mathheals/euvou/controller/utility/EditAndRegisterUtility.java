/*
 *File: EditAndRegisterUtility.java
 *Purpose: allow ro edit and register an utility
 */
package com.mathheals.euvou.controller.utility;

import android.support.v4.app.Fragment;
import android.widget.EditText;

public class EditAndRegisterUtility{

    /**
     * Required constructor to instantiate the class
     */
    public EditAndRegisterUtility(){}

    /**
     * Sets an error message with focus on the textfield
     * @param editText
     * @param message
     */
    public void setMessageError(EditText editText, String message){
        editText.requestFocus();
        editText.setError(message);
    }
}

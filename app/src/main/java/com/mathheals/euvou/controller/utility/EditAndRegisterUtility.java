/*
 *File: EditAndRegisterUtility.java
 *Purpose: allow ro edit and register an utility
 */
package com.mathheals.euvou.controller.utility;

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
        assert editText != null;
        assert message != null;

        editText.requestFocus();
        editText.setError(message);
    }
}

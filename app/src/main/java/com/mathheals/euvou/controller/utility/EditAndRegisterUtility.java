/*
 *File: EditAndRegisterUtility.java
 *Purpose: allow ro edit and register an utility
 */
package com.mathheals.euvou.controller.utility;

import android.support.v4.app.Fragment;
import android.widget.EditText;

public class EditAndRegisterUtility{

    public EditAndRegisterUtility(){}

    public void setMessageError(EditText editText, String message){
        editText.requestFocus();
        editText.setError(message);
    }
}

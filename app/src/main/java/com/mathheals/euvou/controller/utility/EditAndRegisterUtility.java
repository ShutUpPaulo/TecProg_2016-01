package com.mathheals.euvou.controller.utility;

import android.support.v4.app.Fragment;
import android.widget.EditText;

/**
 * Created by izabela on 28/11/15.
 */
public class EditAndRegisterUtility {

    public EditAndRegisterUtility(){

    }

    public void setMessageError(EditText editText, String message) {
        editText.requestFocus();
        editText.setError(message);
    }
}

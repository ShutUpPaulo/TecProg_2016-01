package com.mathheals.euvou.controller.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public abstract class Mask{

    /**
     * Method used to format date
     * @param stringToBeMasked - Defines the string to be formatted
     * @return
     */
    private static String unmask(String stringToBeMasked){
        return stringToBeMasked.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "");
    }

    /**
     * Inserts an TextWatcher to format the data received when is required
     * @param mask - The format of the data
     * @param ediTxt - The field to be edited
     * @return
     */
    public static TextWatcher insert(final String mask, final EditText ediTxt){
        return new TextWatcher(){
            boolean isUpdating;
            String old = "";

            /**
             * Changes the style of the data received
             * @param stringToBeMasked - Defines the string to be formatted
             * @param start
             * @param before
             * @param count
             */
            public void onTextChanged(CharSequence stringToBeMasked, int start, int before,
                                      int count){
                String str = Mask.unmask(stringToBeMasked.toString());
                String mascara = "";
                if (isUpdating){
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()){
                    if (m != '#' && str.length() > old.length()){
                        mascara += m;
                        continue;
                    }
                    try{
                        mascara += str.charAt(i);
                    }catch (Exception e){
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                ediTxt.setText(mascara);
                ediTxt.setSelection(mascara.length());

                assert ediTxt.getText().equals(mascara);
            }

            /**
             * Required method by the Mask class
             * @param stringToBeMasked
             * @param start
             * @param count
             * @param after
             */
            @Override
            public void beforeTextChanged(CharSequence stringToBeMasked, int start, int count,
                                          int after){
                //Nothing to do
            }

            /**
             * Required method by the Mask class
             * @param stringToBeMasked
             */
            @Override
            public void afterTextChanged(Editable stringToBeMasked){
                //Nothing to do
            }
        };
    }

    /**
     * Receive the date and time and change to brazilian format
     * @param dateTime
     * @return
     */
    public static String getDateTimeInBrazilianFormat(String dateTime){
        String[] dateAndTime = dateTime.split(" ");
        String date = dateAndTime[0];

        String[] dateSplit = date.split("-");

        String brazilianDateFormat = dateSplit[2] + "-" +
                dateSplit[1] + "-" +
                dateSplit[0];

        String dateInBrazilianFormat = brazilianDateFormat + " " + dateAndTime[1];

        return dateInBrazilianFormat;
    }
}


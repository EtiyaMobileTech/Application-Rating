/*
 * Copyright (C) 2017 Ferid Cafer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ferid.lib.applicationrating;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;


/**
 * Created by ferid.cafer on 3/10/2017.
 */
public class ApplicationRating {

    private static SharedPreferences sPrefs;            //shared preferences

    private static int sBeforeRatingViewingNumber = 0;  //0 beginning, -1 never show
    private static int sFrequency = 30;                 //usage frequency
    //after so many times of usage the user will be prompted to rate the application

    //alert dialog texts
    private static String sMessage;                     //message to show
    private static String sPositiveButtonText;          //positive (yes) button text
    private static String sNegativeButtonText;          //negative (no) button text


    /**
     * Manages when to prompt user
     * @param context Context
     * @param frequency Usage frequency (default is 30)
     * @param message Alert message to show (can be null)
     * @param positiveButtonText Positive button's text (yes).<br />
     *                           Opens play store and never asks again
     * @param negativeButtonText Negative button's text (no).<br />
     *                           Asks again later after given number of frequency
     */
    public static void initRatingPopupManager(Context context, int frequency, String message,
                                              String positiveButtonText,
                                              String negativeButtonText) {

        //init params
        if (frequency > 0) {
            sFrequency = frequency;
        }

        if (message != null && positiveButtonText != null && negativeButtonText != null) {
            sMessage = message;
            sPositiveButtonText = positiveButtonText;
            sNegativeButtonText = negativeButtonText;
        }
        //---

        //read from preferences
        readFromDisk(context);
        //if it is not rated yet
        if (sBeforeRatingViewingNumber > 0) {
            //check sFrequency
            if (sBeforeRatingViewingNumber % sFrequency == 0) {
                //prompt user
                showPopup(context);
            } else {
                saveOnDisk(context);
            }
        }
    }

    /**
     * Reads from shared preferences
     * @param context
     */
    private static void readFromDisk(Context context) {
        try {
            sPrefs = context.getSharedPreferences(context.getString(R.string.sharedPreferences), 0);
            sBeforeRatingViewingNumber = sPrefs.getInt(context
                    .getString(R.string.pref_beforeRatingViewingNumber), 0);
            if (sBeforeRatingViewingNumber >= 0) {//if smaller than 0, it will never be shown again
                sBeforeRatingViewingNumber++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves to shared preferences
     * @param context
     */
    private static void saveOnDisk(Context context) {
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putInt(context
                .getString(R.string.pref_beforeRatingViewingNumber), sBeforeRatingViewingNumber);
        editor.apply();
    }

    /**
     * Shows a dialog as a pop up
     * @param context
     */
    private static void showPopup(final Context context) {
        //alert
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //message
        builder.setMessage(sMessage);
        //positive button
        builder.setPositiveButton(sPositiveButtonText,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user decides to rate it
                rateApplication(context);
                //never prompt again
                sBeforeRatingViewingNumber = -1;
                //save the situation
                saveOnDisk(context);
            }
        });
        //negative button
        builder.setNegativeButton(sNegativeButtonText,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user does not want to rate yet
                saveOnDisk(context);
            }
        });

        //create and show
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Leads user to store's rating panel
     * @param context
     */
    private static void rateApplication(Context context) {
        final String appName = context.getPackageName();
        try { //if app store is installed it will open
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appName)));
        } catch (android.content.ActivityNotFoundException anfe) { //otherwise brower will open
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="+appName)));
        }
    }

}
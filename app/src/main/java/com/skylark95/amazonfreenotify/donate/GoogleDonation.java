/* 
 * This file is part of Amazon App Notifier (Free App Notifier for Amazon)
 *
 * Copyright 2013 Derek <derek@skylark95.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Amazon App Notifier is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Amazon App Notifier. if not, If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package com.skylark95.amazonfreenotify.donate;

import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.skylark95.amazonfreenotify.AmazonAppNotifier;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.donate.google.IabHelper;
import com.skylark95.amazonfreenotify.donate.google.IabResult;
import com.skylark95.amazonfreenotify.donate.google.Purchase;
import com.skylark95.amazonfreenotify.util.Logger;

import java.util.ArrayList;
import java.util.List;

/*
 * Portions copyright 2012, Google, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * 
 */
public class GoogleDonation implements Donation {

    private static final String TAG = Logger.getTag(GoogleDonation.class);
    private static final int REQUEST_CODE = 3;

    private IabHelper helper;
    private boolean iabAvailable;
    private AmazonAppNotifier activity;    
    
    public GoogleDonation(AmazonAppNotifier activity) {
        this(activity, new IabHelper(activity, new StringBuilder()
            .append("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQE")
            .append("A6pkXAU959n2gqDdzhiXkd2zi1wfCGghXaHgOhMXb7a")
            .append("LixLDG5jtmnyxUero9Sj8sOF/NNkLGoEL8vQT12q6tv")
            .append("yY7WI7KkxqnGe0lMbkTb6GTi8C5cRz5kYO6YVXPHamI")
            .append("iLnzVk+wr3+R+7tf1P4kSLNDA7Xf4mxqEkzpWil7Hpb")
            .append("ieRtourJoA5xEzce4iI97Kld53e3jeObwcWgV2Wsv52")
            .append("+HdxD0+QALOmf7JDVymNYHIDDIYlBisHvyRtV+H/CbE")
            .append("F5g+72Yb2ufN4/G0gwsPubJC1LtTzZ0DnNfOI81D8pf")
            .append("zoP14OcWXhtYkz9YgDaas9h2FHPtk7VYP17nnrIMPQI")
            .append("DAQAB").toString()
        ));
    }
    
    public GoogleDonation(AmazonAppNotifier activity, IabHelper helper) {        
        this.activity = activity;
        this.helper = helper;
        helper.enableDebugLogging(false);
        setup();
    }

    private void setup() {
        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        helper.startSetup(new IabHelper.OnIabSetupFinishedListener() {

            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                iabAvailable = result.isSuccess();
                if (!iabAvailable) {
                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                }
            }
        });

    }

    // Callback for when a purchase is finished
    private IabHelper.OnIabPurchaseFinishedListener purchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
            if (result.isFailure()) {
                complain("Error purchasing: " + result.getMessage());
                return;
            }

            Log.d(TAG, "Purchase successful.");
            helper.consumeAsync(purchase, null);
            alert(activity.getString(R.string.donate_thank_you_dialog));
        }
    };

    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void shutdown() {
        // very important:
        Log.d(TAG, "Destroying helper.");
        if (helper != null)
            helper.dispose();
        helper = null;
    }

    private void complain(String message) {
        Log.e(TAG, "**** Error: " + message);
    }

    private void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(activity);
        bld.setTitle("Donate");
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }
    
    @Override
    public void setupView(View view) {
        final Button button = (Button) view.findViewById(R.id.donate_button);
        final Spinner spinner = (Spinner) view.findViewById(R.id.donation_amounts_spinner);
        setupSpinner(spinner);        
        setupButton(button, spinner);
    }

    private void setupSpinner(final Spinner spinner) {
        List<String> spinnerAmounts = new ArrayList<String>();
        for (DonationAmount donationAmount : DonationAmount.donationList()) {
            spinnerAmounts.add(donationAmount.getDisplayValue());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, spinnerAmounts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setupButton(final Button button, final Spinner spinner) {
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iabAvailable) {
                    donate(spinner);
                } else {
                    alert(activity.getString(R.string.google_play_no_billing));
                }
            }
        });
    }

    private void donate(final Spinner spinner) {
        String displayValue = String.valueOf(spinner.getSelectedItem());
        DonationAmount amount = DonationAmount.getDonationAmountByDisplayValue(displayValue);
        activity.startDonation(helper, amount.getSku(), REQUEST_CODE, purchaseFinishedListener);
    }

}

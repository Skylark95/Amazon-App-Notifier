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

package com.skylark95.amazonfreenotify.tabs;

import static com.skylark95.amazonfreenotify.util.TestUtils.*;
import static org.junit.Assert.*;

import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.skylark95.amazonfreenotify.AmazonAppNotifier;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.donate.Donation;

import org.junit.Before;
import org.junit.Test;

public abstract class AbstractDonateTest {

    protected AmazonAppNotifier activity;
    protected DonateFragment fragment;
    protected Donation donation;
    protected View view;
    protected Button donateButton;
    protected Spinner amountsSpinner;
    
    @Before
    public void setUp() {
        activity = new AmazonAppNotifier();
        setupFragment();
        startFragment(fragment, activity);
        view = fragment.getView();
        donateButton = (Button) view.findViewById(R.id.donate_button);
        amountsSpinner = (Spinner) view.findViewById(R.id.donation_amounts_spinner);
    }

    @Test
    public void donateTabDoesHaveDonateButton() {        
        assertNotNull(donateButton);
        assertEquals(View.VISIBLE, donateButton.getVisibility());
    }

    protected abstract Donation donationUnderTest();

    private void setupFragment() {
        fragment = new DonateFragment();
        donation = donationUnderTest();
        fragment.setDonation(donation);
    }

}

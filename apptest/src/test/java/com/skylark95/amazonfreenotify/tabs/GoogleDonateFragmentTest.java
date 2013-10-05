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

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;

import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.donate.Donation;
import com.skylark95.amazonfreenotify.donate.GoogleDonation;
import com.skylark95.amazonfreenotify.donate.google.IabHelper;
import com.skylark95.amazonfreenotify.donate.google.IabHelper.OnIabPurchaseFinishedListener;
import com.skylark95.amazonfreenotify.donate.google.IabHelper.OnIabSetupFinishedListener;
import com.skylark95.amazonfreenotify.donate.google.IabResult;
import com.skylark95.amazonfreenotify.donate.google.Purchase;
import com.skylark95.robolectric.AmazonAppNotifierTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowAlertDialog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

@RunWith(AmazonAppNotifierTestRunner.class)
public class GoogleDonateFragmentTest extends AbstractDonateTest {
    
    
    private IabHelper mockIabHelper;
    private Purchase mockPurchase;


    @Before
    public void setUp() {
        super.setUp();
        mockPurchase = null;        
    }
	
	@Test
	public void donateTabHasDonateAmountsSpinner() {		
        assertNotNull(amountsSpinner);
        assertEquals(View.VISIBLE, amountsSpinner.getVisibility());
	}
	
	@Test
	public void pressDonateButton() {
	    donateButton.performClick();
	}
	
	@Test
	public void verifyDebugLoggingIsOff() {
	    verify(mockIabHelper).enableDebugLogging(false);
	    verify(mockIabHelper, never()).enableDebugLogging(true);
	}
	
	@Test
	public void testDonationAmount1() {
	    testDefaultDonationAmount();
	}
	
	@Test
	public void testDonationAmount2() {
	    testDonationAmount(1, 2);
	}
	
	@Test
	public void testDonationAmount3() {
	    testDonationAmount(2, 3);
	}
	
	@Test
	public void testDonationAmount5() {
	    testDonationAmount(3, 5);
	}
	
	@Test
	public void testDonationAmount10() {
	    testDonationAmount(4, 10);
	}
	
	@Test
	public void cannotDonateIfSetupFailed() {
	    givenSetupFinishedWithErrors();
	    amountsSpinner.setSelection(0);
	    donateButton.performClick();
	    verify(mockIabHelper, never()).launchPurchaseFlow(any(Activity.class), anyString(), anyInt(), any(OnIabPurchaseFinishedListener.class));	    
	}
	
	@Test
	public void doesShowNoBillingDialogIfSetupFailed() {
	    givenSetupFinishedWithErrors();
        amountsSpinner.setSelection(0);
        donateButton.performClick();
	    AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
        ShadowAlertDialog shadowDialog = shadowOf(dialog);
        assertEquals(activity.getString(R.string.google_play_no_billing), shadowDialog.getMessage());
	}
	
	@Test
	public void doesShowThankYouIfDonateSuccess() {
	    testDefaultDonationAmount();
	    givenPurchaseFinishedSuccessfully();
	    AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
        ShadowAlertDialog shadowDialog = shadowOf(dialog);
        assertEquals(activity.getString(R.string.donate_thank_you_dialog), shadowDialog.getMessage());
	}
	
	@Test
	public void doesNotShowThankYouIfDonateFailure() {
	    testDefaultDonationAmount();
	    givenPurchaseFinishedWithErrors();
	    assertNull(ShadowAlertDialog.getLatestAlertDialog());
	}
	
	@Test
	public void doesConsumePurchaseIfSuccessful() {
	    testDefaultDonationAmount();
	    givenPurchaseFinishedSuccessfully();
	    verify(mockIabHelper).consumeAsync(mockPurchase, null);
	}
	
	@Test
    public void doesNotConsumePurchaseIfFailure() {
        testDefaultDonationAmount();
        givenPurchaseFinishedWithErrors();
        verify(mockIabHelper, never()).consumeAsync(mockPurchase, null);
    }
	
	@Test
	public void callingOnDestroyDoesShutdownIabHelperIfSetup() {
	    givenSetupFinishedSuccessfully();
	    fragment.onDestroy();
	    verify(mockIabHelper).dispose();
	}
	
	@Test
	public void callingOnDestroyDoesNotShutDownIabHelperIfNotSetup() {
	    givenSetupFinishedWithErrors();
        fragment.onDestroy();
        verify(mockIabHelper, never()).dispose();
	}

    private void testDefaultDonationAmount() {
        testDonationAmount(0, 1);
    }

    private void testDonationAmount(int pos, int amount) {
        givenSetupFinishedSuccessfully();
	    amountsSpinner.setSelection(pos);
	    donateButton.performClick();
	    verify(mockIabHelper).launchPurchaseFlow(eq(activity), eq("donate" + amount+ "_v3"), eq(3), any(OnIabPurchaseFinishedListener.class));	    
    }
    
    private void givenPurchaseFinishedSuccessfully() {
        ArgumentCaptor<OnIabPurchaseFinishedListener> purchaseListener = ArgumentCaptor.forClass(OnIabPurchaseFinishedListener.class);
        verify(mockIabHelper).launchPurchaseFlow(any(Activity.class), anyString(), anyInt(), purchaseListener.capture());
        mockPurchase = mock(Purchase.class);
        purchaseListener.getValue().onIabPurchaseFinished(positiveIabResult(), mockPurchase);
    }
    
    private void givenPurchaseFinishedWithErrors() {
        ArgumentCaptor<OnIabPurchaseFinishedListener> purchaseListener = ArgumentCaptor.forClass(OnIabPurchaseFinishedListener.class);
        verify(mockIabHelper).launchPurchaseFlow(any(Activity.class), anyString(), anyInt(), purchaseListener.capture());
        mockPurchase = mock(Purchase.class);
        purchaseListener.getValue().onIabPurchaseFinished(negativeIabResult(), mockPurchase);
    }

    private void givenSetupFinishedSuccessfully() {
        ArgumentCaptor<OnIabSetupFinishedListener> setupListener = ArgumentCaptor.forClass(OnIabSetupFinishedListener.class);
        verify(mockIabHelper).startSetup(setupListener.capture());
        setupListener.getValue().onIabSetupFinished(positiveIabResult());
    }

    private void givenSetupFinishedWithErrors() {
        ArgumentCaptor<OnIabSetupFinishedListener> setupListener = ArgumentCaptor.forClass(OnIabSetupFinishedListener.class);
        verify(mockIabHelper).startSetup(setupListener.capture());
        setupListener.getValue().onIabSetupFinished(negativeIabResult());        
    }

    private IabResult positiveIabResult() {
        return new IabResult(IabHelper.BILLING_RESPONSE_RESULT_OK, null);
    }

    private IabResult negativeIabResult() {
        return new IabResult(IabHelper.BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE, null);
    }

    @Override
    protected Donation donationUnderTest() {
        mockIabHelper = mock(IabHelper.class);
        return new GoogleDonation(activity, mockIabHelper);        
    }

}

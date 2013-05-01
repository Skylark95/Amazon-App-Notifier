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

import java.util.Arrays;
import java.util.List;

public enum DonationAmount {    
    DONATE_1("donate1_v3", "$1"),
    DONATE_2("donate2_v3", "$2"),
    DONATE_3("donate3_v3", "$3"),
    DONATE_5("donate5_v3", "$5"),
    DONATE_10("donate10_v3", "$10"),
    
    /* Test Amounts */
    ANDROID_TEST_PURCHASED("android.test.purchased", "TEST - Purchased"),
    ANDROID_TEST_CANCELED("android.test.canceled", "TEST - Canceled"),
    ANDROID_TEST_REFUNDED("android.test.refunded", "TEST - Refunded"),
    ANDROID_TEST_ITEM_UNAVAILABLE("android.test.item_unavailable", "TEST - Item Unavailable");
    
    private static final boolean TESTING = false;
    
    private String sku;
    private String displayValue;
    
    private DonationAmount(String sku, String displayValue) {
        this.sku = sku;
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
    
    public String getSku() {
        return sku;
    }
    
    public static List<DonationAmount> donationList() {
        if (TESTING) {
            return Arrays.asList(new DonationAmount[] {ANDROID_TEST_PURCHASED, ANDROID_TEST_CANCELED, ANDROID_TEST_REFUNDED, ANDROID_TEST_ITEM_UNAVAILABLE});
        } else {
            return Arrays.asList(new DonationAmount[] {DONATE_1, DONATE_2, DONATE_3, DONATE_5, DONATE_10});
        }
    }
    
    public static DonationAmount getDonationAmountByDisplayValue(String displayValue) {
        for (DonationAmount donationAmount : donationList()) {
            if (donationAmount.getDisplayValue().equals(displayValue)) {
                return donationAmount;
            }
        }
        return null;
    }    
    
}

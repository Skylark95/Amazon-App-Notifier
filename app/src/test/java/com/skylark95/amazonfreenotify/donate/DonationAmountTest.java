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

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

public class DonationAmountTest {

    @Test
    public void verifyDonationList() {
        List<DonationAmount> donationList = DonationAmount.donationList();
        assertEquals(5, donationList.size());
        assertTrue(donationList.contains(DonationAmount.DONATE_1));
        assertTrue(donationList.contains(DonationAmount.DONATE_2));
        assertTrue(donationList.contains(DonationAmount.DONATE_3));
        assertTrue(donationList.contains(DonationAmount.DONATE_5));
        assertTrue(donationList.contains(DonationAmount.DONATE_10));        
    }

}

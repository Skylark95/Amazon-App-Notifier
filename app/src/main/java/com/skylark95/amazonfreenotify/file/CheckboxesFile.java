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

package com.skylark95.amazonfreenotify.file;

import java.io.Serializable;

/**
 * Contains all boolean values used for checkboxes on the user interface.
 * 
 * @author Derek
 * @since 1.0
 * @deprecated
 */
@Deprecated
public class CheckboxesFile implements Serializable {

	private static final long serialVersionUID = 2328437729905504940L;
	private boolean useInternet;
	private boolean useSound;
	private boolean showAtBoot;
	private boolean showNotifications;
	private boolean vibrate = true;

	/**
	 * Creates a new Checkboxes object.
	 */
	public CheckboxesFile() {
		loadDefaults();
	}

	private void loadDefaults() {
		useInternet = true;
		useSound = true;
		showAtBoot = true;
		showNotifications = false;
	}

	public boolean isUseInternet() {
		return useInternet;
	}

	public void setUseInternet(boolean useInternet) {
		this.useInternet = useInternet;
	}

	public boolean isUseSound() {
		return useSound;
	}

	public void setUseSound(boolean userSound) {
		this.useSound = userSound;
	}

	public boolean isShowAtBoot() {
		return showAtBoot;
	}

	public void setShowAtBoot(boolean showAtBoot) {
		this.showAtBoot = showAtBoot;
	}

	public boolean isShowNotifications() {
		return showNotifications;
	}

	public void setShowNotifications(boolean showNotifications) {
		this.showNotifications = showNotifications;
	}

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

}

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

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

/**
 * Contains all information used to manage basic app checks on startup.
 * 
 * @author Derek
 * @since 1.0
 * @deprecated
 */
@Deprecated
public class StartupFile implements Serializable {

	private static final long serialVersionUID = -7439874756494225205L;
	private String version;
	private boolean firstStart;
	private boolean donate;

	public StartupFile(Context context) {
		loadDefaults(context);
	}

	private void loadDefaults(Context context) {
		firstStart = true;
		donate = false;
		try {
			version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e(StartupFile.class.getSimpleName(), "Error reading version info.");
		}
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isFirstStart() {
		return firstStart;
	}

	public void setFirstStart(boolean firstStart) {
		this.firstStart = firstStart;
	}

	public boolean isDonate() {
		return donate;
	}

	public void setDonate(boolean donate) {
		this.donate = donate;
	}

}

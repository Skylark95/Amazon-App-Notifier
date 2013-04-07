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

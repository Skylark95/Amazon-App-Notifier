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

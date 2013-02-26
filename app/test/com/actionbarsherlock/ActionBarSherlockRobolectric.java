package com.actionbarsherlock;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.internal.ActionBarSherlockNative;
import com.xtremelabs.robolectric.Robolectric;

/**
 * ActionBarSherlockRobolectric
 * 
 * @see https://github.com/JakeWharton/ActionBarSherlock/issues/377
 */
@ActionBarSherlock.Implementation(api = 0)
public class ActionBarSherlockRobolectric extends ActionBarSherlockNative {
	
	public static void registerImplementation() {
		ActionBarSherlock.registerImplementation(ActionBarSherlockRobolectric.class);
	}
	
    public ActionBarSherlockRobolectric(Activity activity, int flags) {
        super(activity, flags);
    }

    @Override 
    public void setContentView(int layoutResId) {
        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        View contentView = layoutInflater.inflate(layoutResId, null);

        Robolectric.shadowOf(mActivity).setContentView(contentView);
    }

    @Override 
    public void setContentView(View view) {
    	Robolectric.shadowOf(mActivity).setContentView(view);
    }
}

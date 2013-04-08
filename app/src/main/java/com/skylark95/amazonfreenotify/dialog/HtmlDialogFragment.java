/*
 * This file is part of Amazon App Notifier
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

package com.skylark95.amazonfreenotify.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.util.HtmlUtil;
import com.skylark95.amazonfreenotify.util.Logger;

public class HtmlDialogFragment extends SherlockDialogFragment {

	private static final String KEY_TITLE = "title";
	private static final String KEY_HTML = "html";
	private static final int TEXT_SIZE = 15;
	private static final int PADDING = 20;
	
	private static final String TAG = Logger.getTag(HtmlDialogFragment.class);
	
	public static HtmlDialogFragment newInstance(int title, int html) {
		HtmlDialogFragment dialog = new HtmlDialogFragment();
		
		Bundle args = new Bundle();
		args.putInt(KEY_TITLE, title);
		args.putInt(KEY_HTML, html);
		dialog.setArguments(args);
		return dialog;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String html = getSherlockActivity().getString(getArguments().getInt(KEY_HTML));
		TextView textView = new TextView(getSherlockActivity());
		textView.setTextSize(TEXT_SIZE);
		textView.setPadding(PADDING, PADDING, PADDING, PADDING);
		
		HtmlUtil.createHtmlView(getSherlockActivity(), textView, html);
		Log.v(TAG, "Created html view for dialog");
		
		return new AlertDialog.Builder(getSherlockActivity())
		.setTitle(getArguments().getInt(KEY_TITLE))
		.setView(textView)
		.setPositiveButton(R.string.close_button, null)
		.create();
	}

}

package com.skylark95.amazonfreenotify.dialog;

import java.io.IOException;

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

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String html = getSherlockActivity().getResources().getString(getArguments().getInt(KEY_HTML));
		TextView textView = new TextView(getSherlockActivity());
		textView.setTextSize(TEXT_SIZE);
		textView.setPadding(PADDING, PADDING, PADDING, PADDING);
		
		try {
			HtmlUtil.createHtmlView(getSherlockActivity(), textView, html);
		} catch (IOException e) {
			Log.e(TAG, "Could not create html view for dialog", e);
		}
		
		return new AlertDialog.Builder(getSherlockActivity())
		.setTitle(getArguments().getInt(KEY_TITLE))
		.setView(textView)
		.setPositiveButton(R.string.close_button, null)
		.create();
	}

}

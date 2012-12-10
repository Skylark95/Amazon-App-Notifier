package com.skylark95.amazonfreenotify.ui.dialog;

import java.io.IOException;
import java.io.InputStream;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.ui.tabs.AboutFragment;
import com.skylark95.amazonfreenotify.ui.util.HtmlUtil;
import com.skylark95.amazonfreenotify.util.Logger;

public class ChangelogFragment extends SherlockDialogFragment {

	public static final String TAG = Logger.getTag(ChangelogFragment.class);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.v(TAG, "ENTER - onCreateView()");
		View dialogView = inflater.inflate(R.layout.fragment_dialog, container, false);
		setHtmlText(dialogView);
		setupButtons(dialogView);
		Log.v(TAG, "EXIT - onCreateView()");
		return dialogView;
	}

	private void setupButtons(View dialogView) {
		Button button = (Button) dialogView.findViewById(R.id.dialog_close_button);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	private void setHtmlText(View view) {
		Log.v(TAG, "Creating about_html view");
		TextView changelogTextView = (TextView) view.findViewById(R.id.dialog_text);
		InputStream in = getActivity().getResources().openRawResource(R.raw.html_changelog);
		try {
			HtmlUtil.createHtmlView(getActivity(), changelogTextView, in);
		} catch (IOException e) {
			Log.e(TAG, "Could not read html file");
		}
	}

}

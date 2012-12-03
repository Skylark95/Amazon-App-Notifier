package com.skylark95.amazonfreenotify.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.skylark95.amazonfreenotify.R;

public class ChangelogFragment extends SherlockDialogFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View dialogView = inflater.inflate(R.layout.fragment_dialog, container, false);
		
		Button button = (Button) dialogView.findViewById(R.id.close_button);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});

		return dialogView;
	}

}

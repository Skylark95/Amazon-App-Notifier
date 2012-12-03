package com.skylark95.amazonfreenotify.ui.tabs;

import java.io.IOException;
import java.io.InputStream;

import net.nightwhistler.htmlspanner.HtmlSpanner;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.skylark95.amazonfreenotify.R;

public class AboutFragment extends SherlockFragment {
	
	private static final String TAG = AboutFragment.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_about, container, false);
		setVersionText(view);		
		setHtmlText(view);
		
		return view;
	}

	private void setHtmlText(View view) {
		TextView aboutTextView = (TextView) view.findViewById(R.id.about_html);		
		InputStream in = getActivity().getResources().openRawResource(R.raw.html_about);		
		HtmlSpanner htmlSpanner = new HtmlSpanner();
		htmlSpanner.setStripExtraWhiteSpace(true);
		try {
			aboutTextView.setText(htmlSpanner.fromHtml(in));
		} catch (IOException e) {
			Log.e(TAG, "Error reading raw html file.");
		}
		
		aboutTextView.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private void setVersionText(View view) {
		TextView versionTextView = (TextView) view.findViewById(R.id.about_app_version);
		versionTextView.setText(getString(R.string.app_version_label) + " " + getVersion());
	}
	
	private String getVersion() {
        String result = "";
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);

            result = info.versionName;
        } catch (NameNotFoundException e) {
            Log.w(TAG, "Unable to get application version: " + e.getMessage());
            result = "N/A";
        }

        return result;
    }

}

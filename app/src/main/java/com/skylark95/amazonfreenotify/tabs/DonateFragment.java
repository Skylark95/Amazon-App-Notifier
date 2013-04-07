
package com.skylark95.amazonfreenotify.tabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;
import com.skylark95.amazonfreenotify.R;

public class DonateFragment extends SherlockFragment {

    private static final String PAID_APP_URL = "https://play.google.com/store/apps/details?id=com.skylark95.amazonnotify";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate, container, false);
        setupButtons(view);
        return view;
    }

    private void setupButtons(View view) {
        Button button = (Button) view.findViewById(R.id.donate_button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent paidAppUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(PAID_APP_URL));
                startActivity(paidAppUrl);
            }            
        });
    }
}

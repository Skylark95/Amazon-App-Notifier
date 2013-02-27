package com.skylark95.amazonfreenotify.util;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public final class HtmlUtil {

	private HtmlUtil() {
	}

	public static void createHtmlView(Context context, TextView textView, String html) {
		textView.setText(Html.fromHtml(html));
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		textView.setTextColor(context.getResources().getColor(android.R.color.secondary_text_dark_nodisable));
	}


}

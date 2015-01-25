package com.skylark95.amazonfreenotify.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.ListPreference;
import android.util.AttributeSet;

public class IconListPreference extends ListPreference {

    public IconListPreference(Context context) {
        super(context);
        setup();
    }

    public IconListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        Context context = getContext();
        Icon[] icons = Icon.values();
        CharSequence[] entries = new CharSequence[icons.length];
        CharSequence[] entryValues = new CharSequence[icons.length];
        for (int i = 0; i < icons.length; i++) {
            Icon icon = icons[i];
            entries[i] = icon.toString(context);
            entryValues[i] = icon.name();
        }
        setEntries(entries);
        setEntryValues(entryValues);
        setDefaultValue(Icon.defaultValue().name());
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            setSummary(getSummary());
        }
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        super.onSetInitialValue(restoreValue, defaultValue);
        setSummary(getSummary());
    }

    @Override
    public String getSummary() {
        Icon icon = Icon.valueOf(getPersistedString(Icon.APP.name()));
        return icon.toString(getContext());
    }

}

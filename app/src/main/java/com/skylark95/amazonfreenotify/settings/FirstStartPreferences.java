
package com.skylark95.amazonfreenotify.settings;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.skylark95.amazonfreenotify.AmazonAppNotifier;
import com.skylark95.amazonfreenotify.alarm.FreeAppNotificationListener;
import com.skylark95.amazonfreenotify.tabs.ButtonMenuActions;
import com.skylark95.amazonfreenotify.util.Logger;

public final class FirstStartPreferences {

    public static final String PREF_IS_FIRST_START = "_is_first_start";

    private static final String TAG = Logger.getTag(FirstStartPreferences.class);

    private FirstStartPreferences() {
    }

    public static boolean isFirstStart(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_IS_FIRST_START, Context.MODE_PRIVATE);
        boolean firstStart = pref.getBoolean(PREF_IS_FIRST_START, true);

        if (firstStart) {
            Log.i(TAG, "App First Start");
        }

        return firstStart;
    }

    public static class FirstStartTask extends AsyncTask<Void, String, Void> {

        private static final String PERFORMING_FIRST_START = "Performing First Start";
        private AmazonAppNotifier context;
        private SharedPreferences pref;
        private ProgressDialog progressDialog;

        public FirstStartTask(AmazonAppNotifier context) {
            this.context = context;
            pref = context.getSharedPreferences(PREF_IS_FIRST_START, Context.MODE_PRIVATE);
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle(PERFORMING_FIRST_START);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Starting up...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            convertPrefrences();
            scheduleAlarms();
            completeFirstStart();
            return null;
        }
        
        @Override
        protected void onProgressUpdate(String... messages) {
            progressDialog.setMessage(messages[0]);
        }

        private void scheduleAlarms() {
            String message = "Scheduling alarms...";
            publishProgress(message);
            Log.i(TAG, message);
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            if (sharedPref.getBoolean(Preferences.PREF_ENABLED, true)) {
                Log.i(TAG, "Schedule alarms for first time");
                WakefulIntentService.scheduleAlarms(new FreeAppNotificationListener(), context);
            } else {
                Log.i(TAG, "Skip alarms for first time");
            }
        }

        private void convertPrefrences() {
            String message = "Converting settings...";
            publishProgress(message);
            Log.i(TAG, message);
            LegacyPreferencesConverter converter = LegacyPreferencesConverter.getInstance();
            if (converter.isUpdateFromV1(context)) {
                converter.convert(context);
            }
        }

        private void completeFirstStart() {
            String message = "Almost done...";
            publishProgress(message);
            Log.i(TAG, message);
            Editor editor = pref.edit();
            editor.putBoolean(PREF_IS_FIRST_START, false);
            boolean result = editor.commit();

            if (!result) {
                Log.e(TAG, "Could not update first start preference");
            }
        }

        @Override
        protected void onPostExecute(Void unused) {
            String message = "Restarting... ";
            Log.i(TAG, message);
            progressDialog.dismiss();
            context.reloadAndShowChangeLog();
        }

    }

}

package xyz.miroslaw.gamification_android.database;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;


public class DatabaseManager {

    private static DatabaseHelper databaseHelper = null;

    public static DatabaseHelper getHelper(Context context) {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }

        return databaseHelper;
    }

    public static void releaseHelper() {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
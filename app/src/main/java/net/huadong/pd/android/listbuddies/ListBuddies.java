package net.huadong.pd.android.listbuddies;

import android.app.Application;
import android.content.Context;

public class ListBuddies extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }
}

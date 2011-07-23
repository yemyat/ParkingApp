package sg.srcode.xtremeapp;


import android.content.Intent;
import android.net.Uri;
import greendroid.app.GDApplication;

public class XtremeApp extends GDApplication {
    @Override
    public Class<?> getHomeActivityClass() {
        return DashboardActivity.class;
    }
}

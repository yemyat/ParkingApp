package sg.srcode.xtremeapp;


import greendroid.app.GDApplication;

public class XtremeApp extends GDApplication {
    @Override
    public Class<?> getHomeActivityClass() {
        return DashboardActivity.class;
    }
}

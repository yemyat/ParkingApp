package sg.srcode.xtremeapp;

import android.os.Bundle;
import greendroid.app.GDActivity;
import greendroid.widget.GDActionBarItem.Type;

public class HomeActivity extends GDActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setGDActionBarContentView(R.layout.home_activity);
        addActionBarItem(Type.Refresh, R.id.action_bar_refresh);
    }
}

package sg.srcode.xtremeapp.activity;

import android.os.Bundle;
import greendroid.app.GDActivity;
import sg.srcode.xtremeapp.DashboardActivity;
import sg.srcode.xtremeapp.R;

/**
 * Created by IntelliJ IDEA.
 * User: jeff
 * Date: 22/7/11
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class DirectionActivity extends GDActivity {
@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setGDActionBarContentView(R.layout.direction_activity);
    }

}

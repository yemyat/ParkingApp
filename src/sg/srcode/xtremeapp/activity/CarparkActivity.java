package sg.srcode.xtremeapp.activity;

import android.os.Bundle;
import greendroid.app.GDActivity;
import sg.srcode.xtremeapp.R;

public class CarparkActivity extends GDActivity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setGDActionBarContentView(R.layout.carpark_activity);
    }
}

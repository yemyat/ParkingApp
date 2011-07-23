package sg.srcode.xtremeapp.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import greendroid.app.GDActivity;
import org.w3c.dom.Element;
import sg.srcode.xtremeapp.DashboardActivity;
import sg.srcode.xtremeapp.R;
import sg.srcode.xtremeapp.utils.LocationUtils;

public class DirectionActivity extends GDActivity {
    private EditText sourceTxt;
    private Button confirmBtn;
    private Button currLocBtn;

    private LocationManager mLocationManager;
    private Location mCurrentLocation;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setGDActionBarContentView(R.layout.direction_activity);

        sourceTxt = (EditText) findViewById(R.id.sourceText);
        confirmBtn = (Button) findViewById(R.string.confirmBtn);
        currLocBtn = (Button) findViewById(R.string.getCurrLocationBtn);

        mLocationManager  = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        mCurrentLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

       LocationUtils lu = new LocationUtils();
        lu.init(getBaseContext(), locationResult);


    }

    public LocationUtils.LocationResult locationResult = new LocationUtils.LocationResult() {
        @Override
        public void gotLocation(final Location location) {
            mCurrentLocation.setLatitude(location.getLatitude());
            mCurrentLocation.setLongitude(location.getLongitude());
        }
    };

    public void onConfirmSelection(View view) {
        if (sourceTxt.getText().toString().equalsIgnoreCase("")) {
            String errorMsg = "Please select your source address!";
            Toast errorNotification = Toast.makeText(this, errorMsg, Toast.LENGTH_LONG);
            errorNotification.setGravity(Gravity.CENTER, errorNotification.getXOffset() / 2, errorNotification.getYOffset() / 2);
            errorNotification.show();
        }
        else {
            Intent intent = new Intent(DirectionActivity.this, DestinationAddActivity.class);
            intent.putExtra("sourceAdd", sourceTxt.getText().toString());
            DirectionActivity.this.startActivity(intent);
        }
    }

    public void onGetCurrLocation(View view) {
         Intent intent = new Intent(DirectionActivity.this, DestinationAddActivity.class);
         intent.putExtra("latitude", mCurrentLocation.getLatitude());
         intent.putExtra("longitude", mCurrentLocation.getLongitude());
         DirectionActivity.this.startActivity(intent);

    }
}

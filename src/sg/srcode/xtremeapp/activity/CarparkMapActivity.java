package sg.srcode.xtremeapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import greendroid.app.GDActivity;
import greendroid.widget.GDActionBarItem;
import greendroid.widget.LoaderActionBarItem;
import sg.srcode.xtremeapp.R;

public class CarparkMapActivity extends GDActivity implements LocationListener {
    private static final String MAP_URL = "http://117.120.6.182/index.php";

    private WebView mWebView;

    private Location mCurrentLocation;
    private Location mDestinationLocation;

    private LoaderActionBarItem loaderItem;

    private AlertDialog myAlertDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGDActionBarContentView(R.layout.carpark_map_activity);
        addActionBarItem(GDActionBarItem.Type.Refresh, R.id.action_bar_refresh);

        loaderItem = ((LoaderActionBarItem) getGDActionBar().getItem(0));

        setTitle("To " + getIntent().getStringExtra("development"));

        mDestinationLocation = new Location("dummyprovider");
        mDestinationLocation.setLatitude(Double.parseDouble(getIntent().getStringExtra("latitude")));
        mDestinationLocation.setLongitude(Double.parseDouble(getIntent().getStringExtra("longitude")));
        getLocation();
        setupWebView();
    }

    @Override
    public boolean onHandleActionBarItemClick(GDActionBarItem item, int position) {
        switch (item.getItemId()) {
            case R.id.action_bar_refresh:
                updateWebView();
                break;

            default:
                return super.onHandleActionBarItemClick(item, position);
        }
        return true;
    }


    /**
     * Sets up the WebView object and loads the URL of the page *
     */
    private void setupWebView() {
        loaderItem.setLoading(true);

        mWebView = (WebView) findViewById(R.id.wv_carpark_map);
        mWebView.getSettings().setJavaScriptEnabled(true);
        //Wait for the page to load then send the location information
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(MAP_URL);
        /** Allows JavaScript calls to access application resources **/
        mWebView.addJavascriptInterface(new JavaScriptInterface(), "android");
    }

    public void updateWebView() {
        loaderItem.setLoading(true);
        final String placeDestinationURL = "javascript:routeTo(" +
                mCurrentLocation.getLatitude() + "," +
                mCurrentLocation.getLongitude() + ", " +
                mDestinationLocation.getLatitude() + "," +
                mDestinationLocation.getLongitude() + ")";
        mWebView.loadUrl(placeDestinationURL);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                loaderItem.setLoading(false);
            }
        });
    }

    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        updateWebView();
    }

    public void onStatusChanged(String s, int i, Bundle bundle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onProviderEnabled(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onProviderDisabled(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Sets up the interface for getting access to Latitude and Longitude data from device
     */
    private class JavaScriptInterface {
        public double getLatitude() {
            return mCurrentLocation.getLatitude();
        }

        public double getLongitude() {
            return mCurrentLocation.getLongitude();
        }

        public void setAddress(String address) {
            Log.d("XtemeApp", address);
        }

        public void noRouteFound() {
            showDialog(getApplicationContext(), "Error", "No routes found!");
        }

        public void routingFinished() {
            loaderItem.setLoading(true);

        }
    }

    private void getLocation() {
        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);
        //In order to make sure the device is getting the location, request updates.
        locationManager.requestLocationUpdates(provider, 1, 0, this);
        mCurrentLocation = locationManager.getLastKnownLocation(provider);
    }

        public void showDialog(Context context, String title, String message) {
        if( myAlertDialog == null || myAlertDialog.isShowing() ) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    dialog.dismiss();
                }});
        builder.setCancelable(false);
        myAlertDialog = builder.create();
        myAlertDialog.show();
    }

}

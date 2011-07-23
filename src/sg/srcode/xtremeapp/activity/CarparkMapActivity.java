package sg.srcode.xtremeapp.activity;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import greendroid.app.GDActivity;
import sg.srcode.xtremeapp.R;

public class CarparkMapActivity extends GDActivity implements LocationListener {
    private static final String MAP_URL = "http://117.120.6.182/index.php";

    private WebView mWebView;

    private Location mCurrentLocation;
    private Location mDestinationLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGDActionBarContentView(R.layout.carpark_map_activity);

        setTitle(getIntent().getStringExtra("development"));

        mDestinationLocation = new Location("dummyprovider");
        mDestinationLocation.setLatitude(Double.parseDouble(getIntent().getStringExtra("latitude")));
        mDestinationLocation.setLongitude(Double.parseDouble(getIntent().getStringExtra("longitude")));
        getLocation();
        setupWebView();


    }

    /**
     * Sets up the WebView object and loads the URL of the page *
     */
    private void setupWebView() {
        final String placeDestinationURL = "javascript:routeTo(" +
                mCurrentLocation.getLatitude() + "," +
                mCurrentLocation.getLongitude() + ", " +
                mDestinationLocation.getLatitude() + "," +
                mDestinationLocation.getLongitude() + ")";
        mWebView = (WebView) findViewById(R.id.wv_carpark_map);
        mWebView.getSettings().setJavaScriptEnabled(true);
        //Wait for the page to load then send the location information
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(MAP_URL);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mWebView.loadUrl(placeDestinationURL);
            }
        });
        /** Allows JavaScript calls to access application resources **/
        mWebView.addJavascriptInterface(new JavaScriptInterface(), "android");
    }

    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
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


}

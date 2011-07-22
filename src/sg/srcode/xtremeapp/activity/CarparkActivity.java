package sg.srcode.xtremeapp.activity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;
import greendroid.app.GDActivity;
import greendroid.widget.GDActionBarItem;
import greendroid.widget.LoaderActionBarItem;
import greendroid.widget.GDActionBarItem.Type;
import sg.srcode.xtremeapp.R;
import sg.srcode.xtremeapp.adapter.CarparkAdapter;
import sg.srcode.xtremeapp.adapter.SectionedAdapter;
import sg.srcode.xtremeapp.connection.NimbusServer;
import sg.srcode.xtremeapp.item.CarparkItem;
import sg.srcode.xtremeapp.utils.LocationUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CarparkActivity extends GDActivity {

    private NimbusServer mServer;
    private LoaderActionBarItem loaderItem;
    private ArrayList<CarparkItem> mItems;

    private ListView mListView;
    private SectionedAdapter mSectionedAdapter;

    private LocationManager mLocationManager;

    private final String[] LIST_HEADERS = {"High", "Medium", "Low"};


    private Location mCurrentLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGDActionBarContentView(R.layout.carpark_activity);
        addActionBarItem(Type.Refresh, R.id.action_bar_refresh);

        mItems = new ArrayList<CarparkItem>();

        loaderItem = ((LoaderActionBarItem) getGDActionBar().getItem(0));
        mListView = (ListView) findViewById(R.id.lv_carpark);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mCurrentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        mSectionedAdapter = new SectionedAdapter() {
            protected View getHeaderView(String caption, int index, int count, View convertView, ViewGroup parent) {
                TextView result = (TextView) convertView;

                if (convertView == null) {
                    result = (TextView) getLayoutInflater().inflate(R.layout.list_section_header, null);
                }
                result.setText(caption);

                return (result);
            }
        };

        //Initialize a server instance
        mServer = new NimbusServer(this);

        LocationUtils lu = new LocationUtils();
        lu.init(getBaseContext(), locationResult);

        //Initial retrieval of data
        reloadTask();
    }

    @Override
    public boolean onHandleActionBarItemClick(GDActionBarItem item, int position) {
        switch (item.getItemId()) {
            case R.id.action_bar_refresh:
                reloadTask();
                break;

            default:
                return super.onHandleActionBarItemClick(item, position);
        }
        return true;
    }


    public void reloadData() {

        this.mItems = mServer.getNearbyCarparks(this.mCurrentLocation.getLatitude(), this.mCurrentLocation.getLongitude(), 50);
        this.mSectionedAdapter.removeAllSections();

        for (String header : LIST_HEADERS) {
            ArrayList<CarparkItem> temp = new ArrayList<CarparkItem>();
            for (CarparkItem item : mItems) {
                if (item.getmAvailability().equalsIgnoreCase(header)) {
                    temp.add(item);
                }
            }
            if(temp.size() > 0) {
                this.mSectionedAdapter.addSection("Availability : " + header, new CarparkAdapter(getBaseContext(), temp));
            }
        }

    }

    public void reloadTask() {
        RetrieveDataTask task = new RetrieveDataTask(); //Create a new task
        task.execute("RetrieveData");
    }

    private class RetrieveDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            Looper.prepare();
            reloadData();
            return "";   //Fake response
        }

        @Override
        protected void onPreExecute() {
            loaderItem.setLoading(true);
        }

        @Override
        protected void onPostExecute(String result) {
            mListView.setAdapter(mSectionedAdapter);
            loaderItem.setLoading(false);
        }
    }

    public LocationUtils.LocationResult locationResult = new LocationUtils.LocationResult(){
        @Override
        public void gotLocation(final Location location) {
            // do something
            mCurrentLocation.setLongitude(location.getLongitude());
            mCurrentLocation.setLatitude(location.getLatitude());
            reloadTask();
        }
    };

}

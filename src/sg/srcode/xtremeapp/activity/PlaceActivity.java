package sg.srcode.xtremeapp.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import greendroid.app.GDActivity;
import greendroid.widget.GDActionBarItem;
import greendroid.widget.GDActionBarItem.Type;
import greendroid.widget.LoaderActionBarItem;
import greendroid.widget.QuickActionWidget;
import sg.srcode.xtremeapp.R;
import sg.srcode.xtremeapp.adapter.SectionedAdapter;
import sg.srcode.xtremeapp.connection.NimbusServer;
import sg.srcode.xtremeapp.item.PlaceItem;
import sg.srcode.xtremeapp.utils.LocationUtils;

import java.util.ArrayList;
import java.util.HashSet;

public class PlaceActivity extends GDActivity implements AdapterView.OnItemClickListener {
    private NimbusServer mServer;
    private LoaderActionBarItem loaderItem;

    private ArrayList<PlaceItem> mItems;

    private ListView mListView;
    private SectionedAdapter mSectionedAdapter;
    private QuickActionWidget mBar;

    private LocationManager mLocationManager;
    private Location mCurrentLocation;

    private int mListItemPosition;

    private ArrayList<String> mCategories = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGDActionBarContentView(R.layout.place_activity);
        addActionBarItem(Type.Refresh, R.id.action_bar_refresh);

        //prepareQuickActionBar();

        mItems = new ArrayList<PlaceItem>();

        loaderItem = ((LoaderActionBarItem) getGDActionBar().getItem(0));
        mListView = (ListView) findViewById(R.id.lv_place);

        mSectionedAdapter = new SectionedAdapter() {
            @Override
            protected View getHeaderView(String caption, int index, int count, View convertView, ViewGroup parent) {
                TextView result = (TextView) convertView;

                if (convertView == null) {
                    result = (TextView) getLayoutInflater().inflate(R.layout.list_section_header, null);
                }
                result.setText(caption);
                return result;
            }
        };

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mCurrentLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        //Initialize a server instance
        mServer = new NimbusServer(this);

        LocationUtils lu = new LocationUtils();
        lu.init(getBaseContext(), locationResult);

        mListView.setOnItemClickListener(this);

        reloadTask();


    }


    @Override
    public boolean onHandleActionBarItemClick(GDActionBarItem item, int position) {
        switch (item.getItemId()) {
            case R.id.action_bar_refresh:
                final LoaderActionBarItem loaderItem = (LoaderActionBarItem) item;
                reloadData();
                break;
            default:
                return super.onHandleActionBarItemClick(item, position);
        }
        return true;
    }

    public void reloadData() {
        this.mItems = null;
        this.mItems = mServer.getNearbyPlaces(this.mCurrentLocation.getLatitude(), this.mCurrentLocation.getLongitude(), 50);
        this.mSectionedAdapter.removeAllSections();


        for (PlaceItem item : mItems) {
            if (!item.getmCategory().equals(""))
                mCategories.add(item.getmCategory().replaceFirst("&amp;", "&"));
        }
        removeCategoryDuplicates();

        this.mSectionedAdapter.addSection("Categories", new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, mCategories));

    }

    private void removeCategoryDuplicates() {
        HashSet<String> hs = new HashSet<String>();
        hs.addAll(mCategories);
        mCategories.clear();
        mCategories.addAll(hs);
    }

    public void reloadTask() {
        RetrieveDataTask task = new RetrieveDataTask(); //Create a new task
        task.execute("RetrieveData");
    }

    private class RetrieveDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
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

    public LocationUtils.LocationResult locationResult = new LocationUtils.LocationResult() {
        @Override
        public void gotLocation(final Location location) {
            // do something
            mCurrentLocation.setLongitude(location.getLongitude());
            mCurrentLocation.setLatitude(location.getLatitude());
            reloadTask();
        }
    };

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mListItemPosition = i;
        Intent intent = new Intent(getBaseContext(), CategoryActivity.class);
        intent.putExtra("category_name", mCategories.get(mListItemPosition - 1));
        intent.putExtra("categories", mCategories);
        intent.putExtra("latitude", this.mCurrentLocation.getLatitude());
        intent.putExtra("longitude", this.mCurrentLocation.getLongitude());
        intent.putExtra("distance", 2000.0);
        Toast.makeText(getApplicationContext(), "" + mCategories.get(mListItemPosition - 1), Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }

}

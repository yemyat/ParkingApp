package sg.srcode.xtremeapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import greendroid.app.GDActivity;
import greendroid.widget.*;
import greendroid.widget.GDActionBarItem.Type;
import sg.srcode.xtremeapp.R;
import sg.srcode.xtremeapp.adapter.PlaceAdapter;
import sg.srcode.xtremeapp.adapter.SectionedAdapter;
import sg.srcode.xtremeapp.connection.NimbusServer;
import sg.srcode.xtremeapp.item.PlaceItem;
import sg.srcode.xtremeapp.utils.LocationUtils;

import java.util.ArrayList;

public class PlaceActivity extends GDActivity implements AdapterView.OnItemClickListener  {
    private NimbusServer mServer;
    private LoaderActionBarItem loaderItem;

    private ArrayList<PlaceItem> mItems;
    private ListView mListView;
    private QuickActionWidget mBar;
    private SectionedAdapter mSectionedAdapter;

    private String[] LIST_HEADERS = {"Point of Interests"};


    private LocationManager mLocationManager;
    private Location mCurrentLocation;

    private PlaceItem mCheckedItem;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setGDActionBarContentView(R.layout.place_activity);
        addActionBarItem(Type.Refresh, R.id.action_bar_refresh);

        prepareQuickActionBar();

        mItems = new ArrayList<PlaceItem>();
        loaderItem = ((LoaderActionBarItem) getGDActionBar().getItem(0));
        mListView = (ListView) findViewById(R.id.lv_place);

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

         LocationUtils lu = new LocationUtils();
        lu.init(getBaseContext(), locationResult);

        mServer = new NimbusServer(this);
        mListView.setOnItemClickListener(this);
        reloadTask();

    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mCheckedItem = (PlaceItem) mSectionedAdapter.getItem(i);
        onShowBar(view);
    }
    public void onShowBar(View v) {
        mBar.show(v);
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

     private void prepareQuickActionBar() {
        mBar = new QuickActionBar(this);
        mBar.addQuickAction(new MyQuickAction(this, R.drawable.gd_action_bar_edit, R.string.place_info));
        mBar.addQuickAction(new MyQuickAction(this, R.drawable.gd_action_bar_compass, R.string.place_map));
        mBar.addQuickAction(new MyQuickAction(this, R.drawable.gd_action_bar_talk, R.string.place_call));
        mBar.addQuickAction(new MyQuickAction(this, R.drawable.gd_action_bar_share, R.string.place_url));

        mBar.setOnQuickActionClickListener(mActionListener);
    }

    private static class MyQuickAction extends QuickAction {

        private static final ColorFilter BLACK_CF = new LightingColorFilter(Color.BLACK, Color.BLACK);

        public MyQuickAction(Context ctx, int drawableId, int titleId) {
            super(ctx, buildDrawable(ctx, drawableId), titleId);
        }

        private static Drawable buildDrawable(Context ctx, int drawableId) {
            Drawable d = ctx.getResources().getDrawable(drawableId);
            d.setColorFilter(BLACK_CF);
            return d;
        }

    }

    private QuickActionWidget.OnQuickActionClickListener mActionListener = new QuickActionWidget.OnQuickActionClickListener() {
        public void onQuickActionClicked(QuickActionWidget widget, int position) {

            switch (position) {
                case 0:
                    //Info
                    //intent.setClass(getBaseContext(), ChargeCalculatorActivity.class);
                    break;
                case 1:
                    //Map

                    break;
                case 2:
                    Intent intent = new Intent(Intent.AsCTION_DIAL);
                    String stringURL = mCheckedItem.getmTel();
                    intent.setData(Uri.parse(stringURL));
                    startActivity(intent);
                    break;
                case 3:
                    break;
                default:
                   //Do nothing for now
            }
           // startActivity(intent);
        }
    };

        public LocationUtils.LocationResult locationResult = new LocationUtils.LocationResult(){
        @Override
        public void gotLocation(final Location location) {
            // do something
            mCurrentLocation.setLongitude(location.getLongitude());
            mCurrentLocation.setLatitude(location.getLatitude());
            reloadTask();
        }
    };

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
            mSectionedAdapter.notifyDataSetChanged();
            loaderItem.setLoading(false);
        }
    }

    public void reloadData() {
        this.mItems = null;
        this.mItems = mServer.getNearbyPlaces(this.mCurrentLocation.getLatitude(), this.mCurrentLocation.getLongitude(), 1000);
        this.mSectionedAdapter.removeAllSections();

        for(String header : LIST_HEADERS) {
            ArrayList<PlaceItem> temp = new ArrayList<PlaceItem>();
            for(PlaceItem item: mItems) {
                temp.add(item);
            }

            if(temp.size() > 0) {
                Log.d("POI", temp.toString());
                this.mSectionedAdapter.addSection(header, new PlaceAdapter(getBaseContext(), temp));
                Log.d("count", ""+new PlaceAdapter(getBaseContext(), temp).getCount());
            }
        }

    }

}

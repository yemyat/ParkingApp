package sg.srcode.xtremeapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import greendroid.app.GDActivity;
import greendroid.widget.*;
import greendroid.widget.GDActionBarItem.Type;
import sg.srcode.xtremeapp.R;
import sg.srcode.xtremeapp.adapter.CarparkAdapter;
import sg.srcode.xtremeapp.adapter.SectionedAdapter;
import sg.srcode.xtremeapp.connection.NimbusServer;
import sg.srcode.xtremeapp.item.CarparkItem;
import sg.srcode.xtremeapp.utils.LocationUtils;

import java.util.ArrayList;

public class CarparkActivity extends GDActivity implements AdapterView.OnItemClickListener {

    private NimbusServer mServer;
    private LoaderActionBarItem loaderItem;
    private ArrayList<CarparkItem> mItems;

    private ListView mListView;
    private QuickActionWidget mBar;

    private SectionedAdapter mSectionedAdapter;

    private LocationManager mLocationManager;

    private final String[] LIST_HEADERS = {"High", "Medium", "Low"};
    private final String[] LIST_SCALE = {"> 250", "100 - 250", "< 100"};

    private Location mCurrentLocation;

    private CarparkItem mCheckedItem;

    private AlertDialog myAlertDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGDActionBarContentView(R.layout.carpark_activity);
        addActionBarItem(Type.Refresh, R.id.action_bar_refresh);

        prepareQuickActionBar();

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

        mListView.setOnItemClickListener(this);

        //Initial retrieval of data
        reloadTask();
    }


    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mCheckedItem = (CarparkItem) mSectionedAdapter.getItem(i);
        onShowBar(view);
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
        this.mItems = null;
        this.mItems = mServer.getNearbyCarparks(this.mCurrentLocation.getLatitude(), this.mCurrentLocation.getLongitude(), 50);
        if (this.mItems != null) {
            this.mSectionedAdapter.removeAllSections();
            int i = 0;
            for (String header : LIST_HEADERS) {
                ArrayList<CarparkItem> temp = new ArrayList<CarparkItem>();
                for (CarparkItem item : mItems) {
                    if (item.getmAvailability().equalsIgnoreCase(header)) {
                        temp.add(item);
                    }
                }
                if (temp.size() > 0) {
                    this.mSectionedAdapter.addSection("Availability : " + header + " (" + LIST_SCALE[i] + ")", new CarparkAdapter(getBaseContext(), temp));
                }
                i++;
            }
        } else {
            showDialog(getApplicationContext(),"Error","Cannot connect to the server");
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

    public LocationUtils.LocationResult locationResult = new LocationUtils.LocationResult() {
        @Override
        public void gotLocation(final Location location) {
            // do something
            mCurrentLocation.setLongitude(location.getLongitude());
            mCurrentLocation.setLatitude(location.getLatitude());
            reloadTask();
        }
    };

    public void onShowBar(View v) {
        mBar.show(v);
    }

    private void prepareQuickActionBar() {
        mBar = new QuickActionBar(this);
        mBar.addQuickAction(new MyQuickAction(this, R.drawable.calculator, R.string.carpark_calculate));
        mBar.addQuickAction(new MyQuickAction(this, R.drawable.maps, R.string.carpark_map));

        mBar.setOnQuickActionClickListener(mActionListener);
    }

    private QuickActionWidget.OnQuickActionClickListener mActionListener = new QuickActionWidget.OnQuickActionClickListener() {
        public void onQuickActionClicked(QuickActionWidget widget, int position) {
            Intent intent = new Intent();
            intent = attachInfoForItem(mCheckedItem, intent);
            switch (position) {
                case 0:
                    //Calculator
                    intent.setClass(getBaseContext(), ChargeCalculatorActivity.class);
                    break;
                case 1:
                    //Map

                    break;
                default:
                    //Do nothing for now
            }
            startActivity(intent);
        }
    };

    private static class MyQuickAction extends QuickAction {

        public MyQuickAction(Context ctx, int drawableId, int titleId) {
            super(ctx, buildDrawable(ctx, drawableId), titleId);
        }

        private static Drawable buildDrawable(Context ctx, int drawableId) {
            Drawable d = ctx.getResources().getDrawable(drawableId);
            return d;
        }

    }

    private Intent attachInfoForItem(CarparkItem item, Intent intent) {

        intent.putExtra("baseCharge", item.getmBaseCharge());
        intent.putExtra("development", item.getmDevelopment());

        return intent;
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

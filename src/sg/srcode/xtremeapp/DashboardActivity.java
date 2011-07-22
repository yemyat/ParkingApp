package sg.srcode.xtremeapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import greendroid.app.GDActivity;
import greendroid.widget.GDActionBarItem.Type;
import org.apache.commons.logging.Log;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import sg.srcode.xtremeapp.adapter.DashboardAdapter;
import sg.srcode.xtremeapp.item.DashboardItem;

import java.util.ArrayList;

public class DashboardActivity extends GDActivity
{
    /* UI Elements */
    private GridView mGridView;
    private DashboardAdapter mDashboardAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setGDActionBarContentView(R.layout.dashboard_activity);

        /* Hide the home button */
        getGDActionBar().findViewById(R.id.gd_action_bar_home_item).setVisibility(View.GONE);
        ((ViewGroup) getGDActionBar().findViewById(R.id.gd_action_bar_home_item).getParent()).getChildAt(1).setVisibility(View.GONE);

        /* Items for Dashboard */
        ArrayList<DashboardItem> dashboardItems = new ArrayList<DashboardItem>();
        dashboardItems.add(new DashboardItem("Directions", getResources().getDrawable(R.drawable.icon)));
        dashboardItems.add(new DashboardItem("Item #2", getResources().getDrawable(R.drawable.icon)));
        dashboardItems.add(new DashboardItem("Item #3", getResources().getDrawable(R.drawable.icon)));
        dashboardItems.add(new DashboardItem("Item #4", getResources().getDrawable(R.drawable.icon)));
        dashboardItems.add(new DashboardItem("Nearby", getResources().getDrawable(R.drawable.icon)));


        this.mGridView = (GridView) findViewById(R.id.gv_dashboard);
        this.mDashboardAdapter = new DashboardAdapter(this, dashboardItems);

        this.mGridView.setAdapter(this.mDashboardAdapter);
        this.mGridView.setOnItemClickListener(dashboardItemClickListener);

    }

    /* Click Listeners */
    private AdapterView.OnItemClickListener dashboardItemClickListener = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //to be implemented;
        }
    };
}

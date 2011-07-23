package sg.srcode.xtremeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import greendroid.app.GDActivity;
import sg.srcode.xtremeapp.activity.CabActivity;
import sg.srcode.xtremeapp.activity.CarparkActivity;
import sg.srcode.xtremeapp.activity.DirectionActivity;
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
        dashboardItems.add(new DashboardItem("Car Parks", getResources().getDrawable(R.drawable.icon)));
        dashboardItems.add(new DashboardItem("Cabs", getResources().getDrawable(R.drawable.icon)));
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
            //Initialize a new intent
            Intent intent = new Intent();
            switch(i) {
                case 0:
                    intent.setClass(adapterView.getContext(), DirectionActivity.class);
                    break;
                case 1:
                    intent.setClass(adapterView.getContext(), CarparkActivity.class);
                    break;
                case 2:
                    intent.setClass(adapterView.getContext(), CabActivity.class);
                    break;                                                                        tu
                case 4:
//                    intent.setClass(adapterView.getContext(), PlaceActivity.class);
                    break;
                default:
                    //Do nothing
                    break;
            }                                                                             t
            if(intent.getComponent() != null) {
                startActivity(intent);
            }
        }
    };
}

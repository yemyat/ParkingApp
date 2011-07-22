package sg.srcode.xtremeapp.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import greendroid.app.GDActivity;
import greendroid.widget.GDActionBarItem;
import greendroid.widget.LoaderActionBarItem;
import greendroid.widget.GDActionBarItem.Type;
import sg.srcode.xtremeapp.R;
import sg.srcode.xtremeapp.connection.NimbusServer;

public class CarparkActivity extends GDActivity {

    private NimbusServer mServer;
    private LoaderActionBarItem loaderItem;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setGDActionBarContentView(R.layout.carpark_activity);
        addActionBarItem(Type.Refresh, R.id.action_bar_refresh);

        loaderItem = ((LoaderActionBarItem) getGDActionBar().getItem(0));

        //Initialize a server instance
        mServer = new NimbusServer(this);

        RetrieveDataTask task = new RetrieveDataTask(); //Create a new task
        task.execute("RetrieveData");
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
        mServer.getNearbyCarparks(1.3, 103.85, 2000);
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
            loaderItem.setLoading(false);
        }
    }
}

package sg.srcode.xtremeapp.connection;

import android.content.Context;
import android.util.Log;
import sg.srcode.xtremeapp.item.CarparkItem;
import sg.srcode.xtremeapp.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class NimbusServer {
    protected final String TAG = "XtremeApp";
    protected final String SERVER_BASE_URL = "https://api.projectnimbus.org/";
    protected final String CAR_PARK_URL = "ltafullodataservice.svc/CarParkSet";
    protected final String API_KEY = "b2asLus0l";
    protected final String USER_ID = "00120000000AB0000000000034000001";
    protected final Context mContext;

    //Add the modes necessary as Enum
    private enum Mode {
        CARPARK
    };

    public interface Delegate {
        // Called if connection error occurred
        public void connectionError(String error);

        // Return empty error if success
        public void connectionEnded(String error);
    }

    public NimbusServer(Context context) {
        this.mContext = context;
    }

    //Make request url base on connection
    public String makeUrl(Mode mode) {
        switch (mode) {
            case CARPARK:
                return SERVER_BASE_URL+CAR_PARK_URL;
            default:
                return null;
        }
    }

    //Use this method to initialize connection. It has encapsulated api key
    public URLConnection makeConnection(String urlString) {
        URLConnection conn = null;
        try {
            URL url = new URL(urlString);
            conn = url.openConnection();
            conn.addRequestProperty("AccountKey", API_KEY);
            conn.addRequestProperty("UniqueUserID", USER_ID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public String getSeverUrl() {
        return SERVER_BASE_URL;
    }

    //Remember to make use of StringUtils
    public ArrayList<CarparkItem> getNearbyCarparks(double latitude, double longtitude, double distance) {
        ArrayList<CarparkItem> result = new ArrayList<CarparkItem>();

        try {
            String baseUrl = makeUrl(Mode.CARPARK) + "?Latitude="+latitude+"&"+"Longtitude="+longtitude+"&"+"Distance="+distance;
            URLConnection conn = makeConnection(baseUrl);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()),8000);
            String line = null;
            StringBuilder sb = new StringBuilder();

            while((line = br.readLine()) != null) {
                sb.append(line);
            }

            String[] properties = sb.toString().split("<m:properties>");

            for(String str : properties) {
                CarparkItem item = new CarparkItem();
                item.setmId(StringUtils.getStringBetween(str, "<d:CarParkID m:type=\"Edm.Int32\">", "</d:CarParkID>"));
                item.setmArea(StringUtils.getStringBetween(str, "<d:Area>", "</d:Area>"));
                item.setmDevelopment(StringUtils.getStringBetween(str, "<d:Development>", "</d:Development>"));
                item.setmFreeLots(StringUtils.getStringBetween(str, "<d:Lots m:type=\"Edm.Int32\">", "</d:Lots>"));
                item.setmLatitude(StringUtils.getStringBetween(str, "<d:Latitude m:type=\"Edm.Double\">", "</d:Latitude>"));
                item.setmLongitude(StringUtils.getStringBetween(str, "<d:Longitude m:type=\"Edm.Double\">", "</d:Longitude>"));
                item.setmDistance(StringUtils.getStringBetween(str, "<d:Distance m:type=\"Edm.Double\">", "</d:Distance>"));
                result.add(item);
            }
            Log.d(TAG, "Done retrieving car parks. Retrieved : " + result.size());
        } catch (IOException e) {
            Log.e(TAG, "Error retrieving data");
            e.printStackTrace();
        }
        return result;
    }
}

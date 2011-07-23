package sg.srcode.xtremeapp.connection;

import android.content.Context;
import android.util.Log;
import sg.srcode.xtremeapp.item.CabItem;
import sg.srcode.xtremeapp.item.CarparkItem;
import sg.srcode.xtremeapp.item.PlaceItem;
import sg.srcode.xtremeapp.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class NimbusServer {
    protected final String TAG = "XtremeApp";
    protected final String SERVER_BASE_URL = "https://api.projectnimbus.org/";
    protected final String CAR_PARK_URL = "ltafullodataservice.svc/CarParkSet";
    protected final String CAB_URL = "http://nimbus.cloudapp.net/cxa​odataservice.svc/BookingSet";
    protected final String PLACE_URL = "stbodataservice.svc/PlaceSet";
    protected final String API_KEY = "b2asLus0l";
    protected final String USER_ID = "00120000000AB0000000000034000001";
    protected final Context mContext;

    //Add the modes necessary as Enum
    private enum Mode {
        CARPARK,
        CAB,
        PLACE
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
            case CAB:
                return CAB_URL;
            case PLACE:
                return SERVER_BASE_URL + PLACE_URL;
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
            Log.d(TAG, baseUrl);
            URLConnection conn = makeConnection(baseUrl);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()),8000);
            String line = null;
            StringBuilder sb = new StringBuilder();

            while((line = br.readLine()) != null) {
                sb.append(line);
            }

            String[] properties = sb.toString().split("<m:properties>");

            for(String str : properties) {
                if(!StringUtils.getStringBetween(str, "<d:Area>", "</d:Area>").equals("")) {
                    CarparkItem item = new CarparkItem();
                    item.setmId(StringUtils.getStringBetween(str, "<d:CarParkID m:type=\"Edm.Int32\">", "</d:CarParkID>"));
                    item.setmArea(StringUtils.getStringBetween(str, "<d:Area>", "</d:Area>"));
                    item.setmDevelopment(StringUtils.getStringBetween(str, "<d:Development>", "</d:Development>"));
                    item.setmFreeLots(StringUtils.getOnlyNumerics(StringUtils.getStringBetween(str, "<d:Lots m:type=\"Edm.Int32\">", "</d:Lots>")));
                    item.setmLatitude(StringUtils.getStringBetween(str, "<d:Latitude m:type=\"Edm.Double\">", "</d:Latitude>"));
                    item.setmLongitude(StringUtils.getStringBetween(str, "<d:Longitude m:type=\"Edm.Double\">", "</d:Longitude>"));
                    item.setmDistance(StringUtils.getStringBetween(str, "<d:Distance m:type=\"Edm.Double\">", "</d:Distance>"));
                    result.add(item);
                }

            }
            Log.d(TAG, "Done retrieving car parks. Retrieved : " + result.size());
        } catch (IOException e) {
            Log.e(TAG, "Error retrieving data");
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<CabItem> getNearbyTaxis(double latitude, double longtitude, double distance) {
        ArrayList<CabItem> result = new ArrayList<CabItem>();
        try {
            String baseUrl = makeUrl(Mode.CAB);/* + "?Latitude="+latitude+"&"+"Longtitude="+longtitude+"&"+"Distance="+distance;   */
            Log.d(TAG, baseUrl.toString());
            URLConnection conn = makeConnection(baseUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)conn;
            Log.e(TAG, String.valueOf(httpURLConnection.getResponseCode()));
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()),8000);
            String line = null;
            StringBuilder sb = new StringBuilder();

            while((line = br.readLine()) != null) {
                sb.append(line);
            }

            Log.d(TAG, sb.toString());
            /*String[] properties = sb.toString().split("<m:properties>");
            for(String str : properties) {
                CabItem item = new CabItem();


            }*/
        } catch(IOException e) {
            Log.e(TAG, "Error retrieving cab data");
        }
        return result;
    }

    public ArrayList<PlaceItem> getNearbyPlaces(double latitude, double longitude, double distance) {
        ArrayList<PlaceItem> result = new ArrayList<PlaceItem>();
        try {
           String baseUrl = makeUrl(Mode.PLACE) + "?Latitude=" + latitude + "&" + "Longitude=" + longitude + "&" + "Distance=" + distance;

           URLConnection conn = makeConnection(baseUrl);
           BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()), 8000);
            String line = null;
            StringBuilder sb = new StringBuilder();

            while((line = br.readLine()) != null) {
                sb.append(line);
            }

            String[] properties = sb.toString().split("<m:properties>");
            for(String str : properties) {
                PlaceItem item = new PlaceItem();
                item.setmId(StringUtils.getStringBetween(str, "<d:PlaceID m:type=\"Edm.Int32\">", "</d:PlaceID>"));
                item.setmName(StringUtils.getStringBetween(str, "<d:Name>", "</d:Name>"));
                item.setmAddress(StringUtils.getStringBetween(str, "<d:Address1>", "</d:Address1>"));
                item.setmPostalCode(StringUtils.getStringBetween(str, "<d:PostalCode>", "</d:PostalCode>"));
                item.setmTel(StringUtils.getStringBetween(str, "<d:Tel>", "</d:Tel>"));
                item.setmURL(StringUtils.getStringBetween(str, "<d:URL>", "</d:URL"));
                item.setmOperatingHrs(StringUtils.getStringBetween(str, "<d:OperatingHours>", "</d:OperatingHours>"));
                item.setmLat(StringUtils.getStringBetween(str, "<d:Latitude m:type=\"Edm.Double\">", "</d:Latitude>"));
                item.setmLng(StringUtils.getStringBetween(str, "<d:Longitude m:type=\"Edm.Double\">", "</d:Longitude>"));
                item.setmDistance(StringUtils.getStringBetween(str, "<d:Distance m:type=\"Edm.Double\">", "</d:Distance>"));
                result.add(item);
            }


        }  catch(IOException e) {
            Log.e(TAG, "Error retrieving place data");
        }
        return result;
    }




}

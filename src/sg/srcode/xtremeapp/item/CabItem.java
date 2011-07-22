package sg.srcode.xtremeapp.item;

/**
 * Created by IntelliJ IDEA.
 * User: emoosx
 * Date: 22/7/11
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class CabItem {

    protected int mId;
    protected String mDate;
    protected String mJobType;
    protected int mVechicleId;
    protected String mDriverNRIC;
    protected double mPickupLng;
    protected double mPickupLat;
    protected String mPickupPoint;
    protected double mDestLng;
    protected double mDestLat;

    public CabItem() {}

    public CabItem(int id, String date, String jobType, int vID, String driverNRIC, double pLng, double pLat, String pickupPoint, double dLng, double dLat) {
        this.mId = id;
        this.mDate = date;
        this.mJobType = jobType;
        this.mVechicleId = vID;
        this.mDriverNRIC = driverNRIC;
        this.mPickupLng = pLng;
        this.mPickupLat = pLat;
        this.mPickupPoint = pickupPoint;
        this.mDestLng = dLng;
        this.mDestLat = dLat;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmJobType() {
        return mJobType;
    }

    public void setmJobType(String mJobType) {
        this.mJobType = mJobType;
    }

    public int getmVechicleId() {
        return mVechicleId;
    }

    public void setmVechicleId(int mVechicleId) {
        this.mVechicleId = mVechicleId;
    }

    public String getmDriverNRIC() {
        return mDriverNRIC;
    }

    public void setmDriverNRIC(String mDriverNRIC) {
        this.mDriverNRIC = mDriverNRIC;
    }

    public double getmPickupLng() {
        return mPickupLng;
    }

    public void setmPickupLng(double mPickupLng) {
        this.mPickupLng = mPickupLng;
    }

    public double getmPickupLat() {
        return mPickupLat;
    }

    public void setmPickupLat(double mPickupLat) {
        this.mPickupLat = mPickupLat;
    }

    public String getmPickupPoint() {
        return mPickupPoint;
    }

    public void setmPickupPoint(String mPickupPoint) {
        this.mPickupPoint = mPickupPoint;
    }

    public double getmDestLng() {
        return mDestLng;
    }

    public void setmDestLng(double mDestLng) {
        this.mDestLng = mDestLng;
    }

    public double getmDestLat() {
        return mDestLat;
    }

    public void setmDestLat(double mDestLat) {
        this.mDestLat = mDestLat;
    }
}

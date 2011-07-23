package sg.srcode.xtremeapp.item;

/**
 * Created by IntelliJ IDEA.
 * User: emoosx
 * Date: 23/7/11
 * Time: 12:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class PlaceItem {
    protected String mId;
    protected String mName;
    protected String mAddress;
    protected String mPostalCode;
    protected String mTel;
    protected String mURL;
    protected String mOperatingHrs;
    protected String mLat;
    protected String mLng;
    protected String mDistance;

    public PlaceItem() {}

    public PlaceItem(String mId, String mName, String mAddress, String mPostalCode, String mTel, String mURL, String mOperatingHrs, String mLat, String mLng, String mDistance) {
        this.mId = mId;
        this.mName = mName;
        this.mAddress = mAddress;
        this.mPostalCode = mPostalCode;
        this.mTel = mTel;
        this.mURL = mURL;
        this.mOperatingHrs = mOperatingHrs;
        this.mLat = mLat;
        this.mLng = mLng;
        this.mDistance = mDistance;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmPostalCode() {
        return mPostalCode;
    }

    public void setmPostalCode(String mPostalCode) {
        this.mPostalCode = mPostalCode;
    }

    public String getmTel() {
        return mTel;
    }

    public void setmTel(String mTel) {
        this.mTel = mTel;
    }

    public String getmURL() {
        return mURL;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }

    public String getmOperatingHrs() {
        return mOperatingHrs;
    }

    public void setmOperatingHrs(String mOperatingHrs) {
        this.mOperatingHrs = mOperatingHrs;
    }

    public String getmLat() {
        return mLat;
    }

    public void setmLat(String mLat) {
        this.mLat = mLat;
    }

    public String getmLng() {
        return mLng;
    }

    public void setmLng(String mLng) {
        this.mLng = mLng;
    }

    public String getmDistance() {
        return mDistance;
    }

    public void setmDistance(String mDistance) {
        this.mDistance = mDistance;
    }

    public String toString() {
        return this.mName + "\n" + this.mOperatingHrs + "\n" +this.mDistance;
    }
}

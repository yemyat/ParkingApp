package sg.srcode.xtremeapp.item;

public class CarparkItem {

    protected String mId;
    protected String mArea;
    protected String mDevelopment;
    protected String mFreeLots;
    protected String mLatitude;
    protected String mLongitude;
    protected String mDistance;

    public CarparkItem() {}

    public CarparkItem(String id, String area, String development, String freeLots, String latitude, String longitude, String distance) {
        this.mId = id;
        this.mArea = area;
        this.mDevelopment = development;
        this.mFreeLots = freeLots;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mDistance = distance;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmArea() {
        return mArea;
    }

    public void setmArea(String mArea) {
        this.mArea = mArea;
    }

    public String getmDevelopment() {
        return mDevelopment;
    }

    public void setmDevelopment(String mDevelopment) {
        this.mDevelopment = mDevelopment;
    }

    public String getmFreeLots() {
        return mFreeLots;
    }

    public void setmFreeLots(String mFreeLots) {
        this.mFreeLots = mFreeLots;
    }

    public String getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(String mLatitude) {
        this.mLatitude = mLatitude;
    }

    public String getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(String mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getmDistance() {
        return mDistance;
    }

    public void setmDistance(String mDistance) {
        this.mDistance = mDistance;
    }
}

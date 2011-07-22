package sg.srcode.xtremeapp.item;

import sg.srcode.xtremeapp.utils.StringUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CarparkItem {

    protected String mId;
    protected String mArea;
    protected String mDevelopment;
    protected String mFreeLots;
    protected String mLatitude;
    protected String mLongitude;
    protected String mDistance;
    protected String mAvailability;
    protected String mCharge;

    protected String mBaseCharge;

    private final String[] WEEK_MORNING_PEAK = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private final String[] TIME_MORNING_PEAK = {"07:00", "09:30"};

    private final String[] WEEK_EVENING_PEAK = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private final String[] TIME_EVENING_PEAK = {"17:00", "20:00"};

    private final String[] DAYS_OF_WEEK = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};


    //We're going to fake the base charge for now. Since they are no APIs for that
    public CarparkItem() {
        double myCharge = Math.random() * (3.0 - 0.8);
        this.mBaseCharge = StringUtils.twoDecimalFormatter(myCharge);
    }

    public CarparkItem(String id, String area, String development, String freeLots, String latitude, String longitude, String distance) {
        this();
        this.mId = id;
        this.mArea = area;
        this.mDevelopment = development;
        this.mFreeLots = freeLots;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mDistance = distance;
        calculateAvailability();
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
        calculateAvailability();
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

    public String getmAvailability() {
        return mAvailability;
    }

    public void setmAvailability(String mAvailability) {
        this.mAvailability = mAvailability;
    }

    private void calculateAvailability() {
        String result = "high";
        if (Integer.parseInt(this.mFreeLots) < 250) {
            result = "medium";
        }
        if (Integer.parseInt(this.mFreeLots) < 100) {
            result = "low";
        }
        setmAvailability(result);
    }

    public String getmCharge() {
        return mCharge;
    }

    public void setmCharge(String mCharge) {
        this.mCharge = mCharge;
    }

    public String getmBaseCharge() {
        return mBaseCharge;
    }

    public void setmBaseCharge(String mBaseCharge) {
        this.mBaseCharge = mBaseCharge;
    }

    private void calculateExtraCharge() {

        double charge = Double.parseDouble(this.mCharge);

        /* Calculating charge for peak hours */
        Calendar calendar = new GregorianCalendar();

        //check the time first
        int currentHour = calendar.get(Calendar.HOUR);
        int currentMinute = calendar.get(Calendar.MINUTE);

        String day = DAYS_OF_WEEK[calendar.get(Calendar.DAY_OF_WEEK)-1];

        //Morning check
        if (StringUtils.isInArray(day, WEEK_MORNING_PEAK)) {
            if ((currentHour >= 7 && currentMinute >= 0) && (currentHour < 9 && currentMinute >= 0)) {
                charge += 1.2;
            }
        }

        if (StringUtils.isInArray(day, WEEK_EVENING_PEAK)) {
            //Evening check
            if ((currentHour >= 17 && currentMinute >= 0) && (currentHour < 20 && currentMinute >= 0)) {
                charge += 2.1;
            }
        }

        /* Calculating charge for weekend */
        if(day.equalsIgnoreCase("Sunday")) {
            charge += 0.8;
        }
        this.mCharge = String.valueOf(charge).substring(0, 3);
    }


}

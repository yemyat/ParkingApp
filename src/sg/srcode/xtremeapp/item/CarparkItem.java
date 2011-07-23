package sg.srcode.xtremeapp.item;

import android.util.Log;
import sg.srcode.xtremeapp.utils.StringUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CarparkItem {

    public interface CarparkDelegate {
        public void updatedValue(String charge);
    }

    protected CarparkDelegate mDelegate;

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

    private String mStartDate;
    private String mStartTime;
    private String mEndDate;
    private String mEndTime;
    private String mIntervalDuration;
    private String mIntervalRate;
    private boolean isPeakHour;
    private boolean isWeekend;

    private final String[] WEEK_MORNING_PEAK = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private final String[] TIME_MORNING_PEAK = {"07:00", "09:30"};

    private final String[] WEEK_EVENING_PEAK = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private final String[] TIME_EVENING_PEAK = {"17:00", "20:00"};

    private final String[] DAYS_OF_WEEK = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};


    //We're going to fake the base charge for now. Since they are no APIs for that
    public CarparkItem() {
        isPeakHour = false;
        isWeekend = false;
        double myCharge = Math.random() * (3.0 - 0.8);
        this.mBaseCharge = StringUtils.twoDecimalFormatter(myCharge);
        this.mCharge = this.mBaseCharge;
        this.mIntervalDuration = "";
        this.mIntervalRate = "";
        this.mStartDate = "";
        this.mStartTime = "";
        this.mEndDate = "";
        this.mEndTime = "";
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

    public void reset() {
        this.mCharge = this.mBaseCharge;
        this.mIntervalDuration = "";
        this.mIntervalRate = "";
        this.mStartDate = "";
        this.mStartTime = "";
        this.mEndDate = "";
        this.mEndTime = "";
        this.mDelegate.updatedValue(this.mCharge);
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

    public String getmStartDate() {
        return mStartDate;
    }

    public void setmStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
        calculate();
    }

    public String getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
        calculate();
    }

    public String getmEndDate() {
        return mEndDate;
    }

    public void setmEndDate(String mEndDate) {
        this.mEndDate = mEndDate;
        calculate();
    }

    public String getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
        calculate();
    }

    public String getmIntervalDuration() {
        return mIntervalDuration;
    }

    public void setmIntervalDuration(String mIntervalDuration) {
        this.mIntervalDuration = mIntervalDuration;
        calculate();
    }

    public String getmIntervalRate() {
        return mIntervalRate;
    }

    public void setmIntervalRate(String mIntervalRate) {
        this.mIntervalRate = mIntervalRate;
        calculate();
    }

    public boolean isPeakHour() {
        return isPeakHour;
    }

    public void setPeakHour(boolean peakHour) {
        isPeakHour = peakHour;
        calculateExtraCharge();
        calculate();
    }

    public boolean isWeekend() {
        return isWeekend;
    }

    public void setWeekend(boolean weekend) {
        isWeekend = weekend;
        calculate();
    }

    private void calculateExtraCharge() {

        if(isPeakHour() || isWeekend())  {

        double charge = Double.parseDouble(this.mCharge);

        /* Calculating charge for peak hours */
        Calendar calendar = new GregorianCalendar();

        //check the time first
        int currentHour = calendar.get(Calendar.HOUR);
        int currentMinute = calendar.get(Calendar.MINUTE);

        String day = DAYS_OF_WEEK[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        if (isPeakHour) {
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
        }


        if (isWeekend) {
            /* Calculating charge for weekend */
            if (day.equalsIgnoreCase("Sunday")) {
                charge += 0.8;
            }
        }
        this.mCharge = StringUtils.twoDecimalFormatter(charge);
        mDelegate.updatedValue(this.mCharge);
        }

    }

    private void calculateInterval() {

        if ((!this.mIntervalDuration.equalsIgnoreCase("")) && (!this.mIntervalRate.equalsIgnoreCase(""))
                && (!this.mStartDate.equalsIgnoreCase("")) && (!this.mStartTime.equalsIgnoreCase(""))
                && (!this.mEndDate.equalsIgnoreCase("")) && (!this.mEndTime.equalsIgnoreCase(""))) {
            double charge = Double.parseDouble(this.mBaseCharge);

            Calendar startCal = Calendar.getInstance();
            Calendar endCal = Calendar.getInstance();

            String[] startCalArr = StringUtils.splitDateIntoThreeString(this.mStartDate);
            String[] endCalArr = StringUtils.splitDateIntoThreeString(this.mEndDate);

            String[] startCalTimeArr = StringUtils.splitTimeIntoTwoString(this.mStartTime);
            String[] endCalTimeArr = StringUtils.splitTimeIntoTwoString(this.mEndTime);

            startCal.set(Integer.parseInt(startCalArr[2]), Integer.parseInt(startCalArr[1]), Integer.parseInt(startCalArr[0]), Integer.parseInt(startCalTimeArr[0]), Integer.parseInt(startCalTimeArr[1]));

            endCal.set(Integer.parseInt(endCalArr[2]), Integer.parseInt(endCalArr[1]), Integer.parseInt(endCalArr[0]), Integer.parseInt(endCalTimeArr[0]), Integer.parseInt(endCalTimeArr[1]));

            long diff = endCal.getTimeInMillis() - startCal.getTimeInMillis();
            diff = diff / (60 * 1000);

            Log.d("XtremeApp", diff + " minutes");

            charge += (diff / (Double.parseDouble(StringUtils.getInterval(this.mIntervalDuration)))) * Double.parseDouble(this.mIntervalRate);
            this.mCharge = StringUtils.twoDecimalFormatter(charge);
            mDelegate.updatedValue(this.mCharge);


        }
    }

    private void calculate() {
        calculateInterval();
        calculateExtraCharge();
    }

    public CarparkDelegate getmDelegate() {
        return mDelegate;
    }

    public void setmDelegate(CarparkDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }
}

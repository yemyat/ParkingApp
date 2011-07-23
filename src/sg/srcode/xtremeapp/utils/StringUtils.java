package sg.srcode.xtremeapp.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

public class StringUtils {

    //This functions get the xml data between the xml elements
    public static String getStringBetween(String src, String start, String end) {
        StringBuilder sb = new StringBuilder();
        int startIdx = src.indexOf(start) + start.length();
        int endIdx = src.indexOf(end);
        while (startIdx < endIdx) {
            sb.append(String.valueOf(src.charAt(startIdx)));
            startIdx++;
        }

        return sb.toString();
    }

    public static String getOnlyNumerics(String str) {

        if (str == null) {
            return null;
        }

        StringBuffer strBuff = new StringBuffer();
        char c;

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);

            if (Character.isDigit(c)) {
                strBuff.append(c);
            }
        }
        return strBuff.toString();
    }

    public static boolean isInArray(String string, String[] array) {
        for(String temp : array) {
            if(string.equalsIgnoreCase(temp)) {
                return true;
            }
        }
        return false;
    }

    public static String twoDecimalFormatter(Double myDouble) {
        //Dynamic formatter
        NumberFormat f = new DecimalFormat("#,###,###,##0.00");
        return f.format(myDouble);
    }

    public static String[] splitDateIntoThreeString(String date) {
        return date.split("/");
    }

    public static String[] splitTimeIntoTwoString(String time) {
        return time.split(":");
    }

    public static String getInterval(String str) {
        String[] temp = str.split(" ");
        return temp[0];
    }

    //This functions get the xml data between the xml elements
    public static String getStringBetweenBrackets(String src, String start, String end) {
        StringBuilder sb = new StringBuilder();
        int startIdx = src.indexOf(start) + start.length();
        int endIdx = src.indexOf(end);
        while (startIdx < endIdx) {
            sb.append(String.valueOf(src.charAt(startIdx)));
            startIdx++;
        }

        return sb.toString();
    }
}

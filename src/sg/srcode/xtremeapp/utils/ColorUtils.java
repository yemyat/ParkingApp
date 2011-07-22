package sg.srcode.xtremeapp.utils;

import sg.srcode.xtremeapp.R;

public class ColorUtils
{

	public static int mapColorToValue(String availability) {

        int colorCode = R.color.green;

        if(availability.equals("medium")) {
            colorCode = R.color.yellow;
        }

        if(availability.equals("low")) {
            colorCode = R.color.red;
        }

        return colorCode;

    }
}

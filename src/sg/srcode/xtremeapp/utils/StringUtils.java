package sg.srcode.xtremeapp.utils;

public class StringUtils
{

	//This functions get the xml data between the xml elements
	public static String getStringBetween(String src, String start, String end)
	{
		StringBuilder sb = new StringBuilder();
		int startIdx = src.indexOf(start) + start.length();
		int endIdx = src.indexOf(end);
			while(startIdx < endIdx)
			{
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

    for (int i = 0; i < str.length() ; i++) {
        c = str.charAt(i);

        if (Character.isDigit(c)) {
            strBuff.append(c);
        }
    }
    return strBuff.toString();
}
}

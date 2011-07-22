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
				sb.append("" + String.valueOf(src.charAt(startIdx)));
				startIdx++;
			}

		return sb.toString();
	}
}

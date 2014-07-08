package tw.wesely.mstrikealerm;

import android.util.Log;

public class TimeProc {
	public static String detectAndShiftTimeFormat(String text){
		return text;
	}
	
	public static String getShiftedTime(String strTime){
		Log.d("Timeproc", strTime);
		String[] tokens = strTime.split("[ -]");
		for(String str : tokens) {
			if( str.matches("[0-9]+:[0-5][0-9]") ) {
				String[] toks = str.split(":");
				toks[0] = String.valueOf( Integer.parseInt(toks[0]) - 1 );
				String replacement = toks[0] + ":" + toks[1];
				strTime = strTime.replace(str, replacement);
			}
		}
		return strTime+"(¥x)";
	}
}

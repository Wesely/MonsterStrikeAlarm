package tw.wesely.translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

public class MStrans {
	public static String getTranslated(String orgText, Context ctx) throws IOException {
	    InputStream is = ctx.getAssets().open("translate.txt");
	    BufferedReader in =
	        new BufferedReader(new InputStreamReader(is));
	    String str;
		
		String translated = orgText;
		while( (str = in.readLine()) != null ) {
			String[] tokens = str.split("\t");
			if(tokens.length >= 2)
				translated = translated.replace(tokens[0], tokens[1]);
		}
		
		return translated;
	}
}

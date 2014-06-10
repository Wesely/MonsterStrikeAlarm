package tw.wesely.mstrikealerm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class MSWebView extends WebView {

	public MSWebView(Context context) {
		super(context);
		this.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.d("url.startsWith", url);
				if (url != null && url.startsWith("http://")) {
					view.getContext().startActivity(
							new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					return true;
				} else {
					return true;
				}
			}
		});
	}

	Context ctx; 
	
	public WebView getWebView(Context ctx) {
		return this;
	}
	

}

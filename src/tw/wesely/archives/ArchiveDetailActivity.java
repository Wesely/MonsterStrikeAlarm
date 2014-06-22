package tw.wesely.archives;

import tw.wesely.mstrikealarm.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ArchiveDetailActivity extends Activity {
	Context ctx;
	String monsterID;
	MonsterWebView mwv = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archive);
		Intent intent = getIntent();
		ctx = ArchiveDetailActivity.this;
		monsterID = intent.getExtras().getString("monsterID");
		Log.d("ArchiveDetailActivity", "reciving monsterID = No." + monsterID);

		mwv = new MonsterWebView(ctx);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		params.setMargins(0, 0, 0, 0);
		
		mwv.setWebViewClient(new WebViewClient()  {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url){
				Log.d("monster client", "set client");
				if( url.matches(".*/monster/.*") ) {
					Log.d("monster client", "load data");
					mwv.loadMonsterSection(url);
					return true;
				}
				return false;
			}
		});
		mwv.loadMonsterById(monsterID);
		// mwv.setLayoutParams(params);
		LinearLayout container = (LinearLayout) findViewById(R.id.container);
		container.addView(mwv);
	}

	@Override
	public void onDestroy() {
		super.onDestroy(); // Always call the superclass
		// Stop method tracing that the activity started during onCreate()
		android.os.Debug.stopMethodTracing();
	}
}

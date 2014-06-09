package tw.wesely.mstrikealerm;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tw.wesely.mstrikealarm.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			new MonsterSite().execute();
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	// URL Address
	String url = "http://www.dopr.net/monst";
	ProgressDialog mProgressDialog;
	static View rootView;
	LinearLayout rootLL;

	// Title AsyncTask
	private class MonsterSite extends AsyncTask<Void, Void, Void> {
		@SuppressWarnings("unused")
		String title;
		Document document;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			rootLL = (LinearLayout) findViewById(R.id.rootLL);
			mProgressDialog = new ProgressDialog(MainActivity.this);
			mProgressDialog.setTitle("載入降臨時間");
			mProgressDialog.setMessage("載入中...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				// Connect to the web site
				document = Jsoup.connect(url).get();
				// Get the html document title
				title = document.title();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@SuppressLint("SetJavaScriptEnabled")
		@Override
		protected void onPostExecute(Void result) {
			rootLL = (LinearLayout) findViewById(R.id.rootLL);
			setGroupInfo();
			String content = "";
			/** show table 0 & 1 **/
			// content = content + "get(0) = \n"
			// + document.select("table").get(0).text()
			// + "\n\nget(1) = \n"
			// + document.select("table").get(1).text();

			/** show table 2 **/
			// content = content + "\n\nget(2) = \n"
			// + document.select("table").get(2).html();

			try {
				content = content + parseTurtleTable(document);
			} catch (Exception e) {
				e.printStackTrace();
				Builder builder = new AlertDialog.Builder(MainActivity.this);
				// 設定Dialog的標題
				builder.setTitle("無法分析");
				builder.setMessage("出現了錯誤或是預料之外的降臨活動，請以下面的表格內容時間為準\n烏龜將無法設定鬧鐘敬請見諒，我們會儘快更新修復。");
				builder.create().show();
			}

			LayoutInflater inflater = (LayoutInflater) MainActivity.this
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

			TextView tvHeader2 = (TextView) inflater.inflate(
					R.layout.component_headertextview, null);
			tvHeader2.setText("【今日降臨關卡！】\n*取自日文版攻略網\n點選連結會前往日版網站");
			LayoutParams params = new LayoutParams(
			        LayoutParams.MATCH_PARENT,      
			        LayoutParams.WRAP_CONTENT
			);
			params.setMargins(0, 30, 0, 0);
			tvHeader2.setLayoutParams(params);
			rootLL.addView(tvHeader2);
			/*********************************************/

			WebView wv2 = new WebView(MainActivity.this);
			wv2.loadDataWithBaseURL(
					"",
					"<table border=\"1\" style=\"border-collapse:collapse;\" borderColor=\"black\" >"
							+ document.select("table").get(2).html()
							+ "</table>", "text/html", "UTF-8", "");
			wv2.setWebViewClient(new WebViewClient(){
				 @Override
				    public boolean shouldOverrideUrlLoading(WebView view, String url) {
					 String SCHEME = "https://";
					 Log.d("shouldOverrideUrlLoading" , url);
					    return false;
				    }
			});
			
			rootLL.addView(wv2);

			TextView tvHeader = (TextView) inflater.inflate(
					R.layout.component_headertextview, null);
			tvHeader.setText("【近期降臨時間表】\n*取自日文版攻略網\n點選連結會前往日版網站");
			tvHeader.setLayoutParams(params);
			rootLL.addView(tvHeader);
			/*********************************************/
			WebView wv = new WebView(MainActivity.this);
			wv.loadDataWithBaseURL(
					"",
					"<table border=\"1\" style=\"border-collapse:collapse;\" borderColor=\"black\" >"
							+ document.select("table").get(3).html()
							+ "</table>", "text/html", "UTF-8", "");
			rootLL.addView(wv);

			// Set title into TextView
			TextView txttitle = (TextView) findViewById(R.id.section_label);
			txttitle.setText(""); // set to content for debug
			mProgressDialog.dismiss();
		}
	}

	public void setGroupInfo() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(MainActivity.this);
		TextView tvGroup = (TextView) findViewById(R.id.tvGroup);
		int id = sharedPrefs.getInt("ID", 00);
		int riceGroup = (id % 4);
		int yearGroup = (id % 5);
		String result = "【ID末兩碼 " + id + " 所屬組別】\n" + "飯龜：" + riceGroup + "組"
				+ "\n年龜：" + yearGroup + "組\n";
		tvGroup.setText(result);
	}


	public String parseTurtleTable(Element document) {

		String content = "=========================\n";
		Elements tbls = document.getElementsByTag("table");

		for (Element tbl : tbls) {
			Element headline = tbl.previousElementSibling();
			List<String> listTH = new ArrayList<String>();
			List<String> listTD = new ArrayList<String>();

			while (headline != null) {
				if (headline.tagName() == "h2" || headline.tagName() == "h3")
					break;
				headline = headline.previousElementSibling();
			}
			content += headline.text() + "\n";
			content += "----------------------\n";

			content += "| ";
			Elements fields = tbl.getElementsByTag("th");
			for (Element field : fields) {// 組
				content += field.text() + " | ";
				listTH.add(field.text());
			}

			content += "\n";
			content += "| ";
			int cnt = 0;
			Elements datas = tbl.getElementsByTag("td");
			for (Element data : datas) {
				++cnt;
				Log.d("for (Element data ",
						"cnt = " + cnt + ";  " + data.text());
				content += data.text() + " | ";
				listTD.add(data.text());
				if (cnt % fields.size() == 0)
					content += "\n";
			}

			if (headline.text().contains("飯")) {
				Log.d("parseTurtleTable", "飯龜");
				TurtleQuest tq = new TurtleQuest("【昼の飯より亀の甲？】", listTH, listTD);
				rootLL.addView(tq.getTurtleStageChartView(MainActivity.this));
			}
			if (headline.text().contains("年")) {
				Log.d("parseTurtleTable", "年龜");
				TurtleQuest tq = new TurtleQuest("【年の功より亀の甲？】", listTH, listTD);
				rootLL.addView(tq.getTurtleStageChartView(MainActivity.this));
			}
			if (headline.text().contains("マン")) {
				Log.d("parseTurtleTable", "超大龜");
				TurtleQuest tq = new TurtleQuest("【マンの亀よりオクの甲？】", listTH,
						listTD);
				rootLL.addView(tq.getTurtleStageChartView(MainActivity.this));
			}
			content += "===========================\n";
		}
		return content;
	}

	/**
	 * System gen
	 */
	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static void setTextToMainPage(String string) {
		TextView textView = (TextView) rootView
				.findViewById(R.id.section_label);
		textView.setText(string);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			TextView textView = (TextView) rootView
					.findViewById(R.id.section_label);
			switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
			case 1:
				textView.setText("載入中...");
				break;
			case 2:
				SharedPreferences sharedPrefs = PreferenceManager
						.getDefaultSharedPreferences(getActivity());
				setTextToMainPage("\n**目前設定的ID尾端兩碼為【"
						+ sharedPrefs.getInt("ID", 00) + "】");
				new PlayerID().getSetIDAlertDialog(container, getActivity())
						.show();
				break;
			case 3:
				textView.setText(getString(R.string.about));
				break;
			}
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}

	}

}

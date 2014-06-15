package tw.wesely.mstrikealerm;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tw.wesely.archives.MonsterArchive;
import tw.wesely.mstrikealarm.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

@SuppressLint({ "NewApi", "SimpleDateFormat" })
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
	static Context ctx;
	static Document document;
	static SharedPreferences sharedPrefs;
	static TextView tvGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();
		ctx = MainActivity.this;
		tvGroup = (TextView) findViewById(R.id.tvMsg);

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(MainActivity.this);

		// Editor editor = sharedPrefs.edit();
		// editor.clear();
		// editor.commit();

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
		case 4:
			mTitle = getString(R.string.title_section4);
		case 5:
			mTitle = getString(R.string.title_section5);
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
				// Replace all hypertext to absolute link
				Elements links = document.getElementsByTag("a");
				for (Element link : links)
					link.attr("href", "http://www.dopr.net" + link.attr("href"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@SuppressLint("SetJavaScriptEnabled")
		@Override
		protected void onPostExecute(Void result) {
			mProgressDialog.dismiss();
			setMainpageContent(document);
		}
	}

	public void setMainpageContent(Document document) {
		rootLL = (LinearLayout) findViewById(R.id.rootLL);
		tvGroup = (TextView) findViewById(R.id.tvMsg);
		setGroupInfo();
		try {
			parseTurtleTable(document);
		} catch (Exception e) {
			e.printStackTrace();
			Builder builder = new AlertDialog.Builder(MainActivity.this);
			// 設定Dialog的標題
			builder.setTitle("無法分析");
			builder.setMessage("\n目前只能在網路正常的狀況下執行\n也可能是出現了預料之外的降臨活動\n");
			builder.create().show();
		}
	}


	public void setTurtleNotification(String title, String msg, Long time) {
		return;
//		Intent intentAlarm = new Intent(MainActivity.this, TurtleAlarmReceiver.class);
//	    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//	    alarmManager.set(AlarmManager.RTC_WAKEUP, time, 
//	    		PendingIntent.getBroadcast(MainActivity.this,1,  
//	    				intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
//	    Log.d("notify", "set alarm");
	}

	public static void setGroupInfo() {
		int id = sharedPrefs.getInt("ID", -1);
		String sid = id + "";
		if (id < 0) {
			setTextToMainPage("請點選左上選單設定ID");
			return;
		}
		if (id > -1 && id < 10)
			sid = "0" + id;
		String result = "【ID末兩碼 " + sid + "】";
		tvGroup.setText(result);
	}

	public String parseTurtleTable(Element document) {

		String content = "";
		Elements tbls = document.getElementsByTag("table");

		for (Element tbl : tbls) {
			Element headline = tbl.previousElementSibling();
			List<String> listTH = new ArrayList<String>();
			List<String> listTDTime = new ArrayList<String>();
			List<Element> listTDTitle = new ArrayList<Element>();// colored

			while (headline != null) {
				if (headline.tagName() == "h2" || headline.tagName() == "h3")
					break;
				headline = headline.previousElementSibling();
			}

			Elements fields = tbl.getElementsByTag("th");
			for (Element field : fields) { // 組
				listTH.add(field.text());
				Log.d("fields", "field.text() = " + field.text());
			}

			Elements datas = tbl.getElementsByTag("td");
			for (int index = 0; index < datas.size(); index++) {
				Element data = datas.get(index);
				if (headline.text().contains("亀")) {
					Log.d("contains(亀)", "data.text()" + data.text());
					listTDTime.add(TimeProc.getShiftedTime(data.text()));
					continue;
				} else if ((index % fields.size()) == 0) { // is Stage Title
					Log.d("(index % fields.size()) == 0)", data.text());
					listTDTitle.add(data);
					continue;
				}
				// if (data.text().matches(".*[0-9]:[0-5][0-9].*")) {
				if ((index % fields.size()) == 1) {
					// Representing TIME X:XX & XX:XX
					String item = TimeProc.getShiftedTime(data.text());
					data.text(item);
					Log.d("token time", (index % fields.size()) + ":" + item);
					listTDTime.add(item);
					continue;
				}
			}
			if (headline.text().contains("飯")) {
				Log.d("parseTurtleTable", "飯龜");
				QuestTurtle tq = new QuestTurtle("【昼の飯より亀の甲？】", listTH,
						listTDTime);
				
				for(String td : listTDTime) {
					String[] tokens = td.split("[-()]");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(Calendar.getInstance().getTime());
					Long time = Timestamp.valueOf(date + " " + tokens[0]+":00").getTime();
					Log.d("not time", String.valueOf(time));
					setTurtleNotification("打烏龜囉！", "飯龜", time);
				}
				rootLL.addView(tq.getTurtleStageChartView(MainActivity.this));
			}
			if (headline.text().contains("年")) {
				Log.d("parseTurtleTable", "年龜");
				QuestTurtle tq = new QuestTurtle("【年の功より亀の甲？】", listTH,
						listTDTime);
				rootLL.addView(tq.getTurtleStageChartView(MainActivity.this));
				
				for(String td : listTDTime) {
					String[] tokens = td.split("[-()]");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(Calendar.getInstance().getTime());
					Long time = Timestamp.valueOf(date + " " + tokens[0]+":00").getTime();
					Log.d("not time", String.valueOf(time));
					setTurtleNotification("打烏龜囉！", "飯龜", time);
				}
			}
			if (headline.text().contains("マン")) {
				Log.d("parseTurtleTable", "超大龜");
				QuestTurtle tq = new QuestTurtle("【マンの亀よりオクの甲？】", listTH,
						listTDTime);
				rootLL.addView(tq.getTurtleStageChartView(MainActivity.this));
				
				for(String td : listTDTime) {
					String[] tokens = td.split("[-()]");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(Calendar.getInstance().getTime());
					Long time = Timestamp.valueOf(date + " " + tokens[0]+":00").getTime();
					Log.d("not time", String.valueOf(time));
					setTurtleNotification("打烏龜囉！", "飯龜", time);
				}
			}
			if (headline.text().contains("開催中")) {
				Log.d("parseTurtleTable", "開催中");
				QuestBoss tq = new QuestBoss("【現正降臨中】", listTDTitle, listTDTime);
				rootLL.addView(tq.getTurtleStageChartView(MainActivity.this));
			}
			if (headline.text().contains("予定")) {
				Log.d("parseTurtleTable", "予定");
				QuestBoss tq = new QuestBoss("【未來降臨預定】", listTDTitle,
						listTDTime);
				rootLL.addView(tq.getTurtleStageChartView(MainActivity.this));
			}

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
		// int id = item.getItemId();
		// if (id == R.id.action_settings) {
		// return true;
		// }
		return super.onOptionsItemSelected(item);
	}

	public static void setTextToMainPage(String string) {
		TextView textView = (TextView) rootView.findViewById(R.id.tvMsg);
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
			TextView textView = (TextView) rootView.findViewById(R.id.tvMsg);
			switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
			case 1:
				textView.setText("載入中...");
				break;
			case 4:
				SharedPreferences sharedPrefs = PreferenceManager
						.getDefaultSharedPreferences(getActivity());
				setTextToMainPage("\n**目前設定的ID尾端兩碼為【"
						+ sharedPrefs.getInt("ID", -1) + "】");
				new PlayerID().getSetIDAlertDialog(container, getActivity())
						.show();
				break;
			case 3:
				textView.setText(getString(R.string.about));
				break;
			case 2:
				textView.setText("【近期降臨時間與攻略】");
				LinearLayout rootLL = (LinearLayout) rootView
						.findViewById(R.id.rootLL);
				TextView tvHeader2 = (TextView) inflater.inflate(
						R.layout.component_headertextview, null);
				tvHeader2.setText("【今日降臨！】\n*取自日文版攻略網\n點選連結會前往日版網站");
				LayoutParams params = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				params.setMargins(0, 30, 0, 0);
				tvHeader2.setLayoutParams(params);
				rootLL.addView(tvHeader2);
				MSWebView wv2 = new MSWebView(getActivity());
				wv2.loadDataWithBaseURL(
						"",
						"<table border=\"1\" style=\"border-collapse:collapse;\" borderColor=\"black\" >"
								+ document.select("table").get(2).html()
								+ "</table>", "text/html", "UTF-8", "");

				rootLL.addView(wv2);

				TextView tvHeader = (TextView) inflater.inflate(
						R.layout.component_headertextview, null);
				tvHeader.setText("【今後降臨時刻預定】\n*取自日文版攻略網\n點選連結會前往日版網站");
				tvHeader.setLayoutParams(params);
				rootLL.addView(tvHeader);
				MSWebView wv = new MSWebView(getActivity());
				wv.loadDataWithBaseURL(
						"",
						"<table border=\"1\" style=\"border-collapse:collapse;\" borderColor=\"black\" >"
								+ document.select("table").get(3).html()
								+ "</table>", "text/html", "UTF-8", "");
				rootLL.addView(wv);
				break;
			case 5:
				textView.setText("需要有穩定的網路連線才能點開圖鑑");
				MonsterArchive ma = new MonsterArchive();
				rootLL = (LinearLayout) rootView.findViewById(R.id.rootLL);

				LayoutParams param = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				param.setMargins(0, 0, 0, 0);
				rootLL.setLayoutParams(param);
				rootLL.addView(ma.getArchiveView(inflater, container,
						getActivity()),param);
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

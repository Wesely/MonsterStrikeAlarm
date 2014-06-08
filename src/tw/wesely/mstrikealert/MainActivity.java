package tw.wesely.mstrikealert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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


	// Title AsyncTask
	private class MonsterSite extends AsyncTask<Void, Void, Void> {
		String title;
		Document document;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
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
			String content = "";
			/** show table 0 & 1 **/
			// content = content + "get(0) = \n"
			// + document.select("table").get(0).text()
			// + "\n\nget(1) = \n"
			// + document.select("table").get(1).text();

			/** show table 2 **/
			// content = content + "\n\nget(2) = \n"
			// + document.select("table").get(2).html();

			content = content + parseTurtleTable(document);

			WebView wv = (WebView) findViewById(R.id.wv);
			wv.getSettings().setJavaScriptEnabled(true);
			//wv.loadDataWithBaseURL("", document.select("table").get(2).html(),
			//		"text/html", "UTF-8", "");
			// Set title into TextView
			TextView txttitle = (TextView) findViewById(R.id.section_label);
			txttitle.setText(content);
			mProgressDialog.dismiss();
		}
	}

	public String parseTurtleTable(Element document) {
		String content = "=========================\n";
		
		Elements tbls = document.getElementsByTag("table");
		for(Element tbl : tbls) {
			Element headline = tbl.previousElementSibling();
			while(headline != null) {
				if(headline.tagName() == "h2" || headline.tagName() == "h3")	break;
				headline = headline.previousElementSibling();
			}
			content += headline.text() + "\n";
			content += "----------------------\n";
			
			content += "| ";
			Elements fields = tbl.getElementsByTag("th");
			for(Element field : fields)
				content += field.text() + " | ";
			
			content += "\n";
			content += "| ";
			int cnt = 0;
			Elements datas = tbl.getElementsByTag("td");
			for(Element data : datas) {
				++cnt;
				content += data.text() + " | ";
				if(cnt%fields.size() == 0)	content += "\n";
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			TextView textView = (TextView) rootView
					.findViewById(R.id.section_label);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
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

package tw.wesely.archives;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tw.wesely.mstrikealarm.R;
import tw.wesely.mstrikealerm.MainActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class MonsterWebView extends WebView {

	String monsterID = "";

	public MonsterWebView(Context context) {
		super(context);
	}

	public void loadMonsterById(String id) {
		Log.d("MonsterWebView", "loadMonsterById , No." + id);
		monsterID = id;
		String url = "http://monst.appbank.net/monster/" + id + ".html";
		loadMonsterSection(url);
	}

	public void loadMonsterSection(String url) {
		new LoadDocument().execute(url);

	}

	public void loadTranslatedData(String data) {
		this.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
	}

	// Title AsyncTask
	private class LoadDocument extends AsyncTask<String, Document, Document> {
		@SuppressWarnings("unused")
		String title;
		ProgressDialog mProgressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(getContext());
			mProgressDialog.setTitle("載入圖鑑資料");
			mProgressDialog.setMessage("No." + monsterID + " ,載入中...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.show();
		}

		@Override
		protected Document doInBackground(String... urls) {
			int count = urls.length;
			Document document = null;
			for (int i = 0; i < count; i++) {
				try {
					// Connect to the web site
					document = Jsoup.connect(urls[i]).get();
					// Get the HTML document title
					title = document.title();

					// Replace all hypertext to absolute link
					// Elements links = document.getElementsByTag("a");
					// for (Element link : links)
					// link.attr("href",
					// "http://monst.appbank.net" + link.attr("href"));

					if (isCancelled())
						break;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
			return document;

		}

		@SuppressLint("SetJavaScriptEnabled")
		@Override
		protected void onPostExecute(Document document) {
			mProgressDialog.dismiss();
			Elements sections = document.getElementsByTag("section");
			for (Element section : sections) {
				Elements imgs = section.getElementsByTag("img");
				for (Element img : imgs)
					img.attr("src",
							"http://monst.appbank.net" + img.attr("src"));

			}

			Log.d("show html", sections.html());
			loadTranslatedData(sections.html());
		}

	}
}

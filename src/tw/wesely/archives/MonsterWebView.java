package tw.wesely.archives;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tw.wesely.mstrikealarm.R;
import tw.wesely.mstrikealerm.MainActivity;
import tw.wesely.translate.MStrans;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class MonsterWebView extends WebView {

	String monsterID = "";

	public MonsterWebView(Context context) {
		super(context);
		this.setWebViewClient(new MonsterArchiveWebViewClient());
	}

	private class MonsterArchiveWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	@Override
	public void loadUrl(String url){
		loadMonsterSection(url);
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

	public void loadTranslatedData(String data) throws IOException {
		this.loadDataWithBaseURL(null, new MStrans().getTranslated(data),
				"text/html", "utf-8", null);
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

					// Remove redundant information
					Elements bodies = document.getElementsByTag("body");
					for (Element body : bodies) {
						Element wrapper = body.getElementById("wrapper");
						for (Element element : wrapper.children())
							if (element.tagName() != "section") {
								Log.d("show remove", element.tagName());
								element.remove();
							}
					}
					
					// Replace all hypertext to absolute link
					Elements links = document.getElementsByAttribute("href");
					for (Element link : links)
						link.attr("href",
								"http://monst.appbank.net" + link.attr("href"));

					// Replace all hypertext to absolute link in div
					Element section = document.getElementById("monster");
					Elements divs = section.getElementsByClass("char");
					for (Element div : divs) {
						Element background = div.child(0);
						String attr = background.attr("style");
						Log.d("attr", attr);
						String url = attr.substring(attr.indexOf('(') + 2,
								attr.indexOf(')') - 2);
						Log.d("attr url", url);
						attr = attr.replace(url, "http://monst.appbank.net"
								+ url);
						Log.d("attr after", attr);
						background.attr("style", attr);
					}

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
				Element leftside = section.child(0);
				leftside.remove();
				Elements imgs = section.getElementsByTag("img");
				for (Element img : imgs)
					img.attr("src",
							"http://monst.appbank.net" + img.attr("src"));
			}

			Log.d("show html", document.html());
			try {
				loadTranslatedData(document.html());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}

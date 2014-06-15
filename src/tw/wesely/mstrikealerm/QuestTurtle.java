package tw.wesely.mstrikealerm;

import java.util.ArrayList;
import java.util.List;

import tw.wesely.mstrikealarm.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class QuestTurtle {
	String title;
	List<String> listTH, listTD;
	static boolean extended = false;

	public QuestTurtle(String title, List<String> listTH, List<String> listTD) {
		this.title = title;
		this.listTD = listTD;
		this.listTH = listTH;
	}

	public View getTurtleStageChartView(Context ctx) {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		int id = sharedPrefs.getInt("ID", -1);
		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_questchart, null);
		LinearLayout container = (LinearLayout) view
				.findViewById(R.id.container);
		TextView tvTitle = (TextView) view.findViewById(R.id.title);
		tvTitle.setText(this.title);
		int group = sharedPrefs.getInt("ID", 00) % (listTD.size());
		Log.d("getTurtleStageChartView", "id = " + sharedPrefs.getInt("ID", 00));
		Log.d("getTurtleStageChartView", "#group = " + (listTD.size()));

		final List<View> listNotMyGroup = new ArrayList<View>();
		for (int i = 0; i < listTD.size(); i++) {
			View subView = inflater.inflate(R.layout.component_textviewpair,
					null);
			TextView tvGroup = (TextView) subView.findViewById(R.id.tvTop);
			TextView tvTime = (TextView) subView.findViewById(R.id.tvBot);
			tvGroup.setText(listTH.get(i));
			tvTime.setText(listTD.get(i));
			if (id < 0) {

			} else if (i == group) {
				tvGroup.setTextColor(0xFF00BB00);
				tvTime.setTextColor(0xFF00BB00);
			} else {
				subView.setVisibility(View.GONE);
				listNotMyGroup.add(subView);
			}
			container.addView(subView);
		}

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 15, 0, 15);
		view.setLayoutParams(params);

		ImageButton btnExt = (ImageButton) view.findViewById(R.id.btnExt);
		btnExt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (extended) {
					for (int i = 0; i < listNotMyGroup.size(); i++)
						listNotMyGroup.get(i).setVisibility(View.GONE);
				} else {
					for (int i = 0; i < listNotMyGroup.size(); i++)
						listNotMyGroup.get(i).setVisibility(View.VISIBLE);
				}
				extended = !extended;
			}
		});

		return view;
	}
}

package tw.wesely.mstrikealerm;

import java.util.HashMap;
import java.util.List;

import tw.wesely.mstrikealarm.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TurtleQuest {
	String title;
	List<String> listTH, listTD;

	public TurtleQuest(String title, List<String> listTH, List<String> listTD) {
		this.title = title;
		this.listTD = listTD;
		this.listTH = listTH;
	}

	public View getTurtleStageChartView(Context ctx) {
		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_questchart, null);
		LinearLayout container = (LinearLayout) view
				.findViewById(R.id.container);
		TextView tvTitle = (TextView) view.findViewById(R.id.title);
		tvTitle.setText(this.title);
		for (int i = 0; i < listTD.size(); i++) {
			View subView = inflater.inflate(R.layout.component_textviewpair, null);
			TextView tvGroup = (TextView) subView.findViewById(R.id.tvTop);
			TextView tvTime = (TextView) subView.findViewById(R.id.tvBot);
			tvGroup.setText(listTH.get(i));
			tvTime.setText(listTD.get(i).replace("-", "~\n~"));
			container.addView(subView);
		}
		return view;
	}
}

package tw.wesely.mstrikealerm;

import java.util.ArrayList;
import java.util.List;

import tw.wesely.mstrikealarm.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class QuestBoss {

	String title;
	List<String> listTDTitle, listTDTime;
	static boolean extended = true;

	public QuestBoss(String title, List<String> listTDTitle, List<String> listTDTime) {
		this.title = title;
		this.listTDTitle = listTDTitle;
		this.listTDTime = listTDTime;
	}

	public View getTurtleStageChartView(Context ctx) {
		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_questchart, null);
		LinearLayout container = (LinearLayout) view
				.findViewById(R.id.container);
		TextView tvTitle = (TextView) view.findViewById(R.id.title);
		tvTitle.setText(this.title);

		final List<View> listNotMyGroup = new ArrayList<View>();
		for (int i = 0; i < listTDTime.size(); i++) {
			View subView = inflater.inflate(R.layout.component_textviewpairboss,
					null);
			TextView tvLeft = (TextView) subView.findViewById(R.id.tvLeft);
			TextView tvRight = (TextView) subView.findViewById(R.id.tvRight);
			tvLeft.setText(listTDTitle.get(i));
			tvRight.setText(listTDTime.get(i).replace(" ", "\n"));
			container.addView(subView);
			listNotMyGroup.add(subView);
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

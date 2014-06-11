package tw.wesely.mstrikealerm;

import tw.wesely.mstrikealarm.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class PlayerID {
	int ID = -1;
	AlertDialog dialog;

	public PlayerID() {
	}

	public AlertDialog getSetIDAlertDialog(ViewGroup container,
			final Context ctx) {
		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View contentView = inflater.inflate(R.layout.dialog_setgroup,
				container, false);
		final EditText etID = (EditText) contentView.findViewById(R.id.etID);
		// 產生一個Builder物件
		Builder builder = new AlertDialog.Builder(ctx);
		// 設定Dialog的標題
		builder.setTitle("設定組別");
		// 設定Dialog的內容
		builder.setView(contentView);
		// 設定Positive按鈕資料
		builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				SharedPreferences sharedPrefs = PreferenceManager
						.getDefaultSharedPreferences(ctx);
				Editor editor = sharedPrefs.edit();
				if (etID.getText().toString().length() != 2) {
					dialog.dismiss();
				} else if (Integer.valueOf(etID.getText().toString()) >= 0) {
					ID = Integer.valueOf(etID.getText().toString());
					editor.putInt("ID", ID);
					editor.commit();
					// MainActivity.setMainpageContent();
					MainActivity.setTextToMainPage("\n**目前設定的ID尾端兩碼為【" + ID
							+ "】");
				}
			}
		});
		// 設定Negative按鈕資料
		builder.setNegativeButton("先不要", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialog = builder.create();
		return dialog;
	}

	public int getPlayerID() {
		return ID;
	}
}

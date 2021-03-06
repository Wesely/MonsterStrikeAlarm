package tw.wesely.archives;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.wesely.mstrikealarm.R;
import tw.wesely.mstrikealerm.MainActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MonsterArchive extends Activity {
	GridView gvArchive;
	Context ctx;
	Button btnSearch;
	EditText etID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_archive);
		ctx = MonsterArchive.this;
		btnSearch = (Button) findViewById(R.id.btnSearch);
		etID = (EditText) findViewById(R.id.etID);
		gvArchive = (GridView) findViewById(R.id.gvArchive);
		loadArchive();
		gvArchive.setFastScrollEnabled(true);
		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View btn) {
				String id = etID.getText().toString();
				Log.d("MonsterArchive",
						"start Activity with monster ID = No."
								+ id);
				Intent intent = new Intent(ctx, ArchiveDetailActivity.class);
				intent.putExtra("monsterID", id);
				ctx.startActivity(intent);
			}
		});

	}

	ProgressDialog mProgressDialog;

	private void loadArchive() {
		mProgressDialog = ProgressDialog.show(this, "Loading...", "正在吃記憶體...",
				true);

		new Thread() {
			@Override
			public void run() {

				try {
					Thread.sleep(2000);
					runOnUiThread(new Runnable() {

						public void run() {
							List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
							for (int i = 0; i < image.length; i++) {
								mProgressDialog.setProgress(i / image.length);
								Map<String, Object> item = new HashMap<String, Object>();
								item.put("image", image[i]);
								item.put("text", imgText[i]);
								items.add(item);
							}
							Log.d("Time-after for", System.currentTimeMillis()
									+ "");
							gvArchive
									.setAdapter(new ArchiveGridViewAdapter(ctx));
							gvArchive
									.setOnItemClickListener(new OnItemClickListener() {
										public void onItemClick(
												AdapterView<?> parent,
												View view, int position, long id) {
											String monsterID = imgText[position]
													.replace("No.", "");
											Log.d("MonsterArchive",
													"start Activity with monster ID = No."
															+ monsterID);
											Intent intent = new Intent(ctx,
													ArchiveDetailActivity.class);
											intent.putExtra("monsterID",
													monsterID);
											ctx.startActivity(intent);
										}
									});
							mProgressDialog.dismiss();
						}

					});

					Log.d("Time end", System.currentTimeMillis() + "");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

	}

	public class ArchiveGridViewAdapter extends ArrayAdapter<Object> {
		Context context;

		public ArchiveGridViewAdapter(Context context) {
			super(context, 0);
			this.context = context;
		}

		@Override
		public int getCount() {
			return imgText.length;
		}

		@Override
		public long getItemId(int pos) {
			String monsterID = imgText[pos].replace("No.", "");
			return Integer.valueOf(monsterID);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			View row = inflater.inflate(R.layout.grid_item, parent, false);
			ImageView iv = (ImageView) row.findViewById(R.id.image);
			iv.setImageResource(image[position]);
			TextView tv = (TextView) row.findViewById(R.id.text);
			tv.setText(imgText[position]);
			return row;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Log.d("onKeyDown", "BACK");
			Intent intent = new Intent(ctx, MainActivity.class);
			ctx.startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private int[] image = { R.drawable.icon_1, R.drawable.icon_2,
			R.drawable.icon_3, R.drawable.icon_4, R.drawable.icon_5,
			R.drawable.icon_6, R.drawable.icon_7, R.drawable.icon_8,
			R.drawable.icon_9, R.drawable.icon_10, R.drawable.icon_11,
			R.drawable.icon_12, R.drawable.icon_13, R.drawable.icon_14,
			R.drawable.icon_15, R.drawable.icon_16, R.drawable.icon_17,
			R.drawable.icon_18, R.drawable.icon_19, R.drawable.icon_20,
			R.drawable.icon_21, R.drawable.icon_22, R.drawable.icon_23,
			R.drawable.icon_24, R.drawable.icon_25, R.drawable.icon_26,
			R.drawable.icon_27, R.drawable.icon_28, R.drawable.icon_29,
			R.drawable.icon_30, R.drawable.icon_31, R.drawable.icon_32,
			R.drawable.icon_33, R.drawable.icon_34, R.drawable.icon_35,
			R.drawable.icon_36, R.drawable.icon_37, R.drawable.icon_38,
			R.drawable.icon_39, R.drawable.icon_40, R.drawable.icon_41,
			R.drawable.icon_42, R.drawable.icon_43, R.drawable.icon_44,
			R.drawable.icon_45, R.drawable.icon_56, R.drawable.icon_57,
			R.drawable.icon_58, R.drawable.icon_59, R.drawable.icon_60,
			R.drawable.icon_61, R.drawable.icon_62, R.drawable.icon_63,
			R.drawable.icon_64, R.drawable.icon_65, R.drawable.icon_66,
			R.drawable.icon_67, R.drawable.icon_68, R.drawable.icon_69,
			R.drawable.icon_70, R.drawable.icon_71, R.drawable.icon_72,
			R.drawable.icon_73, R.drawable.icon_74, R.drawable.icon_75,
			R.drawable.icon_76, R.drawable.icon_77, R.drawable.icon_78,
			R.drawable.icon_79, R.drawable.icon_80, R.drawable.icon_81,
			R.drawable.icon_82, R.drawable.icon_83, R.drawable.icon_84,
			R.drawable.icon_85, R.drawable.icon_86, R.drawable.icon_87,
			R.drawable.icon_88, R.drawable.icon_89, R.drawable.icon_90,
			R.drawable.icon_91, R.drawable.icon_92, R.drawable.icon_93,
			R.drawable.icon_94, R.drawable.icon_95, R.drawable.icon_96,
			R.drawable.icon_97, R.drawable.icon_98, R.drawable.icon_99,
			R.drawable.icon_100, R.drawable.icon_101, R.drawable.icon_102,
			R.drawable.icon_103, R.drawable.icon_104, R.drawable.icon_105,
			R.drawable.icon_106, R.drawable.icon_107, R.drawable.icon_108,
			R.drawable.icon_109, R.drawable.icon_110, R.drawable.icon_111,
			R.drawable.icon_112, R.drawable.icon_113, R.drawable.icon_114,
			R.drawable.icon_115, R.drawable.icon_116, R.drawable.icon_117,
			R.drawable.icon_118, R.drawable.icon_119, R.drawable.icon_120,
			R.drawable.icon_121, R.drawable.icon_122, R.drawable.icon_123,
			R.drawable.icon_124, R.drawable.icon_125, R.drawable.icon_126,
			R.drawable.icon_127, R.drawable.icon_128, R.drawable.icon_129,
			R.drawable.icon_130, R.drawable.icon_131, R.drawable.icon_132,
			R.drawable.icon_133, R.drawable.icon_134, R.drawable.icon_135,
			R.drawable.icon_136, R.drawable.icon_137, R.drawable.icon_138,
			R.drawable.icon_139, R.drawable.icon_140, R.drawable.icon_141,
			R.drawable.icon_142, R.drawable.icon_143, R.drawable.icon_144,
			R.drawable.icon_145, R.drawable.icon_146, R.drawable.icon_147,
			R.drawable.icon_148, R.drawable.icon_149, R.drawable.icon_150,
			R.drawable.icon_151, R.drawable.icon_152, R.drawable.icon_153,
			R.drawable.icon_154, R.drawable.icon_155, R.drawable.icon_156,
			R.drawable.icon_157, R.drawable.icon_158, R.drawable.icon_159,
			R.drawable.icon_160, R.drawable.icon_161, R.drawable.icon_162,
			R.drawable.icon_163, R.drawable.icon_164, R.drawable.icon_165,
			R.drawable.icon_166, R.drawable.icon_167, R.drawable.icon_168,
			R.drawable.icon_169, R.drawable.icon_170, R.drawable.icon_171,
			R.drawable.icon_172, R.drawable.icon_173, R.drawable.icon_174,
			R.drawable.icon_175, R.drawable.icon_176, R.drawable.icon_177,
			R.drawable.icon_178, R.drawable.icon_179, R.drawable.icon_180,
			R.drawable.icon_181, R.drawable.icon_182, R.drawable.icon_183,
			R.drawable.icon_184, R.drawable.icon_185, R.drawable.icon_186,
			R.drawable.icon_187, R.drawable.icon_188, R.drawable.icon_189,
			R.drawable.icon_190, R.drawable.icon_191, R.drawable.icon_192,
			R.drawable.icon_193, R.drawable.icon_194, R.drawable.icon_195,
			R.drawable.icon_196, R.drawable.icon_197, R.drawable.icon_198,
			R.drawable.icon_199, R.drawable.icon_200, R.drawable.icon_201,
			R.drawable.icon_202, R.drawable.icon_203, R.drawable.icon_204,
			R.drawable.icon_205, R.drawable.icon_206, R.drawable.icon_209,
			R.drawable.icon_210, R.drawable.icon_216, R.drawable.icon_217,
			R.drawable.icon_218, R.drawable.icon_219, R.drawable.icon_220,
			R.drawable.icon_221, R.drawable.icon_222, R.drawable.icon_223,
			R.drawable.icon_224, R.drawable.icon_225, R.drawable.icon_226,
			R.drawable.icon_227, R.drawable.icon_228, R.drawable.icon_229,
			R.drawable.icon_230, R.drawable.icon_231, R.drawable.icon_232,
			R.drawable.icon_233, R.drawable.icon_234, R.drawable.icon_235,
			R.drawable.icon_236, R.drawable.icon_237, R.drawable.icon_238,
			R.drawable.icon_239, R.drawable.icon_240, R.drawable.icon_241,
			R.drawable.icon_242, R.drawable.icon_243, R.drawable.icon_244,
			R.drawable.icon_245, R.drawable.icon_246, R.drawable.icon_247,
			R.drawable.icon_248, R.drawable.icon_249, R.drawable.icon_250,
			R.drawable.icon_251, R.drawable.icon_252, R.drawable.icon_253,
			R.drawable.icon_254, R.drawable.icon_255, R.drawable.icon_256,
			R.drawable.icon_257, R.drawable.icon_258, R.drawable.icon_259,
			R.drawable.icon_260, R.drawable.icon_261, R.drawable.icon_262,
			R.drawable.icon_263, R.drawable.icon_264, R.drawable.icon_265,
			R.drawable.icon_266, R.drawable.icon_267, R.drawable.icon_268,
			R.drawable.icon_269, R.drawable.icon_270, R.drawable.icon_271,
			R.drawable.icon_272, R.drawable.icon_273, R.drawable.icon_274,
			R.drawable.icon_275, R.drawable.icon_276, R.drawable.icon_277,
			R.drawable.icon_278, R.drawable.icon_279, R.drawable.icon_280,
			R.drawable.icon_281, R.drawable.icon_282, R.drawable.icon_283,
			R.drawable.icon_284, R.drawable.icon_285, R.drawable.icon_286,
			R.drawable.icon_287, R.drawable.icon_288, R.drawable.icon_289,
			R.drawable.icon_290, R.drawable.icon_291, R.drawable.icon_292,
			R.drawable.icon_293, R.drawable.icon_294, R.drawable.icon_295,
			R.drawable.icon_296, R.drawable.icon_297, R.drawable.icon_298,
			R.drawable.icon_299, R.drawable.icon_300, R.drawable.icon_301,
			R.drawable.icon_302, R.drawable.icon_303, R.drawable.icon_304,
			R.drawable.icon_305, R.drawable.icon_306, R.drawable.icon_307,
			R.drawable.icon_308, R.drawable.icon_309, R.drawable.icon_310,
			R.drawable.icon_311, R.drawable.icon_312, R.drawable.icon_313,
			R.drawable.icon_314, R.drawable.icon_315, R.drawable.icon_316,
			R.drawable.icon_317, R.drawable.icon_318, R.drawable.icon_319,
			R.drawable.icon_320, R.drawable.icon_321, R.drawable.icon_322,
			R.drawable.icon_323, R.drawable.icon_324, R.drawable.icon_325,
			R.drawable.icon_326, R.drawable.icon_327, R.drawable.icon_328,
			R.drawable.icon_329, R.drawable.icon_330, R.drawable.icon_331,
			R.drawable.icon_332, R.drawable.icon_333, R.drawable.icon_334,
			R.drawable.icon_335, R.drawable.icon_336, R.drawable.icon_337,
			R.drawable.icon_338, R.drawable.icon_339, R.drawable.icon_340,
			R.drawable.icon_341, R.drawable.icon_342, R.drawable.icon_343,
			R.drawable.icon_344, R.drawable.icon_345, R.drawable.icon_346,
			R.drawable.icon_347, R.drawable.icon_348, R.drawable.icon_349,
			R.drawable.icon_350, R.drawable.icon_351, R.drawable.icon_352,
			R.drawable.icon_353, R.drawable.icon_354, R.drawable.icon_355,
			R.drawable.icon_356, R.drawable.icon_357, R.drawable.icon_358,
			R.drawable.icon_359, R.drawable.icon_360, R.drawable.icon_361,
			R.drawable.icon_362, R.drawable.icon_363, R.drawable.icon_364,
			R.drawable.icon_365, R.drawable.icon_366, R.drawable.icon_367,
			R.drawable.icon_368, R.drawable.icon_369, R.drawable.icon_370,
			R.drawable.icon_371, R.drawable.icon_372, R.drawable.icon_373,
			R.drawable.icon_374, R.drawable.icon_375, R.drawable.icon_376,
			R.drawable.icon_377, R.drawable.icon_378, R.drawable.icon_379,
			R.drawable.icon_380, R.drawable.icon_381, R.drawable.icon_382,
			R.drawable.icon_383, R.drawable.icon_384, R.drawable.icon_385,
			R.drawable.icon_386, R.drawable.icon_387, R.drawable.icon_388,
			R.drawable.icon_389, R.drawable.icon_390, R.drawable.icon_391,
			R.drawable.icon_392, R.drawable.icon_393, R.drawable.icon_394,
			R.drawable.icon_395, R.drawable.icon_396, R.drawable.icon_397,
			R.drawable.icon_398, R.drawable.icon_399, R.drawable.icon_400,
			R.drawable.icon_401, R.drawable.icon_402, R.drawable.icon_403,
			R.drawable.icon_404, R.drawable.icon_405, R.drawable.icon_406,
			R.drawable.icon_407, R.drawable.icon_408, R.drawable.icon_409,
			R.drawable.icon_410, R.drawable.icon_411, R.drawable.icon_412,
			R.drawable.icon_413, R.drawable.icon_414, R.drawable.icon_415,
			R.drawable.icon_416, R.drawable.icon_417, R.drawable.icon_418,
			R.drawable.icon_419, R.drawable.icon_420, R.drawable.icon_421,
			R.drawable.icon_422, R.drawable.icon_423, R.drawable.icon_424,
			R.drawable.icon_425, R.drawable.icon_426, R.drawable.icon_427,
			R.drawable.icon_428, R.drawable.icon_429, R.drawable.icon_430,
			R.drawable.icon_431, R.drawable.icon_432, R.drawable.icon_433,
			R.drawable.icon_434, R.drawable.icon_435, R.drawable.icon_436,
			R.drawable.icon_437, R.drawable.icon_438, R.drawable.icon_439,
			R.drawable.icon_440, R.drawable.icon_441, R.drawable.icon_442,
			R.drawable.icon_443, R.drawable.icon_444, R.drawable.icon_445,
			R.drawable.icon_446, R.drawable.icon_447, R.drawable.icon_448,
			R.drawable.icon_449, R.drawable.icon_450, R.drawable.icon_451,
			R.drawable.icon_452, R.drawable.icon_453, R.drawable.icon_454,
			R.drawable.icon_455, R.drawable.icon_456, R.drawable.icon_457,
			R.drawable.icon_458, R.drawable.icon_459, R.drawable.icon_460,
			R.drawable.icon_461, R.drawable.icon_462, R.drawable.icon_463,
			R.drawable.icon_464, R.drawable.icon_465, R.drawable.icon_466,
			R.drawable.icon_467, R.drawable.icon_468, R.drawable.icon_469,
			R.drawable.icon_470, R.drawable.icon_471, R.drawable.icon_472,
			R.drawable.icon_473, R.drawable.icon_474, R.drawable.icon_475,
			R.drawable.icon_476, R.drawable.icon_477, R.drawable.icon_478,
			R.drawable.icon_479, R.drawable.icon_480, R.drawable.icon_481,
			R.drawable.icon_482, R.drawable.icon_483, R.drawable.icon_484,
			R.drawable.icon_485, R.drawable.icon_486, R.drawable.icon_487,
			R.drawable.icon_488, R.drawable.icon_489, R.drawable.icon_490,
			R.drawable.icon_491, R.drawable.icon_492, R.drawable.icon_493,
			R.drawable.icon_494, R.drawable.icon_495, R.drawable.icon_496,
			R.drawable.icon_497, R.drawable.icon_498, R.drawable.icon_499,
			R.drawable.icon_500, R.drawable.icon_501, R.drawable.icon_502,
			R.drawable.icon_503, R.drawable.icon_504, R.drawable.icon_505,
			R.drawable.icon_506, R.drawable.icon_507, R.drawable.icon_508,
			R.drawable.icon_509, R.drawable.icon_510, R.drawable.icon_511,
			R.drawable.icon_512, R.drawable.icon_513, R.drawable.icon_514,
			R.drawable.icon_515, R.drawable.icon_516, R.drawable.icon_517,
			R.drawable.icon_518, R.drawable.icon_519, R.drawable.icon_520,
			R.drawable.icon_521, R.drawable.icon_522, R.drawable.icon_523,
			R.drawable.icon_524, R.drawable.icon_525, R.drawable.icon_526,
			R.drawable.icon_527, R.drawable.icon_528, R.drawable.icon_529,
			R.drawable.icon_530, R.drawable.icon_531, R.drawable.icon_532,
			R.drawable.icon_533, R.drawable.icon_534, R.drawable.icon_535,
			R.drawable.icon_536, R.drawable.icon_537, R.drawable.icon_538,
			R.drawable.icon_539, R.drawable.icon_540, R.drawable.icon_541,
			R.drawable.icon_542, R.drawable.icon_543, R.drawable.icon_544,
			R.drawable.icon_545, R.drawable.icon_547, R.drawable.icon_548,
			R.drawable.icon_549, R.drawable.icon_551, R.drawable.icon_552,
			R.drawable.icon_553, R.drawable.icon_555, R.drawable.icon_556,
			R.drawable.icon_557, R.drawable.icon_559, R.drawable.icon_560,
			R.drawable.icon_561, R.drawable.icon_562, R.drawable.icon_563,
			R.drawable.icon_564, R.drawable.icon_565, R.drawable.icon_566,
			R.drawable.icon_567, R.drawable.icon_568, R.drawable.icon_569,
			R.drawable.icon_570, R.drawable.icon_571, R.drawable.icon_572,
			R.drawable.icon_573, R.drawable.icon_574, R.drawable.icon_575,
			R.drawable.icon_576, R.drawable.icon_577, R.drawable.icon_578,
			R.drawable.icon_579, R.drawable.icon_580, R.drawable.icon_581,
			R.drawable.icon_582, R.drawable.icon_583, R.drawable.icon_584,
			R.drawable.icon_585, R.drawable.icon_586, R.drawable.icon_587,
			R.drawable.icon_588, R.drawable.icon_589, R.drawable.icon_590,
			R.drawable.icon_591, R.drawable.icon_592, R.drawable.icon_593,
			R.drawable.icon_594, R.drawable.icon_595, R.drawable.icon_596,
			R.drawable.icon_597, R.drawable.icon_598, R.drawable.icon_599,
			R.drawable.icon_600, R.drawable.icon_601, R.drawable.icon_602,
			R.drawable.icon_603, R.drawable.icon_604, R.drawable.icon_605,
			R.drawable.icon_606, R.drawable.icon_607, R.drawable.icon_608,
			R.drawable.icon_609, R.drawable.icon_610, R.drawable.icon_611,
			R.drawable.icon_612, R.drawable.icon_613, R.drawable.icon_614,
			R.drawable.icon_615, R.drawable.icon_616, R.drawable.icon_617,
			R.drawable.icon_618, R.drawable.icon_619, R.drawable.icon_620,
			R.drawable.icon_621, R.drawable.icon_622, R.drawable.icon_623,
			R.drawable.icon_624, R.drawable.icon_625, R.drawable.icon_626,
			R.drawable.icon_627, R.drawable.icon_628, R.drawable.icon_629,
			R.drawable.icon_630, R.drawable.icon_631, R.drawable.icon_632,
			R.drawable.icon_633, R.drawable.icon_634, R.drawable.icon_635,
			R.drawable.icon_636, R.drawable.icon_637, R.drawable.icon_638,
			R.drawable.icon_639, R.drawable.icon_640, R.drawable.icon_641,
			R.drawable.icon_642, R.drawable.icon_643, };

	private String[] imgText = { "No.1", "No.2", "No.3", "No.4", "No.5",
			"No.6", "No.7", "No.8", "No.9", "No.10", "No.11", "No.12", "No.13",
			"No.14", "No.15", "No.16", "No.17", "No.18", "No.19", "No.20",
			"No.21", "No.22", "No.23", "No.24", "No.25", "No.26", "No.27",
			"No.28", "No.29", "No.30", "No.31", "No.32", "No.33", "No.34",
			"No.35", "No.36", "No.37", "No.38", "No.39", "No.40", "No.41",
			"No.42", "No.43", "No.44", "No.45", "No.56", "No.57", "No.58",
			"No.59", "No.60", "No.61", "No.62", "No.63", "No.64", "No.65",
			"No.66", "No.67", "No.68", "No.69", "No.70", "No.71", "No.72",
			"No.73", "No.74", "No.75", "No.76", "No.77", "No.78", "No.79",
			"No.80", "No.81", "No.82", "No.83", "No.84", "No.85", "No.86",
			"No.87", "No.88", "No.89", "No.90", "No.91", "No.92", "No.93",
			"No.94", "No.95", "No.96", "No.97", "No.98", "No.99", "No.100",
			"No.101", "No.102", "No.103", "No.104", "No.105", "No.106",
			"No.107", "No.108", "No.109", "No.110", "No.111", "No.112",
			"No.113", "No.114", "No.115", "No.116", "No.117", "No.118",
			"No.119", "No.120", "No.121", "No.122", "No.123", "No.124",
			"No.125", "No.126", "No.127", "No.128", "No.129", "No.130",
			"No.131", "No.132", "No.133", "No.134", "No.135", "No.136",
			"No.137", "No.138", "No.139", "No.140", "No.141", "No.142",
			"No.143", "No.144", "No.145", "No.146", "No.147", "No.148",
			"No.149", "No.150", "No.151", "No.152", "No.153", "No.154",
			"No.155", "No.156", "No.157", "No.158", "No.159", "No.160",
			"No.161", "No.162", "No.163", "No.164", "No.165", "No.166",
			"No.167", "No.168", "No.169", "No.170", "No.171", "No.172",
			"No.173", "No.174", "No.175", "No.176", "No.177", "No.178",
			"No.179", "No.180", "No.181", "No.182", "No.183", "No.184",
			"No.185", "No.186", "No.187", "No.188", "No.189", "No.190",
			"No.191", "No.192", "No.193", "No.194", "No.195", "No.196",
			"No.197", "No.198", "No.199", "No.200", "No.201", "No.202",
			"No.203", "No.204", "No.205", "No.206", "No.209", "No.210",
			"No.216", "No.217", "No.218", "No.219", "No.220", "No.221",
			"No.222", "No.223", "No.224", "No.225", "No.226", "No.227",
			"No.228", "No.229", "No.230", "No.231", "No.232", "No.233",
			"No.234", "No.235", "No.236", "No.237", "No.238", "No.239",
			"No.240", "No.241", "No.242", "No.243", "No.244", "No.245",
			"No.246", "No.247", "No.248", "No.249", "No.250", "No.251",
			"No.252", "No.253", "No.254", "No.255", "No.256", "No.257",
			"No.258", "No.259", "No.260", "No.261", "No.262", "No.263",
			"No.264", "No.265", "No.266", "No.267", "No.268", "No.269",
			"No.270", "No.271", "No.272", "No.273", "No.274", "No.275",
			"No.276", "No.277", "No.278", "No.279", "No.280", "No.281",
			"No.282", "No.283", "No.284", "No.285", "No.286", "No.287",
			"No.288", "No.289", "No.290", "No.291", "No.292", "No.293",
			"No.294", "No.295", "No.296", "No.297", "No.298", "No.299",
			"No.300", "No.301", "No.302", "No.303", "No.304", "No.305",
			"No.306", "No.307", "No.308", "No.309", "No.310", "No.311",
			"No.312", "No.313", "No.314", "No.315", "No.316", "No.317",
			"No.318", "No.319", "No.320", "No.321", "No.322", "No.323",
			"No.324", "No.325", "No.326", "No.327", "No.328", "No.329",
			"No.330", "No.331", "No.332", "No.333", "No.334", "No.335",
			"No.336", "No.337", "No.338", "No.339", "No.340", "No.341",
			"No.342", "No.343", "No.344", "No.345", "No.346", "No.347",
			"No.348", "No.349", "No.350", "No.351", "No.352", "No.353",
			"No.354", "No.355", "No.356", "No.357", "No.358", "No.359",
			"No.360", "No.361", "No.362", "No.363", "No.364", "No.365",
			"No.366", "No.367", "No.368", "No.369", "No.370", "No.371",
			"No.372", "No.373", "No.374", "No.375", "No.376", "No.377",
			"No.378", "No.379", "No.380", "No.381", "No.382", "No.383",
			"No.384", "No.385", "No.386", "No.387", "No.388", "No.389",
			"No.390", "No.391", "No.392", "No.393", "No.394", "No.395",
			"No.396", "No.397", "No.398", "No.399", "No.400", "No.401",
			"No.402", "No.403", "No.404", "No.405", "No.406", "No.407",
			"No.408", "No.409", "No.410", "No.411", "No.412", "No.413",
			"No.414", "No.415", "No.416", "No.417", "No.418", "No.419",
			"No.420", "No.421", "No.422", "No.423", "No.424", "No.425",
			"No.426", "No.427", "No.428", "No.429", "No.430", "No.431",
			"No.432", "No.433", "No.434", "No.435", "No.436", "No.437",
			"No.438", "No.439", "No.440", "No.441", "No.442", "No.443",
			"No.444", "No.445", "No.446", "No.447", "No.448", "No.449",
			"No.450", "No.451", "No.452", "No.453", "No.454", "No.455",
			"No.456", "No.457", "No.458", "No.459", "No.460", "No.461",
			"No.462", "No.463", "No.464", "No.465", "No.466", "No.467",
			"No.468", "No.469", "No.470", "No.471", "No.472", "No.473",
			"No.474", "No.475", "No.476", "No.477", "No.478", "No.479",
			"No.480", "No.481", "No.482", "No.483", "No.484", "No.485",
			"No.486", "No.487", "No.488", "No.489", "No.490", "No.491",
			"No.492", "No.493", "No.494", "No.495", "No.496", "No.497",
			"No.498", "No.499", "No.500", "No.501", "No.502", "No.503",
			"No.504", "No.505", "No.506", "No.507", "No.508", "No.509",
			"No.510", "No.511", "No.512", "No.513", "No.514", "No.515",
			"No.516", "No.517", "No.518", "No.519", "No.520", "No.521",
			"No.522", "No.523", "No.524", "No.525", "No.526", "No.527",
			"No.528", "No.529", "No.530", "No.531", "No.532", "No.533",
			"No.534", "No.535", "No.536", "No.537", "No.538", "No.539",
			"No.540", "No.541", "No.542", "No.543", "No.544", "No.545",
			"No.547", "No.548", "No.549", "No.551", "No.552", "No.553",
			"No.555", "No.556", "No.557", "No.559", "No.560", "No.561",
			"No.562", "No.563", "No.564", "No.565", "No.566", "No.567",
			"No.568", "No.569", "No.570", "No.571", "No.572", "No.573",
			"No.574", "No.575", "No.576", "No.577", "No.578", "No.579",
			"No.580", "No.581", "No.582", "No.583", "No.584", "No.585",
			"No.586", "No.587", "No.588", "No.589", "No.590", "No.591",
			"No.592", "No.593", "No.594", "No.595", "No.596", "No.597",
			"No.598", "No.599", "No.600", "No.601", "No.602", "No.603",
			"No.604", "No.605", "No.606", "No.607", "No.608", "No.609",
			"No.610", "No.611", "No.612", "No.613", "No.614", "No.615",
			"No.616", "No.617", "No.618", "No.619", "No.620", "No.621",
			"No.622", "No.623", "No.624", "No.625", "No.626", "No.627",
			"No.628", "No.629", "No.630", "No.631", "No.632", "No.633",
			"No.634", "No.635", "No.636", "No.637", "No.638", "No.639",
			"No.640", "No.641", "No.642", "No.643", };
}

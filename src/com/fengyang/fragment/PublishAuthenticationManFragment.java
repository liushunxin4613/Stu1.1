package com.fengyang.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fengyang.activity.ChooseCollegeActivity;
import com.fengyang.adapter.VolleyErrorAdapter;
import com.fengyang.db.CollegeDAO;
import com.fengyang.dialog.ChooseListDialog;
import com.fengyang.dialog.LoadingDialog;
import com.fengyang.dialog.SingleChoiceDialog;
import com.fengyang.dialog.SingleChoiceDialog.OnItemSelectedListener;
import com.fengyang.entity.AppStudentBench;
import com.fengyang.entity.OubaArea;
import com.fengyang.entity.AppCollege;
import com.fengyang.entity.User;
import com.fengyang.entity.UserDetail;
import com.fengyang.model.Json;
import com.fengyang.service.InitUserService;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.stu.StuApplication;
import com.fengyang.util.Config;
import com.fengyang.util.FormatUtils;
import com.fengyang.util.IndentityCard;
import com.fengyang.util.UriPrase;
import com.fengyang.util.ui.UIUtils;
import com.fengyang.volleyTool.MultipartRequest;
import com.fengyang.volleyTool.MultipartRequestParams;

/***
 * 
 * @author LiuShunxin
 *
 */
public class PublishAuthenticationManFragment extends Fragment implements
		OnClickListener {

	private String[] sexStr;
	// 身份证正面照view，学生证正面照view
	private ImageButton IdCardPhotoView;
	private ImageButton stuCardPhotoView;
	// 身份证正面照id，学生证正面照id
	private EditText cardIdEt;
	private EditText studentIdEt;

	// 省份
	private OubaArea province;
	// 大学
	private AppCollege college;
	// 地址选择TV，Ley
	private TextView locationTv;
	private LinearLayout locationLy;

	private String TAG_PUBLISH_MAN = "Publishstu";

	// 提交，获取验证码按钮
	private Button submitButton;

	private TextView sexTv;
	private LinearLayout sexLy;
	private SingleChoiceDialog sexDialog;
	// 姓名，电话
	private EditText nameEt;
	/**
	 * 拍照暂存的uri
	 */
	private Uri tempUri;
	/**
	 * 拍照暂存的file
	 */
	private File tempFile;
	/**
	 * 截图后保存图片的Uri
	 */
	private Uri cropUri;

	private Uri IdCardPhotoUri;
	private Uri stuCardPhotoUri;
	/**
	 * 选择图片类型，是身份证还是营业执照
	 */
	private int chooseType;
	/**
	 * 选择类型，身份证
	 */
	public static final int CHOOSE_TYPE_IDCARD = 0;
	/**
	 * 选择类型，学生证
	 */
	public static final int CHOOSE_TYPE_STUCARD = 1;

//	private PersonDialog personDialog;
	private ChooseListDialog choosePicDialog;
	public static final String TAG = "PublishstuManFragment";
	public static final String KEY_URI_DATA_ID = "uriIDCard";
	public static final String KEY_URI_DATA_STU = "uriStuCard";
	public static final int REQUEST_CHOOSE_IMAGE = 0;
	public static final int REQUEST_TAKE_PHOTO = 1;
	public static final int REQUEST_CROP_IMAGE = 3;
	public static final int REQUEST_CHOOSE_LOCATION = 4;

	private static PublishAuthenticationManFragment fragment = null;

	private AppStudentBench studentBench;
	private StuApplication application;
	private User user;
	private UserDetail userDetail;
	private RequestQueue mQueue;
	private LoadingDialog dialog;

	private Integer SEX = null;
	DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialogInterface, int i) {
			SEX = i;
			sexStr = getResources().getStringArray(R.array.sex_choice_array);
			if (null != SEX) {
				sexTv.setText(sexStr[SEX]);
			}
		}
	};

	public static Fragment getInstance() {
		if (fragment == null)
			fragment = new PublishAuthenticationManFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_authentication_man,
				container, false);

		ListenThis(rootView);
		init();

		if (savedInstanceState != null) {
			IdCardPhotoUri = savedInstanceState.getParcelable(KEY_URI_DATA_ID);
			stuCardPhotoUri = savedInstanceState
					.getParcelable(KEY_URI_DATA_STU);

			// 计算出控件的大致长度，屏幕宽度-padding
			int len = (UIUtils.getDisplayWidth(getActivity()) - UIUtils.dip2px(
					getActivity(), 30)) / 2;
			if (IdCardPhotoUri != null)
				IdCardPhotoView.setImageBitmap(UIUtils.loadBitmap(
						getActivity(), IdCardPhotoUri, len, len));
			if (stuCardPhotoUri != null)
				stuCardPhotoView.setImageBitmap(UIUtils.loadBitmap(
						getActivity(), stuCardPhotoUri, len, len));
		}

		return rootView;
	}

	private void init() {
		application = (StuApplication) getActivity().getApplication();
		user = application.getUser().copy();
		userDetail = application.getUserDetail().copy();
		mQueue = Volley.newRequestQueue(getActivity());

		sexStr=getResources().getStringArray(R.array.sex_choice_array);
		if (null == userDetail.getSex()) {
			if (SEX != null) {
				userDetail.setSex(SEX);
			}
		} else {
			SEX = userDetail.getSex();
			sexTv.setHint(sexStr[SEX]);
		}
		
		
		if (null == userDetail.getTrueName()) {
			if (nameEt.getText().length() != 0) {
				userDetail.setTrueName(nameEt.getText().toString());
			}
		} else {
			if (userDetail.getTrueName().length() != 0) {
				nameEt.setHint(userDetail.getTrueName());
			}
		}
		
	}
	
	/**
	 * 填充学生数据
	 */
	private void fillStudent() {
		studentBench = new AppStudentBench();
		studentBench.setIdcard(cardIdEt.getText().toString());
		studentBench.setStuId(studentIdEt.getText().toString());
		studentBench.setOubaArea(province);
		studentBench.setAppCollege(college);
		
		Timestamp ts = new Timestamp(
				new Date(System.currentTimeMillis()).getTime());
		studentBench.setApplyDate(ts);
		
		studentBench.setTrueName(userDetail.getTrueName());
		userDetail.setTrueName(userDetail.getTrueName());
		
		user.setUserDetail(userDetail);
		
		studentBench.setOubaMember(user);
	}

	public void ListenThis(View rootView) {
		// 监听及初始化控件
		IdCardPhotoView = (ImageButton) rootView
				.findViewById(R.id.authentication_stu_idCard_photo);
		stuCardPhotoView = (ImageButton) rootView
				.findViewById(R.id.authentication_stu_stuCard_photo);
		locationTv = (TextView) rootView
				.findViewById(R.id.Tv_publish_authentication_man_location_show);
		locationLy = (LinearLayout) rootView
				.findViewById(R.id.publish_authentication_man_location);
		submitButton = (Button) rootView
				.findViewById(R.id.Bt_authentication_man_submit);
		sexTv = (TextView) rootView
				.findViewById(R.id.Tv_authentication_user_stu_sex);
		sexLy = (LinearLayout) rootView
				.findViewById(R.id.Ly_authentication_user_stu_sex);
		nameEt = (EditText) rootView
				.findViewById(R.id.Et_publish_part_input_man_name);
		cardIdEt = (EditText) rootView
				.findViewById(R.id.Et_item_authentication_man_idcard);
		studentIdEt = (EditText) rootView
				.findViewById(R.id.Et_item_authentication_man_studentcard);

		sexLy.setOnClickListener(this);
		locationLy.setOnClickListener(this);
		submitButton.setOnClickListener(this);
		IdCardPhotoView.setOnClickListener(this);
		stuCardPhotoView.setOnClickListener(this);
	}

	public boolean checkdata() {
		
		//TODO 测试
		/*if (userDetail == null) {
			Log.i(TAG, "userDetail == null");
			return false;
		}
		
		if (userDetail.getTrueName() == null) {
			Log.i(TAG, "userDetail.getTrueName() == null");
			return false;
		}*/
		
		if (nameEt.getText().length() > 0) {
			userDetail.setTrueName(nameEt.getText().toString());
		};
		
		// 上传数据是否符合规范
		if (userDetail.getTrueName() != null) {
			if (userDetail.getTrueName().length() <= 0) {
				MainActivity.showToast(getActivity(),
						R.string.publish_stu_tips_name);
				return false;
			}
		}else {
			MainActivity.showToast(getActivity(),
					R.string.publish_stu_tips_name);
			return false;
		}
		
		if (userDetail.getSex() == null) {
			MainActivity.showToast(getActivity(),
					R.string.publish_stu_tips_sex);
			return false;
		}
		if (locationTv.getText().length() == 0) {
			MainActivity.showToast(getActivity(),
					R.string.publish_stu_tips_school_address);
			return false;
		}
		if (cardIdEt.getText().length() == 0) {
			MainActivity.showToast(getActivity(),
					R.string.publish_stu_tips_card_id_require);
			return false;
		}
		if (!IndentityCard.isLegal(cardIdEt.getText().toString())) {
			MainActivity.showToast(getActivity(),
					R.string.publish_stu_tips_card_id_error);
			return false;
		}
		if (studentIdEt.getText().length() == 0) {
			MainActivity.showToast(getActivity(),
					R.string.publish_stu_tips_student_id);
			return false;
		}
		if (IdCardPhotoUri == null) {
			MainActivity.showToast(getActivity(),
					R.string.publish_stu_tips_card_photo);
			return false;
		}
		if (stuCardPhotoUri == null) {
			MainActivity.showToast(getActivity(),
					R.string.publish_stu_tips_student_photo);
			return false;
		}
		return true;
	}

	private boolean checkSoftStage() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) { // 判断是否存在SD卡
			return true;
		} else {
			Toast.makeText(getActivity(), R.string.sdCard_error,
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(KEY_URI_DATA_ID, IdCardPhotoUri);
		outState.putParcelable(KEY_URI_DATA_STU, stuCardPhotoUri);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CHOOSE_IMAGE:
				Uri uri = data.getData();
				cropImage(uri);
				Log.d(TAG, "uri = " + uri.toString());
				break;
			case REQUEST_TAKE_PHOTO:
				Log.i(TAG, "tempUri = " + tempUri);
				cropImage(tempUri);
				break;
			case REQUEST_CROP_IMAGE:
				Log.i(TAG, cropUri.toString());
				// 计算出控件的大致长度，屏幕宽度-padding
				int len = (UIUtils.getDisplayWidth(getActivity()) - UIUtils
						.dip2px(getActivity(), 30)) / 2;
				switch (chooseType) {
				case CHOOSE_TYPE_IDCARD:
					IdCardPhotoUri = cropUri;
					IdCardPhotoView.setImageBitmap(UIUtils.loadBitmap(
							getActivity(), cropUri, len, len));
					break;
				case CHOOSE_TYPE_STUCARD:
					stuCardPhotoUri = cropUri;
					stuCardPhotoView.setImageBitmap(UIUtils.loadBitmap(
							getActivity(), cropUri, len, len));
					break;
				default:
					break;
				}
				// 删除拍照残余照片
				if (tempFile != null && tempFile.exists())
					tempFile.delete();
				break;
			// 地址编辑 省份+学校
			case REQUEST_CHOOSE_LOCATION:
				int ProvinceID = data.getExtras().getInt(
						ChooseCollegeActivity.SELECTED_PROVINCE_ID);
				int CollegeID = data.getExtras().getInt(
						ChooseCollegeActivity.SELECTED_COLLEGE_ID);
				CollegeDAO collegeDAO = new CollegeDAO(getActivity());
				province = collegeDAO.queryProvinceById(ProvinceID);
				college = collegeDAO.QueryCollegeById(CollegeID);
				province.getAreaName();
				college.getCProvinceName();
				locationTv.setText(province.getAreaName() + " "
						+ college.getCUniversityName());
			}
		}
	}

	public void cropImage(Uri uri) {
		if (null == uri)
			return;

		File dir = getActivity()
				.getExternalFilesDir(Environment.DIRECTORY_DCIM);
		String fileName = System.currentTimeMillis() + ".jpg";
		File saveFile = new File(dir, fileName);
		cropUri = Uri.fromFile(saveFile);

		Intent intent = new Intent();

		intent.setAction("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");// mUri是已经选择的图片Uri
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 4);// 裁剪框比例
		intent.putExtra("aspectY", 3);
		intent.putExtra("outputX", Config.UPLOAD_IMAGE_WIDTH);// 输出图片大小
		intent.putExtra("outputY", Config.UPLOAD_IMAGE_HEIGHT);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);// 输出图片的Uri
		intent.putExtra("return-data", false);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(intent, REQUEST_CROP_IMAGE);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.authentication_stu_idCard_photo:
			takeIdCardPhoto();
			break;
		case R.id.authentication_stu_stuCard_photo:
			takeStuCardPhoto();
			break;
		case R.id.Ly_authentication_user_stu_sex:
			chooseSex();
			break;
		case R.id.publish_authentication_man_location:
			chooseLocation();
			break;
		case R.id.Bt_authentication_man_submit:
			saveStu();
			break;
		}
	}

	private void chooseSex() {
		int sex = userDetail.getSex() == null ? 0 : userDetail.getSex();
		sexDialog = new SingleChoiceDialog(getActivity(),
				getString(R.string.dialog_choose_sex), sex,
				R.array.sex_choice_array);
		sexDialog.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(int pos) {
				userDetail.setSex(pos);
				sexTv.setText(sexStr[pos]);
				SEX = pos;
			}
		});
		sexDialog.show(getChildFragmentManager(), TAG_PUBLISH_MAN);
	}

	private void takeIdCardPhoto() {
		choosePhotoDialog(CHOOSE_TYPE_IDCARD);

	}

	private void takeStuCardPhoto() {
		choosePhotoDialog(CHOOSE_TYPE_STUCARD);

	}

	private void choosePhotoDialog(int type) {
		chooseType = type;
		choosePicDialog = new ChooseListDialog(getActivity());
		choosePicDialog.addButton(getActivity().getResources().getString(
				R.string.dialog_take_photo));
		choosePicDialog.addButton(getActivity().getResources().getString(
				R.string.dialog_choose_photo));
		choosePicDialog
				.setOnclickListener(new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (checkSoftStage()) {
							Intent intent = null;
							switch (which) {
							case 0:
								File dir = getActivity().getExternalFilesDir(
										Environment.DIRECTORY_DCIM);
								Log.d(TAG,
										"dir path = " + dir.getAbsolutePath());
								intent = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);
								String fileName = System.currentTimeMillis()
										+ ".jpg";
								tempFile = new File(dir, fileName);
								tempUri = Uri.fromFile(tempFile);
								intent.putExtra(MediaStore.EXTRA_OUTPUT,
										tempUri);
								startActivityForResult(intent,
										REQUEST_TAKE_PHOTO);
								break;
							case 1:
								// 调用系统图库，选择图片
								intent = new Intent(Intent.ACTION_GET_CONTENT);
								intent.addCategory(Intent.CATEGORY_OPENABLE);
								intent.setType("image/*");
								startActivityForResult(intent,
										REQUEST_CHOOSE_IMAGE);
								break;
							}
						}
					}
				});
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		choosePicDialog.show(ft, TAG);
	}

	private void chooseLocation() {
		Intent intent = new Intent(getActivity(), ChooseCollegeActivity.class);
		startActivityForResult(intent, REQUEST_CHOOSE_LOCATION);
	}

	private void isSuccess() {
		Log.d(TAG, "userDetail = " + userDetail);
		user.setIsVerify(User.VERIFY_TYPE_COMMIT);
		application.setUser(user);
		if (dialog != null)
			dialog.dismiss();
		Intent intent = new Intent(getActivity(), InitUserService.class);
		intent.putExtra(InitUserService.KEY_START_SERVICE_FOR,
				InitUserService.TASK_UPDATE_USER);
		getActivity().startService(intent);
		MainActivity.showToast(getActivity(), R.string.publish_auth_published);
		getActivity().finish();

	}

	private void isError(String msg) {
		if (dialog != null)
			dialog.dismiss();
		if (msg != null)
			MainActivity.showToast(getActivity(), msg);
		else
			MainActivity.showToast(getActivity(),
					R.string.publish_auth_publish_error);

	}

	private void saveStu() {
		
		if (!checkdata())
			return;
		
		fillStudent();
		
		dialog = new LoadingDialog(getString(R.string.publish_auth_publishing));
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		dialog.show(ft, TAG);

		Uri.Builder builder = Uri.parse(Config.URL_PUBLISH_MAN_SAVE)
				.buildUpon();
		builder.appendQueryParameter("jsonStr",
				FormatUtils.getJSONString(studentBench));
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.i(TAG, "response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							studentBench.setVerifyId((Integer) json.getObj());
							uploadStu();
						} else {
							isError(json.getMessage());
						}
					}

				}, new VolleyErrorAdapter(getActivity()) {
					@Override
					protected void onOccurError(VolleyError error) {
						super.onOccurError(error);
						if (dialog != null)
							dialog.dismiss();
					}
				});
		request.setTag(TAG_PUBLISH_MAN);
		mQueue.add(request);
		mQueue.start();
	}

	/**
	 * 上传图片
	 * 
	 * @param id
	 *            学生证正面照id
	 */
	private void uploadStu() {
		
		Uri.Builder builder = Uri.parse(Config.URL_PUBLISH_MAN_UPLOADSTU)
				.buildUpon();
		Log.d(TAG, "URL = " + builder.toString());
		MultipartRequestParams params = new MultipartRequestParams();
		String path = UriPrase.getPath(getActivity(), stuCardPhotoUri);
		File file = new File(path);
		params.put("id", studentBench.getVerifyId().toString());
		params.put("uploadFileFileName", file.getName());
		try {
			params.put("uploadFile", new FileInputStream(file), file.getName(),
					"application/octet-stream");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		MultipartRequest picRequest = new MultipartRequest(Method.POST, params,
				builder.toString(), new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, "response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							uploadIDCard();
						} else {
							isError(null);
						}
					}
				}, new VolleyErrorAdapter(getActivity()) {
					@Override
					protected void onOccurError(VolleyError error) {
						isError(null);
					}
				});
		picRequest.setTimeOut(20000, 0);
		picRequest.setTag(TAG_PUBLISH_MAN);
		mQueue.add(picRequest);
		mQueue.start();
	}

	/**
	 * 上传图片
	 * 
	 * @param id
	 */
	private synchronized void uploadIDCard() {
		Uri.Builder builder = Uri.parse(Config.URL_PUBLISH_MAN_UPLOADIDCARD)
				.buildUpon();
		Log.d(TAG, "URL = " + builder.toString());
		MultipartRequestParams params = new MultipartRequestParams();
		String path = UriPrase.getPath(getActivity(), IdCardPhotoUri);
		File file = new File(path);
		params.put("id", studentBench.getVerifyId().toString());
		params.put("uploadFileFileName", file.getName());
		try {
			params.put("uploadFile", new FileInputStream(file), file.getName(),
					"application/octet-stream");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		MultipartRequest picRequest = new MultipartRequest(Method.POST, params,
				builder.toString(), new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, "response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							isSuccess();
						} else {
							isError(null);
						}
					}
				}, new VolleyErrorAdapter(getActivity()) {
					@Override
					protected void onOccurError(VolleyError error) {
						isError(null);
					}
				});
		picRequest.setTimeOut(20000, 0);
		picRequest.setTag(TAG_PUBLISH_MAN);
		mQueue.add(picRequest);
		mQueue.start();
	}

	@Override
	public void onDestroy() {
		if (mQueue != null) {
			mQueue.cancelAll(TAG_PUBLISH_MAN);
		}
		super.onDestroy();
	}
}

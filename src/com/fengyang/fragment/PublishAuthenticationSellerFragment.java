package com.fengyang.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.fengyang.activity.ChooseListActivity;
import com.fengyang.adapter.VolleyErrorAdapter;
import com.fengyang.db.AreaDAO;
import com.fengyang.dialog.ChooseListDialog;
import com.fengyang.dialog.LoadingDialog;
import com.fengyang.dialog.SingleChoiceDialog;
import com.fengyang.dialog.SingleChoiceDialog.OnItemSelectedListener;
import com.fengyang.entity.AppMerchantBench;
import com.fengyang.entity.OubaArea;
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
public class PublishAuthenticationSellerFragment extends Fragment implements
		OnClickListener {

	private String[] sexStr;
	// ����֤������view��ѧ��֤������view
	private ImageButton IdCardPhotoView;
	private ImageButton licensePhotoView;
	// ����֤id��Ӫҵִ��id
	private EditText cardIdEt;
	private EditText licenseEt;
//	private String phoneKey = "";
	// ʡ��
	private OubaArea province;
	private OubaArea city;
	private OubaArea distrct;
	// ��ַѡ��TV��Ley
	private TextView locationTv;
	private LinearLayout locationLy;

	// �ύ����ȡ��֤�밴ť
	private Button submitButton;

	private TextView sexTv;
	private LinearLayout sexLy;
	private SingleChoiceDialog sexDialog;
	// �������绰
	private EditText nameEt;
	private EditText addressEt;
	private EditText tNameEt;

	/**
	 * �����ݴ��uri
	 */
	private Uri tempUri;
	/**
	 * �����ݴ��file
	 */
	private File tempFile;
	/**
	 * ��ͼ�󱣴�ͼƬ��Uri
	 */
	private Uri cropUri;

	private Uri IdCardPhotoUri;
	private Uri licensePhotoUri;
	/**
	 * ѡ��ͼƬ���ͣ�������֤����Ӫҵִ��
	 */
	private int chooseType;
//	private PersonDialog personDialog;
	public static final int CHOOSE_TYPE_IDCARD = 0;
	public static final int CHOOSE_TYPE_LICESNE = 1;
	private ChooseListDialog choosePicDialog;

	public static final String TAG = "PublishAuthenticationManFragment";
	private static final String TAG_PUBLISH_SELLER = "PublishAuthenticationsell";
	public static final String KEY_URI_DATA_ID = "uriIDCard";
	public static final String KEY_URI_DATA_LICENSE = "uriLicense";
	public static final int REQUEST_CHOOSE_IMAGE = 0;
	public static final int REQUEST_TAKE_PHOTO = 1;
	public static final int REQUEST_CROP_IMAGE = 3;
	public static final int REQUEST_CHOOSE_LOCATION = 4;

	private static PublishAuthenticationSellerFragment fragment = null;

	private AppMerchantBench merchantBench;
	private StuApplication application;
	private User user;
	private UserDetail userDetail;
	private RequestQueue mQueue;
	private LoadingDialog dialog;
	private Integer SEX = null;
	
	public static Fragment getInstance() {
		if (fragment == null)
			fragment = new PublishAuthenticationSellerFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_authentication_seller, container, false);

		ListenThis(rootView);
		init();
		
		if (savedInstanceState != null) {
			IdCardPhotoUri = savedInstanceState.getParcelable(KEY_URI_DATA_ID);
			licensePhotoUri = savedInstanceState
					.getParcelable(KEY_URI_DATA_LICENSE);

			// ������ؼ��Ĵ��³��ȣ���Ļ����-padding
			int len = (UIUtils.getDisplayWidth(getActivity()) - UIUtils.dip2px(
					getActivity(), 30)) / 2;
			if (IdCardPhotoUri != null)
				IdCardPhotoView.setImageBitmap(UIUtils.loadBitmap(
						getActivity(), IdCardPhotoUri, len, len));
			if (licensePhotoUri != null)
				licensePhotoView.setImageBitmap(UIUtils.loadBitmap(
						getActivity(), licensePhotoUri, len, len));
		}

		return rootView;
	}

	public void ListenThis(View rootView) {
		// ��������ʼ���ؼ�
		licensePhotoView = (ImageButton) rootView
				.findViewById(R.id.authentication_choose_license_photo);
		IdCardPhotoView = (ImageButton) rootView
				.findViewById(R.id.authentication_choose_idCard_photo);
		locationTv = (TextView) rootView
				.findViewById(R.id.Tv_publish_authentication_sell_location_show);
		locationLy = (LinearLayout) rootView
				.findViewById(R.id.publish_authentication_sell_location);
		submitButton = (Button) rootView
				.findViewById(R.id.Bt_authentication_sell_submit);
		sexTv = (TextView) rootView
				.findViewById(R.id.Tv_authentication_user_seller_sex);
		sexLy = (LinearLayout) rootView
				.findViewById(R.id.Ly_authentication_user_seller_sex);
		nameEt = (EditText) rootView
				.findViewById(R.id.Et_publish_part_input_sell_name);
		cardIdEt = (EditText) rootView
				.findViewById(R.id.Et_item_authentication_sell_idcard);
		licenseEt = (EditText) rootView
				.findViewById(R.id.Et_item_authentication_sell_card);
		addressEt = (EditText) rootView
				.findViewById(R.id.Et_publish_sell_input_place);
		tNameEt = (EditText) rootView
				.findViewById(R.id.Et_publish_part_input_sell_tname);

		sexLy.setOnClickListener(this);
		locationLy.setOnClickListener(this);
		submitButton.setOnClickListener(this);
		IdCardPhotoView.setOnClickListener(this);
		licensePhotoView.setOnClickListener(this);
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
	 * ����̼�����
	 */
	private void fillMerch() {
		
		Timestamp ts = new Timestamp(
				new Date(System.currentTimeMillis()).getTime());

		merchantBench = new AppMerchantBench();
		
		merchantBench.setMerchantName(tNameEt.getText().toString());
		merchantBench.setIdcard(cardIdEt.getText().toString());
		merchantBench.setLicenseId(licenseEt.getText().toString());
		merchantBench.setApplyDate(ts);
		merchantBench.setOubaAreaByCityId(city);
		merchantBench.setOubaAreaByDisId(distrct);
		merchantBench.setOubaAreaByProId(province);
		
		merchantBench.setTrueName(userDetail.getTrueName());
		
		user.setUserDetail(userDetail);
		merchantBench.setOubaMember(user);
	}
	
	public boolean checkdata() {
		// �ϴ������Ƿ���Ϲ淶
		if (nameEt.getText().length() > 0) {
			userDetail.setTrueName(nameEt.getText().toString());
		};
		
		// �ϴ������Ƿ���Ϲ淶
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
					R.string.publish_seller_tips_sex);
			return false;
		}
		if (tNameEt.getText().length() == 0) {
			MainActivity.showToast(getActivity(),
					R.string.publish_seller_tips_tname);
			return false;
		}
		if (cardIdEt.getText().length() == 0
				|| !IndentityCard.isLegal(cardIdEt.getText().toString())) {
			MainActivity.showToast(getActivity(),
					R.string.publish_seller_tips_card_id_error);
			return false;
		}
		if (!IndentityCard.isLegal(cardIdEt.getText().toString())) {
			MainActivity.showToast(getActivity(),
					R.string.publish_seller_tips_card_id_error);
			return false;
		}
		if (licenseEt.getText().length() == 0) {
			MainActivity.showToast(getActivity(),
					R.string.publish_seller_tips_license_require);
			return false;
		}
		if (licenseEt.getText().length() != 15) {
			MainActivity.showToast(getActivity(),
					R.string.publish_seller_tips_license_error);
			return false;
		}
		if (IdCardPhotoUri == null) {
			MainActivity.showToast(getActivity(),
					R.string.publish_seller_tips_card_photo);
			return false;
		}
		if (licensePhotoUri == null) {
			MainActivity.showToast(getActivity(),
					R.string.publish_seller_tips_sell_photo);
			return false;
		}
		if (locationTv.getText().length() == 0) {
			MainActivity.showToast(getActivity(),
					R.string.publish_seller_tips_sell_address);
			return false;
		}
		if (addressEt.getText().length() == 0) {
			MainActivity.showToast(getActivity(),
					R.string.publish_seller_tips_seller_address);
			return false;
		}
		return true;
	}

	private boolean checkSoftStage() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) { // �ж��Ƿ����SD��
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
		outState.putParcelable(KEY_URI_DATA_LICENSE, licensePhotoUri);
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
				// ������ؼ��Ĵ��³��ȣ���Ļ����-padding
				int len = (UIUtils.getDisplayWidth(getActivity()) - UIUtils
						.dip2px(getActivity(), 30)) / 2;
				switch (chooseType) {
				case CHOOSE_TYPE_IDCARD:
					IdCardPhotoUri = cropUri;
					IdCardPhotoView.setImageBitmap(UIUtils.loadBitmap(
							getActivity(), cropUri, len, len));
					break;
				case CHOOSE_TYPE_LICESNE:
					licensePhotoUri = cropUri;
					licensePhotoView.setImageBitmap(UIUtils.loadBitmap(
							getActivity(), cropUri, len, len));
					break;
				}
				// ɾ�����ղ�����Ƭ
				if (tempFile != null && tempFile.exists())
					tempFile.delete();
				break;
			// ��ַ�༭ ʡ��+ѧУ
			case REQUEST_CHOOSE_LOCATION:
				ArrayList<Integer> choosedId = data
						.getIntegerArrayListExtra(ChooseListActivity.KEY_CHOOSED_LIST);
				StringBuffer sb = new StringBuffer();
				AreaDAO areaDAO = new AreaDAO(getActivity());
				for (int i = 0; i < choosedId.size(); i++) {
					if (i == 0) {
						province = areaDAO.queryById(choosedId.get(i));
					} else if (i == 1) {
						city = areaDAO.queryById(choosedId.get(i));
					} else if (i == 2) {
						distrct = areaDAO.queryById(choosedId.get(i));
					}
					sb.append(areaDAO.queryById(choosedId.get(i)).getAreaName()
							+ " ");
				}
				sb.deleteCharAt(sb.length() - 1);
				locationTv.setText(sb.toString());
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
		intent.setDataAndType(uri, "image/*");// mUri���Ѿ�ѡ���ͼƬUri
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 4);// �ü������
		intent.putExtra("aspectY", 3);
		intent.putExtra("outputX", Config.UPLOAD_IMAGE_WIDTH);// ���ͼƬ��С
		intent.putExtra("outputY", Config.UPLOAD_IMAGE_HEIGHT);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);// ���ͼƬ��Uri
		intent.putExtra("return-data", false);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(intent, REQUEST_CROP_IMAGE);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.authentication_choose_idCard_photo:
			takeIdCardPhoto();
			break;
		case R.id.Ly_authentication_user_seller_sex:
			chooseSex();
			break;
		case R.id.authentication_choose_license_photo:
			takeLicensePhoto();
			break;
		case R.id.publish_authentication_sell_location:
			chooseLocation();
			break;
		case R.id.Bt_authentication_sell_submit:
			saveSeller();
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
		sexDialog.show(getChildFragmentManager(), TAG);
	}

	private void takeIdCardPhoto() {
		choosePhotoDialog(CHOOSE_TYPE_IDCARD);
	}

	private void takeLicensePhoto() {
		choosePhotoDialog(CHOOSE_TYPE_LICESNE);
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
								// ����ϵͳͼ�⣬ѡ��ͼƬ
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
		Intent intent = new Intent(getActivity(), ChooseListActivity.class);
		intent.putExtra(ChooseListActivity.KEY_DAO_CLASS, AreaDAO.class);
		startActivityForResult(intent, REQUEST_CHOOSE_LOCATION);
	}

	private void isSuccess() {
		user.setIsVerify(User.VERIFY_TYPE_COMMIT);
		application.setUser(user);
		Intent intent = new Intent(getActivity(), InitUserService.class);
		intent.putExtra(InitUserService.KEY_START_SERVICE_FOR,
				InitUserService.TASK_UPDATE_USER);
		getActivity().startService(intent);
		if (dialog != null)
			dialog.dismiss();
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

	/**
	 * �ύ����
	 */
	private void saveSeller() {
		
		if(!checkdata())
			return;
		
		fillMerch();
		
		dialog=new LoadingDialog(getString(R.string.publish_auth_publishing));
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		dialog.show(ft, TAG);

		Uri.Builder builder = Uri.parse(Config.URL_PUBLISH_SELL_SAVE)
				.buildUpon();
		builder.appendQueryParameter("jsonStr",
				FormatUtils.getJSONString(merchantBench));
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.i(TAG, "response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							merchantBench.setVerifyId((Integer) json.getObj());
							uploadLicense();
						} else {
							isError(json.getMessage());
						}
					}

				}, new VolleyErrorAdapter(getActivity()) {
					@Override
					protected void onOccurError(VolleyError error) {
						isError(null);
					}
				});
		request.setTag(TAG_PUBLISH_SELLER);
		mQueue.add(request);
		mQueue.start();

	}

	/**
	 * �ϴ�ͼƬ
	 * 
	 * @param id
	 *            Ӫҵִ��������id
	 */
	private void uploadLicense() {
		Uri.Builder builder = Uri.parse(Config.URL_PUBLISH_SELL_UPLOADSELL)
				.buildUpon();
		Log.d(TAG, "URL = " + builder.toString());
		MultipartRequestParams params = new MultipartRequestParams();
		String path = UriPrase.getPath(getActivity(), licensePhotoUri);
		File file = new File(path);
		params.put("id", merchantBench.getVerifyId().toString());
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
		picRequest.setTag(TAG_PUBLISH_SELLER);
		mQueue.add(picRequest);
		mQueue.start();
	}

	/**
	 * �ϴ�ͼƬ
	 * 
	 * @param id
	 *            ����֤������id
	 */
	private synchronized void uploadIDCard() {
		Uri.Builder builder = Uri.parse(Config.URL_PUBLISH_SELL_UPLOADIDCARD)
				.buildUpon();
		Log.d(TAG, "URL = " + builder.toString());
		MultipartRequestParams params = new MultipartRequestParams();
		String path = UriPrase.getPath(getActivity(), IdCardPhotoUri);
		File file = new File(path);
		params.put("id", merchantBench.getVerifyId().toString());
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
		picRequest.setTag(TAG_PUBLISH_SELLER);
		mQueue.add(picRequest);
		mQueue.start();
	}

	@Override
	public void onDestroy() {
		if (mQueue != null) {
			mQueue.cancelAll(TAG_PUBLISH_SELLER);
		}
		super.onDestroy();
	}
}
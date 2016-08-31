package com.fengyang.util;

/**
 * ����������
 * 
 * @author tangdekun
 *
 */
public class Config {

	/**
	 * ������ip��ַ
	 */

	 public static final String SERVER_IP = "121.40.196.160";
//	public static final String SERVER_IP = "192.168.0.106";

	// public static final String SERVER_IP = "192.168.1.113";
//	public static final String SERVER_IP = "192.168.56.1";


	/**
	 * �������˿ں�
	 */
	public static final String SERVER_PORT = "8090";
//	 public static final String SERVER_PORT = "8080";

	public static String APPKEY = "5887b8134af8";// 463db7238681 27fe7909f8e8
	// ��д�Ӷ���SDKӦ�ú�̨ע��õ���APPSECRET
	public static String APPSECRET = "9cd46d6cf9908a297fbafeb75b7b19d3";//

	/*
	 * /** ������֤������url��ַ
	 */
	public static final String MESSAGE_CHECK_URL = "http://42.121.122.61:18002/send.do?ua=oubamall&pw=411473";

	/**
	 * Ӧ�ð汾��
	 */
	public static final Integer APP_VERSION_CODE = 1;

	/**
	 * �����������ռ�
	 */
	public static final String SERVER_NAMESPACE = "AppServer2";
	/**
	 * �����������ַ
	 */
	public static final String SERVER_URL = "http://" + SERVER_IP + ":"
			+ SERVER_PORT + "/" + SERVER_NAMESPACE + "/";
	/**
	 * �̳ǵ�ַ
	 */
	public static final String SERVER_WAP_MALL = "http://wap.oubamall.com/";
	/**
	 * ���ص�ַǰ׺
	 */
	public static final String DOWNLAND_ADDRESS = "http://121.40.196.160:8090/AppServer2/upload/";
	/**
	 * �û���Ϣ�����ռ�
	 */
	public static final String NAMESPACE_USER = "User/";
	
	//TODO
	/**
	 * app��Ϣ�����ռ�
	 */
	public static final String NAMESPACE_APP = "app/";
	/**
	 * ǩ�������ռ�
	 */
	public static final String NAMESPACE_CHECK = "check/";
	/**
	 * �û�����������ռ�
	 */
	public static final String NAMESPACE_USER_DETAIL = "UserDetail/";
	/**
	 * ��ְ��Ϣ�������ռ�
	 */
	public static final String NAMESPACE_PART = "part/";
	/**
	 * ��ְ�ղص������ռ�
	 */
	public static final String NAMESPACE_PART_COLLECTION = "partCollection/";
	/**
	 * ������Ʒ�������ռ�
	 */
	public static final String NAMESPACE_SECOND = "second/";
	/**
	 * ѧ���������ռ�
	 */
	public static final String NAMESPACE_STUDENT = "stu/";
	/**
	 * �̼ҵ������ռ�
	 */
	public static final String NAMESPACE_SELL = "merchant/";
	/**
	 * ������Ʒ�ղص������ռ�
	 */
	public static final String NAMESPACE_SECOND_COLLECTION = "secondCollection/";
	/**
	 * ��������ӿ������ռ�
	 */
	public static final String NAMESPACE_USER_ADVICE = "userAdvice/";
	/**
	 * ���־ٱ������ռ�
	 */
	public static final String NAMESPACE_SECOND_HAND_REPORT = "secondReport/";
	/**
	 * ��ְ�ٱ������ռ�
	 */
	public static final String NAMESPACE_PART_TIME_REPORT = "partReport/";
	/**
	 * ���Ͱ������ռ�
	 */
	public static final String NAMESPACE_CLOUD_PUSH = "cloudPush/";
	/**
	 * ��֤�����ռ�
	 */
	public static final String NAMESPACE_VERIFY = "verify/";
	/**
	 * ��ҳ���������ռ�
	 */
	public static final String NAMESPACE_HOME_PUSH = "home/";
	/**
	 * �������������ռ�
	 */
	public static final String NAMESPACE_APP_UPDATE = "app/";
	/**
	 * ��̬ҳ���ַ
	 */
	public static final String SERVER_STATIC_PAGE = SERVER_URL + "staticPage/";
	/**
	 * ����ͼ���·��
	 */
	public static final String THUM_PIC_PATH = "/thum";
	/**
	 * ����ͼ�ļ���ǰ׺
	 */
	public static final String THUM_PERFIX = "Thum_";

	/**
	 * URL ͨ���ֻ�����֤�û���½
	 */
	public static final String URL_VERIFY_BY_PHONE = SERVER_URL
			+ NAMESPACE_USER + "verifyByPhone.action";
	/**
	 * URL ͨ��������֤�û���¼
	 */
	public static final String URL_VERIFY_BY_EMAIL = SERVER_URL
			+ NAMESPACE_USER + "verifyByEmail.action";
	/**
	 * URL �ϴ��û�ͷ��
	 */
	public static final String URL_UPLOAD_USER_PHOTO = SERVER_URL
			+ NAMESPACE_USER + "uploadUserPhoto.action";
	/**
	 * URL ��ȡ�û���Ϣ
	 */
	public static final String URL_GET_USER_DATA = SERVER_URL + NAMESPACE_USER
			+ "getUserData.action";
	/**
	 * URL ��ȡ�û���ϸ��Ϣ
	 */
	public static final String URL_GET_USER_DETAIL = SERVER_URL
			+ NAMESPACE_USER_DETAIL + "getUserDetail.action";
	/**
	 * URL �û�ע��
	 */
	public static final String URL_GET_USER_REGIST = SERVER_URL
			+ NAMESPACE_USER + "registUser.action";
	/**
	 * URL ͨ���û��ֻ��Ż�ȡ�û���Ϣ
	 */
	public static final String URL_GET_USER_BY_PHONE = SERVER_URL
			+ NAMESPACE_USER + "getUserByPhone.action";
	/**
	 * URL �����û�������Ϣ
	 */
	public static final String URL_GET_USER_INFORMATION_UPDATE = SERVER_URL
			+ NAMESPACE_USER + "updatePart.action";
	public static final String URL_GET_USER_UPDATE = SERVER_URL
			+ NAMESPACE_USER + "update.action";
	public static final String URL_GET_USER_UPDATE_DETAIL = SERVER_URL
			+ NAMESPACE_USER_DETAIL + "update.action";
	public static final String URL_GET_USER_SAVEORUPDATE = SERVER_URL
			+ NAMESPACE_USER + "saveOrUpdate.action";
	/**
	 * URL ���¼�ְ������Ϣ
	 */
	public static final String URL_GET_PARTTIME_INFORMATION_UPDATE = SERVER_URL
			+ NAMESPACE_PART + "update.action";
	/**
	 * URL ���¶�����Ϣ
	 */
	public static final String URL_GET_SECOND_HAND_INFORMATION_UPDATE = SERVER_URL
			+ NAMESPACE_SECOND + "update.action";
	/**
	 * URL ���¶��ֲ�����Ϣ
	 */
	public static final String URL_GET_SECOND_HAND_INFORMATION_PART_UPDATE = SERVER_URL
			+ NAMESPACE_SECOND + "updatePart.action";

	/**
	 * URL ������ְ
	 */
	public static final String URL_PUBLISH_PART = SERVER_URL + NAMESPACE_PART
			+ "save.action";
	/**
	 * ��ȡ��ְ�б�
	 */
	public static final String URL_GET_PART_LIST = SERVER_URL + NAMESPACE_PART
			+ "getByPage.action";
	/**
	 * ���־ٱ��ӿ�
	 */
	public static final String URL_REPORT_SECOND_HAND = SERVER_URL
			+ NAMESPACE_SECOND_HAND_REPORT + "save.action";
	/**
	 * ��ְ�ٱ��ӿ�
	 */
	public static final String URL_REPORT_PART_TIME = SERVER_URL
			+ NAMESPACE_PART_TIME_REPORT + "save.action";
	/**
	 * URL ��ȡ�û������ļ�ְ����
	 */
	public static final String URL_GET_PART_COUNT_BY_USER = SERVER_URL
			+ NAMESPACE_PART + "getPublishedCount.action";
	/**
	 * URL ��ȡ�û������ļ�ְ�б�
	 */
	public static final String URL_GET_PART_LIST_BY_USER = SERVER_URL
			+ NAMESPACE_PART + "getPublishPart.action";
	/**
	 * URL ɾ����ְ�ӿ�
	 */
	public static final String URL_DELETE_PARTTIME = SERVER_URL
			+ NAMESPACE_PART + "updatePart.action";
	/**
	 * ��ȡ��ְ����
	 */
	public static final String URL_GET_PART_DETAIL = SERVER_URL
			+ NAMESPACE_PART + "getPartTime.action";
	/**
	 * ��ȡ�û���ְ�ղص�����
	 */
	public static final String URL_GET_PART_COLLECT_COUNT = SERVER_URL
			+ NAMESPACE_PART_COLLECTION + "getPartCount.action";
	/**
	 * ��ȡ�û���ְ�ղص��б�
	 */
	public static final String URL_GET_PART_COLLECT_LIST = SERVER_URL
			+ NAMESPACE_PART_COLLECTION + "getByPage.action";
	/**
	 * URL ��ְ�ղؽӿ�
	 */
	public static final String URL_PART_COLLECT = SERVER_URL
			+ NAMESPACE_PART_COLLECTION + "save.action";
	/**
	 * URL ȡ����ְ�ղؽӿ�
	 */
	public static final String URL_PART_UNCOLLECT = SERVER_URL
			+ NAMESPACE_PART_COLLECTION + "deleteCollect.action";

	/**
	 * URL ����������Ʒ
	 */
	public static final String URL_PUBLISH_SECOND = SERVER_URL
			+ NAMESPACE_SECOND + "save.action";
	/***
	 * URL ������֤ URL �̼���֤
	 */
	public static final String URL_PUBLISH_SELL_GETSELL = SERVER_URL
			+ NAMESPACE_SELL + "getMerchant.action";
	public static final String URL_PUBLISH_SELL_SAVE = SERVER_URL
			+ NAMESPACE_SELL + "save.action";
	public static final String URL_PUBLISH_SELL_UPDATE = SERVER_URL
			+ NAMESPACE_SELL + "update.action";
	public static final String URL_PUBLISH_SELL_SAVEORUPDATE = SERVER_URL
			+ NAMESPACE_SELL + "saveOrUpdate.action";
	public static final String URL_PUBLISH_SELL_UPLOADSELL = SERVER_URL
			+ NAMESPACE_SELL + "uploadLicense.action";
	public static final String URL_PUBLISH_SELL_UPLOADIDCARD = SERVER_URL
			+ NAMESPACE_SELL + "uploadIDCard.action";
	/***
	 * URL ������֤
	 */
	public static final String URL_PUBLISH_MAN_GETSTU = SERVER_URL
			+ NAMESPACE_STUDENT + "getStu.action";
	public static final String URL_PUBLISH_MAN_SAVE = SERVER_URL
			+ NAMESPACE_STUDENT + "save.action";
	public static final String URL_PUBLISH_MAN_UPDATE = SERVER_URL
			+ NAMESPACE_STUDENT + "update.action";
	public static final String URL_PUBLISH_MAN_SAVEORUPDATE = SERVER_URL
			+ NAMESPACE_STUDENT + "saveOrUpdate.action";
	public static final String URL_PUBLISH_MAN_UPLOADSTU = SERVER_URL
			+ NAMESPACE_STUDENT + "uploadStu.action";
	public static final String URL_PUBLISH_MAN_UPLOADIDCARD = SERVER_URL
			+ NAMESPACE_STUDENT + "uploadIDCard.action";
	public static final String URL_USER_UPLOADUSERPHOTO = SERVER_URL
			+ NAMESPACE_USER + "uploadUserPhoto.action";
	
	/** TODO
	 * APP ��ȡ���µ�App�汾��Ϣ
	 */
	public static final String URL_APP_GETLASTVERSION = SERVER_URL
			+ NAMESPACE_APP + "getLastVersion.action";
	/** TODO
	 * APP ��ȡ���µ�App�汾���ص�ַ
	 */
	public static final String URL_APP_DOWNLOADLASTAPK = SERVER_URL
			+ NAMESPACE_APP + "downloadLastApk.action";
	/**
	 * URL �ϴ�������Ʒ��ͼƬ
	 */
	public static final String URL_SECOND_UPLOADPIC = SERVER_URL
			+ NAMESPACE_SECOND + "uploadPic.action";
	/**
	 * URL ��ȡ������Ʒ�б�
	 */
	public static final String URL_GET_SECOND_LIST = SERVER_URL
			+ NAMESPACE_SECOND + "getByPage.action";
	/**
	 * URL ��ȡ�û������Ķ�����Ʒ����
	 */
	public static final String URL_GET_SECOND_COUNT_BY_USER = SERVER_URL
			+ NAMESPACE_SECOND + "getPublishedCount.action";
	/**
	 * URL ��ȡ�û������Ķ�����Ʒ�б�
	 */
	public static final String URL_GET_SECOND_LIST_BY_USER = SERVER_URL
			+ NAMESPACE_SECOND + "getPublishedSecond.action";
	/**
	 * URL ��ȡ������Ʒ��ϸ��Ϣ
	 */
	public static final String URL_GET_SECOND_DETAIL = SERVER_URL
			+ NAMESPACE_SECOND + "getSecondHand.action";
	/**
	 * URL ��ȡ������Ʒ��ͼƬ��ַ
	 */
	public static final String URL_GET_SECOND_PIC = SERVER_URL
			+ NAMESPACE_SECOND + "getSecondPic.action";
	/**
	 * URL ��ȡ�û��Ķ����ղ��б�
	 */
	public static final String URL_GET_SECOND_COLLECT_LIST = SERVER_URL
			+ NAMESPACE_SECOND_COLLECTION + "getByPage.action";
	/**
	 * URL ��ȡ�û������ղ�����
	 */
	public static final String URL_GET_SECOND_COLLECT_COUNT = SERVER_URL
			+ NAMESPACE_SECOND_COLLECTION + "getSecondCount.action";
	/**
	 * URL �ղض�����Ʒ
	 */
	public static final String URL_SECOND_COLLECT = SERVER_URL
			+ NAMESPACE_SECOND_COLLECTION + "save.action";
	/**
	 * URL ȡ���ղض�����Ʒ
	 */
	public static final String URL_SECOND_UNCOLLECT = SERVER_URL
			+ NAMESPACE_SECOND_COLLECTION + "deleteCollect.action";
	/**
	 * URL �ύ�������
	 */
	public static final String URL_ADVICE_SEND = SERVER_URL
			+ NAMESPACE_USER_ADVICE + "save.action";
	/**
	 * URL �����û����Ͱ󶨵�userId
	 */
	public static final String URL_UPDATE_PUSH_CHANNEL_ID = SERVER_URL
			+ NAMESPACE_CLOUD_PUSH + "saveOrUpdate.action";
	/**
	 * URL ����������֤
	 */
	public static final String URL_REQUEST_VERIFY_BY_EMAIL = SERVER_URL
			+ NAMESPACE_VERIFY + "requestVerifyEmail.action";
	/**
	 * URL ��֤����
	 */
	public static final String URL_VERIFY_EMAIL = SERVER_URL + NAMESPACE_VERIFY
			+ "verifyEmail.action";
	/**
	 * URL ��ȡ��ҳ��������
	 */
	public static final String URL_GET_HOME_PUSH = SERVER_URL
			+ NAMESPACE_HOME_PUSH + "getHomePush.action";
	/**
	 * �ϴ��ļ�·��
	 */
	public static final String UPLOAD_PATH = SERVER_URL + "upload";
	/**
	 * �û�ͷ�񲿷�·��
	 */
	public static final String USER_PHOTO_PATH = UPLOAD_PATH + "/user/avatar";
	/**
	 * ������ƷͼƬ·��
	 */
	public static final String SECOND_PIC_PATH = UPLOAD_PATH + "/secondHand";
	/**
	 * ������Ʒ����ͼƬ·��
	 */
	public static final String SECOND_PIC_PATH_THUM = SECOND_PIC_PATH
			+ THUM_PIC_PATH;
	/**
	 * ������Ʒ����·��
	 */
	public static final String SECOND_COMMENT_PATH = SECOND_PIC_PATH
			+ "/comment";
	/**
	 * ��ְͼƬ·��
	 */
	public static final String PART_PIC_PATH = UPLOAD_PATH + "/partTime";
	/**
	 * ��ְ����ͼƬ·��
	 */
	public static final String PART_COMMENT_PATH = PART_PIC_PATH + "/comment";
	/**
	 * ��֤����֤ͼƬ·��
	 */
	public static final String IDCARD_PIC_PATH = UPLOAD_PATH + "/IDCard";
	/**
	 * ѧ��֤��֤ͼƬ
	 */
	public static final String STU_PIC_PATH = UPLOAD_PATH + "/Stu";
	/**
	 * �̼�Ӫҵִ����֤ͼƬ
	 */
	public static final String MERCHANT_LICENSE_PATH = UPLOAD_PATH
			+ "/merchant";
	/**
	 * ��ҳ����·��
	 */
	public static final String HOME_PUSH_PATH = UPLOAD_PATH + "/push";
	/**
	 * ��ͨͼƬ·��
	 */
	public static final String NORMAL_IMAGE_PATH = UPLOAD_PATH + "/Image";
	/**
	 * ��ͨ�ļ�·��
	 */
	public static final String NORMAL_FILE_PATH = UPLOAD_PATH + "/File";
	/**
	 * app���ؽӿ�
	 */
	public static final String APK_FILE_PATH = SERVER_URL
			+ NAMESPACE_APP_UPDATE + "downloadLastApk.action";
	 
	/**
	 * app��װ���ļ�json���ݽӿ�
	 */
	public static final String APK_FILE_VERSION_PATH =SERVER_URL
			+ NAMESPACE_APP_UPDATE + "getLastVersion.action";

	/**
	 * ������Ϣ�ļ���
	 */
	public static final String SHAREPREFERENCE_SETTING = "setting_info";

	public static final String SETTING_HOME_INDEX = "home_index_location";
	/**
	 * �û���Ϣ�ļ���
	 */
	public static final String SHAREPREFERENCE_USER_INFO = "user_info";

	public static final String USER_INFO_NAME = "userName";

	public static final String USER_INFO_PASSWORD = "userPwd";
	/**
	 * �ϴθ����û������ʱ��
	 */
	public static final String USER_INFO_USER_DETAIL_DATE = "detail_update_time";
	/**
	 * �󶨵��û�id
	 */
	public static final String USER_BIND_USER_ID = "bind_user_Id";
	/**
	 * �Ѿ���������ϴ����û�����id
	 */
	public static final String USER_BIND_CHANNEL_ID = "channel_Id";
	/**
	 * ����id�İ�����
	 */
	public static final String USER_BIND_CHANNEL_ID_DATE = "bind_date";
	/**
	 * �û��ι��ֿ۵Ļ���
	 */
	public static final int GUAGUAKA_POINT = 3;

	/**
	 * Ĭ�ϵķ�ҳ��С
	 */
	public static final int LIST_PAGE_SIZE = 10;
	/**
	 * �ϴ�ͼƬ�Ŀ���
	 */
	public static final int UPLOAD_IMAGE_WIDTH = 1200;

	/**
	 * �ϴ�ͼƬ�ĸ߶�
	 */
	public static final int UPLOAD_IMAGE_HEIGHT = 900;
	/**
	 * �ϴ��û�ͷ��Ŀ���
	 */
	public static final int UPLOAD_USER_PHOTO_LEN = 180;
	/**
	 * �������ʱ��
	 */
	public static final int CACHE_TIME_OUT = 1000 * 3600 * 24 * 10;

	public static final String WORK_TIME_SPLIT = " ";

	/**
	 * URL ǩ���ӿ�
	 */
	public static final String URL_CHECK_IN = SERVER_URL + NAMESPACE_CHECK
			+ "checkIn.action";
	/**
	 * ������֤�����ʱ��
	 */
	public static final int VERIFY_EMAIL_CODE_EXPIRATION_TIME = 5 * 60 * 1000;

}
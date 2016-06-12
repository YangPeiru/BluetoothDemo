package com.example.yang.myapplication.bean;

import java.nio.charset.Charset;

/**
 * Created by llh on 2015/12/30.
 */
public class PConst {

    public static boolean DEBUG = true;

    public static Charset UTF_8= Charset.forName("utf-8");
    public static final int SELECT_CAMERA2 = 101;//广场中选择调用相机后...
    public static final int SELECT_CAMERA3= 102;//广场中选择调用相机后...
    public static final int SELECT_PHOTO = 200;//广场发布界面选择相片后传递给ManiActivity的标识
    public static final int SELECT_CAMERA = 100;//广场中选择调用相机后传递给ManiActivity的标识
    public static final String PHOTO = "photo";//广场发中选择调用相册
    public static final String CAMERA = "camera";//广场中选择调用相机
    public static final String TRANSMIT_IMAGE = "transmitImage";//MainActivity传递给Controller显示图片的动作标识
    public static final String PHOTO_URI = "fileUri";//MainActivity回调给广场发布界面的图片uri数据
    public static final String  INTO_PARTICULARS= "particulars";//过滤广播,用于通知Frame加载广场详情界面
    public static final String CLICK_BACK = "clickBack";//广场发布界面点击返回按钮的广播
    public static final String CLICK_POLICE="clickPolice";//广场Tab界面点击举报的广播

    public PConst() {
    }

    /**
     * SharedPreference默认读取参数
     */
    public static final String SP_CONFIG = "pac_config";

    /**
     * 手机号正则(加入短信验证码功能以后需要完善)
     */
    public static final String REG_EXP_PHONE = "^[1][3458][0-9]{9}$";

    /**
     * 密码正则
     */
    public static final String REG_EXP_PASSWORD = "[a-zA-Z0-9_]{6,16}";

    /**
     * 邮箱正则
     */
    public static final String REG_EXP_MAIL = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 昵称正则(中英文字符,最大支持15位)
     */
    public static final String REG_EXP_NICKNAME = "[\\u4e00-\\u9fa5a-zA-Z]{1,15}";

    /**
     * WIFI连接是否可用
     */
    public static boolean WIFI_CONNECTED;

    /**
     * 网络连接是否可用
     */
    public static boolean NETWORK_CONNECTED;

    /**
     * 首选项中默认IP
     */
    public static final String SP_IP_DEFAULT = "IP_DEFAULT";

    /**
     * 首选项中默认端口号
     */
    public static final String SP_PORT_DEFAULT = "PORT_DEFAULT";

    /**
     * ProgressBar默认显示时间
     */
    public static final long PROGRESS_BAR_TIMEOUT = 1500L;

    /**
     * iermu热点默认密码
     */
    public static final String DEFAULT_IERMU_PASSWORD = "cmsiermu2013";

    /**
     * 默认配置信息
     */
    public static final String DEFAULT_CONFIGURATION = "AwEAAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==";

    /**
     * 默认保存账户Key
     */
    public static final String LOCAL_ACCOUNT = "LOCAL_ACCOUNT";

    /**
     * 默认保存密码Key
     */
    public static final String LOCAL_PWD = "LOCAL_PWD";

    /**
     * 默认登录云平台的APP_ID
     */
    public static final String APP_ID = "Yynv";

    /**
     * 默认登录云平台的
     */
    public static final String CLOUD_DEFAULT_PWD = "654321";

    /**
     * 默认登录云平台所需配置串
     */
    public static final String CLOUD_SERVICE_CONFIG = "";

    /**
     * 报警消息查询普通用户userId
     */
    public static final int COMMON_USER_ID = 0;

    /**
     * 默认保存本地设备数量
     */
    public static final String DEVICE_AMOUNT = "DEVICE_AMOUNT";

    public static final String INTENT_FILTER_FINISH_ADD = "finishAdd";//过滤广播

    public static final String INTENT_FILTER_ADD_SUCCESS = "addSuccess";//过滤广播

    public static final String INTENT_FILTER_FINISH_UNBIND = "finishUnbind";//过滤广播

}

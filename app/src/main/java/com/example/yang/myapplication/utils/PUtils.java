package com.example.yang.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ypr on 2015/12/30.
 *
 */
public class PUtils {

    public PUtils() {
    }

    /**
     * Button点击时,关闭软件盘
     */
    public static void hintKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 二进制转换16进制的字符串形式
     *
     * @param b
     * @return
     */
    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
        }
        // return sb.toString().toUpperCase().trim();
        return sb.toString().trim();
    }

    /**
     * 土司显示
     *
     * @param context
     * @param content
     */
    public static void showToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static boolean isGingerbreadOrLater() {
        return Build.VERSION.SDK_INT >= 9;
    }

    public static String inputStreamToString(InputStream is) {
        String response = "";

        try {
            byte[] e = new byte[1024];

            int n1;
            for (boolean n = false; (n1 = is.read(e)) != -1; response = response + new String(e, 0, n1)) {

            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return response;
    }

    /**
     * 系统时间格式化
     *
     * @param timemillis SystemClock
     * @return
     */
    public static String getFormatDate(long timemillis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timemillis);
        Date d = c.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(d);
    }

    /**
     * 标准时间转换成 MM-dd格式
     *
     * @param date 标准时间格式
     * @return 切割后的时间
     */
    public static String formatDate2SimpleString(String date) {
        return date.substring(5, 10);
    }

    /**
     * 标准时间转换成yyyy-MM-dd格式
     *
     * @param date 标准时间格式
     * @return 切割后的时间
     */
    public static String formatDate2YMD(String date) {
        return date.substring(0, 10);
    }

    /**
     * 标准时间转化为HH:mm:ss格式
     *
     * @param date 标准时间格式
     * @return 切割后的时间
     */
    public static String formatDate2HMS(String date) {
        return date.substring(11);
    }

    /**
     * 转换为压缩图片地址
     *
     * @param url 原地址
     * @return 压缩之后的地址
     */
    public static String getCompressImageUrl(String url) {
        String compressUrl = null;
        int lastIndex = url.lastIndexOf(".");
        if (url != null && lastIndex != -1) {
            compressUrl = new StringBuilder().append(url.substring(0, lastIndex))
                    .append("_thumbnail").append(url.substring(lastIndex)).toString();
        }
        return compressUrl;
    }

    public static String getImageFileCachePathFromUrl(String url, Context context) {
        return null;
    }

    public static String getPreviewPath(String fileName, Context context) {
        return null;
    }

    public static JSONObject getPreviewParams(String fileName) throws JSONException {
        String[] Params = fileName.split("-");
        JSONObject JParams = new JSONObject();
        if (Params.length >= 3) {
            JParams.put("sn", Params[0]);
            JParams.put("time", Params[1]);
            JParams.put("type", Params[2]);
        }

        return JParams;
    }

    public static JSONObject getThumbParams(String fileName) throws JSONException {
        JSONObject JParams = new JSONObject();
        JParams.put("sn", fileName);
        return JParams;
    }

    public static int px2dp(Context context, int px) {
        // dp = px * 160 / dpi
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int dpi = metrics.densityDpi;
        return (int) (px * 160f / dpi + 0.5f);
    }

    public static int dp2px(Context context, int dp) {
        // px = dp * (dpi / 160)
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int dpi = metrics.densityDpi;
        return (int) (dp * (dpi / 160f) + 0.5f);
    }

    public static String getBody(String[] pairKeys, String... params) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < pairKeys.length; ++i) {
            sb.append(pairKeys[i] + "=" + params[i] + "&");
        }

        return sb.toString();
    }

    /**
     * 毫秒转00:00:00
     *
     * @param time
     * @return
     */
    public static String formatLongToTimeStr(Long time) {
        int hour = 0;
        int minute = 0;
        int second = 0;

        second = time.intValue() / 1000;

        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        return (getTwoLength(hour) + ":" + getTwoLength(minute) + ":" + getTwoLength(second));
    }

    private static String getTwoLength(final int data) {
        if (data < 10) {
            return "0" + data;
        } else {
            return "" + data;
        }
    }

    public static void togglePassword(EditText inputView, ImageView indicator) {
    }

    /**
     * 判断当前环境是否联网
     *
     * @param context
     * @return true表示有网
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * 判断是否为移动网络
     *
     * @param context
     * @return true表示移动网络
     */
    public static boolean isMoblieNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() ? activeNetworkInfo.getType() == 0 : false;
    }

    /**
     * 判断是否为2G网络
     *
     * @param context
     * @return true表示2G网络
     */
    public static boolean isWapNetwork(Context context) {
        if (isMoblieNetwork(context)) {
            String proxyHost = Proxy.getDefaultHost();
            if (!TextUtils.isEmpty(proxyHost)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断是否为Wifi环境
     *
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        try {
            ConnectivityManager e = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netinfo = e.getActiveNetworkInfo();
            if (netinfo != null && netinfo.isAvailable() && netinfo.isConnected()) {
                String type = netinfo.getTypeName().toLowerCase(Locale.US);
                return "wifi".equals(type);
            } else {
                return false;
            }
        } catch (Exception var4) {
            return false;
        }
    }

    /**
     * 获取设备IMEI序列号
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager telManage = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telManage.getDeviceId();
    }

    /**
     * 判断设备是否获取root权限
     *
     * @return
     */
    public static boolean IsRoot() {
        Process process = null;
        DataOutputStream os = null;

        boolean var3;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
            return true;
        } catch (Exception var13) {
            var3 = false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }

                process.destroy();
            } catch (Exception var12) {

            }

        }

        return var3;
    }


    /**
     * 判断Wifi加密方式
     *
     * @param auth
     * @return
     */
    public static int getAuth(String auth) {
        return auth.contains("Enterprise") ? 4 : (auth.contains("EAP") ? 4 : (auth.contains("WPA2") ? 3 : (auth.contains("WPA") ? 2 : (auth.contains("WEP") ? 1 : (auth.equals("") ? 0 : 5)))));
    }

    public static long ipToLong(String strIp) {
        try {
            String[] e = strIp.split("\\.");
            long[] ip = new long[e.length];

            for (int i = 0; i < e.length; ++i) {
                ip[i] = Long.parseLong(e[i]);
            }

            return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
        } catch (Exception var4) {
            var4.printStackTrace();
            return -1L;
        }
    }

    public static long ipToLongR(String strIp) {
        try {
            String[] e = strIp.split("\\.");
            long[] ip = new long[e.length];

            for (int i = 0; i < e.length; ++i) {
                ip[i] = Long.parseLong(e[i]);
            }

            return (ip[3] << 24) + (ip[2] << 16) + (ip[1] << 8) + ip[0];
        } catch (Exception var4) {
            var4.printStackTrace();
            return -1L;
        }
    }

    public static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        sb.append(String.valueOf(longIp >>> 24));
        sb.append(".");
        sb.append(String.valueOf((longIp & 16777215L) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((longIp & 65535L) >>> 8));
        sb.append(".");
        sb.append(String.valueOf(longIp & 255L));
        return sb.toString();
    }

    public static boolean isChineseChar(String str) {
        if (str.equals("")) {
            return false;
        } else {
            char[] array = str.toCharArray();

            for (int i = 0; i < array.length; ++i) {
                if ((char) ((byte) array[i]) != array[i]) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean isNumeric(String str) {
        int i = str.length();

        do {
            --i;
            if (i < 0) {
                return true;
            }
        } while (Character.isDigit(str.charAt(i)));

        return false;
    }

    /**
     * 判断是否为手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        String str = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是否为邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 打开浏览器
     *
     * @param context
     * @param urlStr
     */
    public static void startBrowser(Context context, String urlStr) {
        try {
            Intent e = new Intent();
            e.setAction("android.intent.action.VIEW");
            Uri urlData = Uri.parse(urlStr);
            e.setData(urlData);
            context.startActivity(e);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static SpannableStringBuilder makeTextWithTag(String tag, int tagColor, String text) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(tag);
        int end = builder.length();
        builder.append(" ");
        if (text != null) {
            builder.append(text);
        }

        builder.setSpan(new ForegroundColorSpan(tagColor), 0, end, 33);
        return builder;
    }

    public static void ellipsizeString(final SpannableStringBuilder text, final TextView txtView, final int lines) {
        ViewTreeObserver observer = txtView.getViewTreeObserver();
        String ellipsize = "...";
        final TextPaint paint = new TextPaint(1);
        paint.setTextSize(txtView.getTextSize());
        final float ellipsizeWidth = paint.measureText("...");
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                ViewTreeObserver obs = txtView.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (txtView.getLineCount() > lines) {
                    int lineEndIndex = txtView.getLayout().getLineEnd(lines - 1);
                    int i = 0;
                    String tmp = "";

                    for (float width = 0.0F; width < ellipsizeWidth; ++i) {
                        tmp = "" + text.charAt(lineEndIndex - i);
                        width += paint.measureText(tmp);
                    }

                    text.replace(lineEndIndex - i, lineEndIndex, "...");
                    text.delete(lineEndIndex - i + "...".length(), text.length());
                    txtView.setText(text);
                }

            }
        });
    }


}

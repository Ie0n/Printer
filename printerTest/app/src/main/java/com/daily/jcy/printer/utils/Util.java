package com.example.jcy.testleancloud;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return  version;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    public static void changLanguage(Context context, int language) {
        //设置语言类型
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        switch (language) {
            case Language.en://英文
                configuration.setLocale(Locale.ENGLISH);
                break;
            case Language.cht://繁体中文
                configuration.setLocale(Locale.TAIWAN);
                break;
            case Language.zh://简体中文
                configuration.setLocale(Locale.SIMPLIFIED_CHINESE);
                break;
            case Language.jp://日文
                configuration.setLocale(Locale.JAPAN);
                break;
            case Language.kor://韩语
                configuration.setLocale(Locale.KOREA);
                break;
            case Language.spa://西班牙
                Locale spanish = new Locale("es", "ES");
                configuration.setLocale(spanish);
                break;
            case Language.fra:
                configuration.setLocale(Locale.FRANCE);
                break;
            default:
                configuration.setLocale(Locale.getDefault());
                break;
        }
        context.getResources().updateConfiguration(configuration,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 重新启动应用
     *
     * @param context
     */
    public static void restartApplication(Context context) {
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 旋转
     */
    public static void rotate(View v, float start, float end) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation", start, end);
        anim.setDuration(100);
        anim.start();
    }

    public static void closeInput(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     *
     */
    public static float maxAsAbs(float x, float y) {
        return Math.abs(x) > Math.abs(y) ? x : y;
    }

    /**
     * @param midPoint：围绕旋转的点
     * @param rotatePoint：要旋转的点
     * @param rotate：旋转的角度
     * @return
     */
    public static PointF getRotatePointF(PointF midPoint, PointF rotatePoint, double rotate) {

        PointF rotatePoint1 = new PointF(rotatePoint.x, rotatePoint.y);

//要旋转的左上角相对中心点坐标
        float x = (rotatePoint.x - midPoint.x);
        float y = midPoint.y - rotatePoint.y;
        //计算旋转后的点 此时中点的坐标为（0,0）
        rotate = 360 - rotate;
        double cos = Math.cos(Math.PI * rotate / 180);
        double sin = Math.sin(Math.PI * rotate / 180);
        float xRotate = (float) ((x - 0) * cos - (y - 0) * sin + 0);
        float yRotate = (float) ((x - 0) * sin + (y - 0) * cos + 0);

        rotatePoint1.x = xRotate + midPoint.x;
        rotatePoint1.y = midPoint.y - yRotate;
        return rotatePoint1;
    }


    /**
     * 保留两位小数
     *
     * @param f
     * @return
     */
    public static float twoDecimal(float f) {
        float b = (float) (Math.round(f * 100)) / 100;
        return b;
    }

    public static String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MyApplication.getInstance().getString(R.string.time_format));
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    public static void ToastText(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void Post(Runnable runnable) {
        MyApplication.getInstance().getHandler().post(runnable);
    }

    /**
     * 非主线程弹Toast
     *
     * @param context
     * @param text
     */
    public static void ToastTextThread(final Context context, final String text) {
        MyApplication.getInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static float dip2px(Context context, float dpValue) {
        if (dpValue == 0) {
            return dpValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取应用详情页面intent
     *
     * @return
     */

    public static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }

    public static class Language {
        public static final int zh = 0;//中文
        public static final int en = 1;//英语
        public static final int cht = 2;//繁体中文
        public static final int vie = 3;//越南语
        public static final int jp = 4;//日语
        public static final int kor = 5;//韩语
        public static final int fra = 6;//法语
        public static final int spa = 7;//西班牙语
        public static final int th = 8;//泰语
        public static final int ara = 9;//阿拉伯语
        public static final int ru = 10;//俄语
        public static final int pt = 11;//葡萄牙语
        public static final int de = 12;//德语
        public static final int it = 13;//意大利语
        public static final int el = 14;//希腊语
        public static final int nl = 15;//荷兰语
        public static final int pl = 16;//波兰语
        public static final int bul = 17;//保加利亚语
        public static final int est = 18;//爱沙尼亚语
        public static final int dan = 19;//丹麦语
        public static final int fin = 20;//芬兰语
        public static final int cs = 21;//捷克语
        public static final int rom = 22;//罗马尼亚语
        public static final int slo = 23;//斯洛文尼亚语
        public static final int swe = 24;//瑞典语
        public static final int hu = 25;//匈牙利语
    }

}
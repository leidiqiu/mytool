package com.ldq.appinfo.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;

import com.ldq.appinfo.bean.AppInfo;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leidiqiu @ 2020-07-08 18:05:03
 */
public class AppInfoCache {

    private static AppInfoCache instance;
    private List<AppInfo> appInfoList;

    private AppInfoCache(Context context) {
        init(context);
    }

    public static synchronized AppInfoCache getInstance(Context context) {
        if (instance == null) {
            instance = new AppInfoCache(context);
        } else if (instance.appInfoList == null || instance.appInfoList.size() == 0) {
            instance.init(context);
        }
        return instance;
    }

    public List<AppInfo> getAppInfoList() {
        return appInfoList;
    }

    private void init(Context context) {
        if (appInfoList == null || appInfoList.size() == 0) {
            PackageManager packageManager = context.getPackageManager();
            List<ApplicationInfo> list = packageManager.getInstalledApplications(PackageManager.SIGNATURE_MATCH);
            appInfoList = new ArrayList<>();
            for (ApplicationInfo applicationInfo : list) {
                try {
                    Drawable icon = packageManager.getApplicationIcon(applicationInfo.packageName);
                    String label = (String) packageManager.getApplicationLabel(applicationInfo);
                    String signature = getSignature(context, applicationInfo.packageName);
                    AppInfo appInfo = new AppInfo(icon, label, applicationInfo.packageName, signature);
                    appInfoList.add(appInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getSignature(Context context, String packageName) throws Exception {
        PackageManager packageManager = context.getPackageManager();
        Signature[] signatures = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures;
        String signature = md5(signatures[0].toByteArray());
        return signature;
    }

    private String md5(byte[] b) {
        String HEX = "0123456789ABCDEF";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bb = md.digest(b);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < bb.length; i++) {
                sb.append(HEX.charAt((bb[i] >>> 4) & 0x0F));
                sb.append(HEX.charAt(bb[i] & 0x0F));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}

package com.ldq.appinfo.utils;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;

public class AppInfoUtils {

    public static String[] getTabTitle(PackageManager packageManager, String packageName) {
        String[] tabTitles = new String[4];
        try {

            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            int count = packageInfo.activities != null ? packageInfo.activities.length : 0;
            tabTitles[0] = "Activity[" + count + "]";

            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SERVICES);
            count = packageInfo.services != null ? packageInfo.services.length : 0;
            tabTitles[1] = "Service[" + count + "]";

            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_RECEIVERS);
            count = packageInfo.receivers != null ? packageInfo.receivers.length : 0;
            tabTitles[2] = "Receiver[" + count + "]";

            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_PROVIDERS);
            count = packageInfo.providers != null ? packageInfo.providers.length : 0;
            tabTitles[3] = "Provider[" + count + "]";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tabTitles;
    }

    public static String getActivityInfo(PackageManager packageManager, String packageName) {
        StringBuffer sb = new StringBuffer();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    packageName, PackageManager.GET_ACTIVITIES);
            ActivityInfo[] activityInfos = packageInfo.activities;
            for (ActivityInfo activityInfo : activityInfos) {
                sb.append(activityInfo.name);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getServiceInfo(PackageManager packageManager, String packageName) {
        StringBuffer sb = new StringBuffer();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    packageName, PackageManager.GET_SERVICES);
            ServiceInfo[] serviceInfos = packageInfo.services;
            for (ServiceInfo serviceInfo : serviceInfos) {
                sb.append(serviceInfo.name);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getReceiverInfo(PackageManager packageManager, String packageName) {
        StringBuffer sb = new StringBuffer();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    packageName, PackageManager.GET_RECEIVERS);
            ActivityInfo[] activityInfos = packageInfo.receivers;
            for (ActivityInfo activityInfo : activityInfos) {
                sb.append(activityInfo.name);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getProviderInfo(PackageManager packageManager, String packageName) {
        StringBuffer sb = new StringBuffer();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    packageName, PackageManager.GET_PROVIDERS);
            ProviderInfo[] providerInfos = packageInfo.providers;
            for (ProviderInfo providerInfo : providerInfos) {
                sb.append(providerInfo.name);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

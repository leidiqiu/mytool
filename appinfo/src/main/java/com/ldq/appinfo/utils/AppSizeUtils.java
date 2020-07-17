package com.ldq.appinfo.utils;

import android.annotation.TargetApi;
import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.os.Build;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.format.Formatter;

import com.ldq.appinfo.bean.AppInfo;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class AppSizeUtils {

    @TargetApi(Build.VERSION_CODES.O)
    public static void setApkSize(Context context, String packageName, AppInfo appInfo) {
        final StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        final StorageStatsManager storageStatsManager = (StorageStatsManager) context.getSystemService(Context.STORAGE_STATS_SERVICE);
        final List<StorageVolume> storageVolumes = storageManager.getStorageVolumes();
        final UserHandle user = android.os.Process.myUserHandle();
        for (StorageVolume storageVolume : storageVolumes) {
            final String uuidStr = storageVolume.getUuid();
            final UUID uuid = uuidStr == null ? StorageManager.UUID_DEFAULT : UUID.fromString(uuidStr);
            try {
                final StorageStats storageStats = storageStatsManager.queryStatsForPackage(uuid, packageName, user);
                if (appInfo != null) {
                    appInfo.appSize = storageStats.getAppBytes();
                    appInfo.dataSize = storageStats.getDataBytes();
                    appInfo.cacheSize = storageStats.getCacheBytes();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static String formatShortFileSize(Context context, long size) {
        return Formatter.formatShortFileSize(context, size);
    }

    public static long fileSize(File file) {
        if (file.isFile()) {
            return file.length();
        } else {
            long size = 0;
            for (File f : file.listFiles()) {
                size = size + fileSize(f);
            }
            return size;
        }
    }
}

package com.ldq.appinfo.bean;

import android.graphics.drawable.Drawable;

public class AppInfo {

    public Drawable icon;
    public CharSequence name;
    public CharSequence packageName;
    public CharSequence signature;
    public long appSize;
    public long dataSize;
    public long cacheSize;

    public AppInfo() {
    }

    public AppInfo(Drawable icon, String name, String packageName,
                   String signature) {
        this.icon = icon;
        this.name = name;
        this.packageName = packageName;
        this.signature = signature;
    }

}

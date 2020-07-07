package com.ldq.appinfo.bean;

import android.graphics.drawable.Drawable;

public class AppInfo implements Cloneable {

    private Drawable icon;
    private CharSequence name;
    private CharSequence packageName;
    private CharSequence signature;

    public AppInfo(Drawable icon, String name, String packageName,
                   String signature) {
        this.icon = icon;
        this.name = name;
        this.packageName = packageName;
        this.signature = signature;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public CharSequence getPackageName() {
        return packageName;
    }

    public void setPackageName(CharSequence packageName) {
        this.packageName = packageName;
    }

    public CharSequence getSignature() {
        return signature;
    }

    public void setSignature(CharSequence signature) {
        this.signature = signature;
    }

    @Override
    public AppInfo clone() throws CloneNotSupportedException {
        return (AppInfo) super.clone();
    }
}

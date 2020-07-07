package com.ldq.appinfo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldq.appinfo.R;
import com.ldq.appinfo.bean.AppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppListAdapter extends BaseAdapter {

    private Context mContext;
    private List<AppInfo> mList;
    private List<AppInfo> mListBackup;

    public AppListAdapter(Context context, List<AppInfo> list) {
        mContext = context;
        mList = list;
        mListBackup = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.appinfo_list_item, parent, false);
            holder = new Holder();
            holder.imageView = convertView.findViewById(R.id.appinfo_image);
            holder.textViewName = convertView.findViewById(R.id.appinfo_name);
            holder.textViewPkgName = convertView.findViewById(R.id.appinfo_pkgname);
            holder.textViewSignature = convertView.findViewById(R.id.appinfo_signature);
            convertView.setTag(holder);
        }

        bindView(convertView, position);

        return convertView;
    }

    public void sort(SortKey sortKey) {
        switch (sortKey) {
            case NAME:
                Collections.sort(mList, (o1, o2) -> o1.getName().toString().compareTo(o2.getName().toString()));
                break;
            case PKG_NAME:
                Collections.sort(mList, (o1, o2) -> o1.getPackageName().toString().compareTo(o2.getPackageName().toString()));
                break;
            case SIGNATURE:
                Collections.sort(mList, (o1, o2) -> o1.getSignature().toString().compareTo(o2.getSignature().toString()));
                break;
            default:
                break;
        }

        notifyDataSetChanged();
    }

    public void filter(String keyword) {
        mList = new ArrayList();
        if (!TextUtils.isEmpty(keyword)) {
            for (AppInfo appInfo : mListBackup) {
                appInfo.setName(spannable(appInfo.getName(), keyword));
                appInfo.setPackageName(spannable(appInfo.getPackageName(), keyword));
                appInfo.setSignature(spannable(appInfo.getSignature(), keyword));
                if (appInfo.getName().toString().contains(keyword)
                        || appInfo.getPackageName().toString().contains(keyword)
                        || appInfo.getSignature().toString().contains(keyword)) {
                    mList.add(appInfo);
                }
            }
        }
        notifyDataSetChanged();
    }

    private Spannable spannable(CharSequence charSequence, String keyword) {
        SpannableString spannableString = new SpannableString(charSequence.toString());
        int start = charSequence.toString().indexOf(keyword);
        if (start >= 0) {
            int end = start + keyword.length();
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
            spannableString.setSpan(foregroundColorSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public void reset() {
        mList = mListBackup;
        notifyDataSetChanged();
    }

    private void bindView(View view, int position) {
        Holder holder = (Holder) view.getTag();
        AppInfo appInfo = (AppInfo) getItem(position);
        holder.imageView.setImageDrawable(appInfo.getIcon());
        holder.textViewName.setText(appInfo.getName());

        holder.textViewPkgName.setText(new SpannableStringBuilder("包名:").append(appInfo.getPackageName()));
        holder.textViewSignature.setText(new SpannableStringBuilder("签名:").append(appInfo.getSignature()));
    }

    public enum SortKey {
        NAME, PKG_NAME, SIGNATURE
    }

    private class Holder {
        ImageView imageView;
        TextView textViewName;
        TextView textViewPkgName;
        TextView textViewSignature;
    }
}

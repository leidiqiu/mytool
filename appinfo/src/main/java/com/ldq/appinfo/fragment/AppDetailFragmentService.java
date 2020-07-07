package com.ldq.appinfo.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ldq.appinfo.R;
import com.ldq.appinfo.consts.ConstKey;
import com.ldq.appinfo.utils.AppInfoUtils;

public class AppDetailFragmentService extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container,
                false);
        TextView textView = view.findViewById(R.id.textview);
        Bundle bundle = getArguments();
        String packageName = bundle.getString(ConstKey.KEY_PACKAGE_NAME);
        PackageManager packageManager = getActivity().getPackageManager();
        String info = AppInfoUtils.getServiceInfo(packageManager, packageName);
        textView.setText(info);
        return view;
    }
}

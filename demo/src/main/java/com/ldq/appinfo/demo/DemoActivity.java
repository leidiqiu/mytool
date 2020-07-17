package com.ldq.appinfo.demo;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ldq.appinfo.activity.AppListActivity;
import com.ldq.appinfo.activity.SdcardActivity;
import com.ldq.appinfo.utils.AppInfoCache;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Button> buttonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);

        buttonList.add(findViewById(R.id.app_info));
        buttonList.add(findViewById(R.id.button));

        for (Button button : buttonList) {
            button.setOnClickListener(this);
        }

        int granted = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (granted != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DemoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }

        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getPackageName());
        if (mode != AppOpsManager.MODE_ALLOWED) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivityForResult(intent, 110);
        }

        final ProgressDialog progressDialog = new ProgressDialog(DemoActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在初始化，请稍候...");
        progressDialog.show();
        new Thread(() -> {
            AppInfoCache.getInstance(getApplicationContext());
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_info:
                startActivity(new Intent(DemoActivity.this, AppListActivity.class));
                break;
            case R.id.button:
                startActivity(new Intent(DemoActivity.this, SdcardActivity.class));
                break;
            default:
                break;

        }
    }

}
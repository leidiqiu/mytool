package com.ldq.appinfo.demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ldq.appinfo.activity.AppListActivity;
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

        for (Button button : buttonList) {
            button.setOnClickListener(this);
        }

        int granted = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (granted != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DemoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }

        AppInfoCache.getInstance(getApplicationContext());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_info:
                startActivity(new Intent(DemoActivity.this, AppListActivity.class));
                break;
            default:
                break;

        }
    }

}
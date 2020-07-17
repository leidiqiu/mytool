package com.ldq.appinfo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.ldq.appinfo.R;
import com.ldq.appinfo.utils.AppSizeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class SdcardActivity extends AppCompatActivity {

    private ArrayList<DirSizeBean> arrayList = new ArrayList();
    private TextView textViewTitle;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdcard_size);
        textViewTitle = findViewById(R.id.text_title);
        textView = findViewById(R.id.text_sdcard_size);

        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        String total = AppSizeUtils.formatShortFileSize(getApplicationContext(), externalStorageDirectory.getTotalSpace());
        String usable = AppSizeUtils.formatShortFileSize(getApplicationContext(), externalStorageDirectory.getUsableSpace());
        textViewTitle.setText("存储空间【共：" + total + "，可用：" + usable + "】");

        final ProgressDialog progressDialog = new ProgressDialog(SdcardActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在统计，请稍候...");
        progressDialog.show();
        new Thread(() -> {

            for (File f : externalStorageDirectory.listFiles()) {
                DirSizeBean dirSizeBean = new DirSizeBean(f.getName(), AppSizeUtils.fileSize(f));
                arrayList.add(dirSizeBean);
            }

            runOnUiThread(() -> refreshUi());

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        }).start();

    }

    private void refreshUi() {
        StringBuffer stringBuffer = new StringBuffer();
        for (DirSizeBean dirSizeBean : arrayList) {
            stringBuffer.append(dirSizeBean.name);
            stringBuffer.append(":");
            stringBuffer.append(AppSizeUtils.formatShortFileSize(getApplicationContext(), dirSizeBean.size));
            stringBuffer.append("\n");
        }
        textView.setText(stringBuffer.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_sdcard_size, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.sort_name) {
            Collections.sort(arrayList, (o1, o2) -> o1.name.compareTo(o2.name));
        } else if (item.getItemId() == R.id.sort_name_desc) {
            Collections.sort(arrayList, (o1, o2) -> o2.name.compareTo(o1.name));
        } else if (item.getItemId() == R.id.sort_size) {
            Collections.sort(arrayList, (o1, o2) -> compare(o1.size, o2.size));
        } else if (item.getItemId() == R.id.sort_size_desc) {
            Collections.sort(arrayList, (o1, o2) -> compare(o2.size, o1.size));
        }

        refreshUi();

        return super.onOptionsItemSelected(item);

    }

    private int compare(long a, long b) {
        if (a == b) {
            return 0;
        } else if (a > b) {
            return 1;
        } else {
            return -1;
        }
    }

    private static class DirSizeBean {
        String name;
        long size;

        public DirSizeBean(String name, long size) {
            this.name = name;
            this.size = size;
        }
    }
}

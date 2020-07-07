package com.ldq.appinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ldq.appinfo.R;
import com.ldq.appinfo.adapter.AppListAdapter;
import com.ldq.appinfo.bean.AppInfo;
import com.ldq.appinfo.consts.ConstKey;
import com.ldq.appinfo.utils.AppInfoCache;

import java.lang.reflect.Field;
import java.util.List;

public class AppListActivity extends AppCompatActivity implements OnItemClickListener {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mListView = findViewById(R.id.list_view);
        List<AppInfo> list = AppInfoCache.getInstance(getApplicationContext()).getAppInfoList();
        AppListAdapter appListAdapter = new AppListAdapter(getApplicationContext(), list);
        mListView.setAdapter(appListAdapter);

        mListView.setOnItemClickListener(this);

        actionBar.setTitle(actionBar.getTitle() + "[" + list.size() + "]");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = new Intent(AppListActivity.this, AppDetailActivity.class);
        AppListAdapter adapter = (AppListAdapter) parent.getAdapter();
        AppInfo appInfo = (AppInfo) adapter.getItem(position);
        String packageName = appInfo.getPackageName().toString();
        intent.putExtra(ConstKey.KEY_PACKAGE_NAME, packageName);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);

        SearchView searchView = ((SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search)));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((AppListAdapter) mListView.getAdapter()).filter(newText);
                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            ((AppListAdapter) mListView.getAdapter()).reset();
            return false;
        });

        SearchView.SearchAutoComplete searchTextView = searchView.findViewById(R.id.search_src_text);
        try {
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            field.set(searchTextView, R.drawable.bg_actionbar_search_cursor);
        } catch (Exception e) {
            // ignore
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.appinfo_name) {
            ((AppListAdapter) mListView.getAdapter()).sort(AppListAdapter.SortKey.NAME);
            return true;
        } else if (item.getItemId() == R.id.sort_pkgname) {
            ((AppListAdapter) mListView.getAdapter()).sort(AppListAdapter.SortKey.PKG_NAME);
            return true;
        } else if (item.getItemId() == R.id.sort_signature) {
            ((AppListAdapter) mListView.getAdapter()).sort(AppListAdapter.SortKey.SIGNATURE);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

}

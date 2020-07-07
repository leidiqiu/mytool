package com.ldq.appinfo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AppInfoUtilsTest {

    @Test
    public void testPackageName() {
        Context context = InstrumentationRegistry.getContext();
        Assert.assertEquals("com.ldq.appinfo.test", context.getPackageName());
    }


}

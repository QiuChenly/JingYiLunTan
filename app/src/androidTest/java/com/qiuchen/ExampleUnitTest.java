package com.qiuchen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import com.qiuchen.API.mJingYi;
import com.qiuchen.Base.BaseApp;
import com.qiuchen.Base.mLayoutSet;
import com.qiuchen.DataModel.mTask;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qiuchen on 2017/12/31.
 */
import org.junit.Test;

import org.junit.Assert.*;

public class ExampleUnitTest implements mJingYi.TaskCallBack {
    @Test
    public void addition_isCorrect() {
        mJingYi.Companion.getTasklist(this);
        for (int a =0;a<10;a++){

        }
    }

    @Override
    public void taskCallback(@NotNull List<? extends mTask> a) {

    }
}


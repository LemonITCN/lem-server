package cn.lemonit.lemoi.adapter;

import cn.lemonit.lemoi.listener.LemoiProgressListener;

import java.io.File;

public class LemoiProgressListenerAdapter implements LemoiProgressListener {

    @Override
    public void onStart(String aimPath) {

    }

    @Override
    public void onException(String aimPath, Exception e) {

    }

    @Override
    public void onComplete(String aimPath, File file) {

    }

    @Override
    public void onProgress(String aimPath, double progress) {

    }
}

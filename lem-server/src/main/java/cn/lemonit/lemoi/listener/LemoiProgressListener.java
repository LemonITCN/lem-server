package cn.lemonit.lemoi.listener;

import java.io.File;

/**
 * 公共的进度监听器
 *
 * @author lemonit_cn
 */
public interface LemoiProgressListener {

    /**
     * 开始操作的时候的回调函数
     *
     * @param aimPath 任务目标路径
     */
    void onStart(String aimPath);

    /**
     * 发生异常的时候的回调
     *
     * @param aimPath 任务目标路径
     * @param e       异常对象
     */
    void onException(String aimPath, Exception e);

    /**
     * 操作完毕的回调
     *
     * @param aimPath 任务目标路径
     */
    void onComplete(String aimPath, File file);

    /**
     * 当进度被改变的时候触发
     *
     * @param aimPath  任务目标路径
     * @param progress 范围0.0 - 1.0
     */
    void onProgress(String aimPath, double progress);

}

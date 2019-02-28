package cn.lemonit.lemoi.interfaces;

import cn.lemonit.lemoi.exceptions.TargetNotExistsException;
import cn.lemonit.lemoi.listener.LemoiProgressListener;
import cn.lemonit.lemoi.models.LemoiPath;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Lemoi操作者规范
 *
 * @author lemonit_cn
 */
public interface LemoiOperator {

    LemoiClientConfig getClientConfig();

    /**
     * 向指定目录中放置文件
     *
     * @param file             要放置的文件对象
     * @param path             要放到的路径
     * @param progressListener 进度监听器
     */
    void put(File file, String path, LemoiProgressListener progressListener);

    /**
     * 向指定的目录指定位置输出文件
     *
     * @param inputStream      数据输出流
     * @param contentLength    输出数据的内容长度
     * @param path             要放到的路径
     * @param progressListener 进度监听器
     */
    void put(InputStream inputStream, Long contentLength, String path, LemoiProgressListener progressListener);

    /**
     * 删除指定位置的文件
     *
     * @param path 要删除的文件的路径
     */
    void delete(String path);

    /**
     * 获取指定位置的文件对象
     *
     * @param path             要获取的文件的路径
     * @param progressListener 进度监听器（要是远程文件（OSS或其他网络位置）需要首先下载）
     */
    void get(String path, LemoiProgressListener progressListener) throws TargetNotExistsException;

    /**
     * 将指定文件拷贝至另一个目录
     *
     * @param sourcePath       源文件路径
     * @param aimPath          目标文件路径
     * @param progressListener 进度监听器
     */
    void copy(String sourcePath, String aimPath, LemoiProgressListener progressListener) throws TargetNotExistsException;

    /**
     * 列出指定目录下的所有子路径
     *
     * @param parentPath 父路径
     * @return 子路径列表
     */
    List<String> list(String parentPath);

    /**
     * 判断指定路径的文件是否存在
     *
     * @param path 要检查的路径
     * @return 是否存在的布尔值
     */
    boolean contain(String path);

}

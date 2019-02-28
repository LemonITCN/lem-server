package cn.lemonit.lemoi.models;

import cn.lemonit.lemoi.interfaces.LemoiClientConfig;

import java.io.File;

/**
 * S3客户端配置信息类
 *
 * @author lemonit_cn
 */
public class LemoiLocalClientConfig implements LemoiClientConfig {

    /**
     * 根路径
     */
    private String rootPath;
    /**
     * 内存缓冲区大小
     * 用于设置文件流在内存中转所占用的内存大小
     */
    private Integer bufferSize;

    public String getRootPath() {
        if (rootPath.length() > 1 && rootPath.endsWith(File.separator)) {
            rootPath = rootPath.substring(0, rootPath.length() - 1);
        }
        return rootPath;
    }

    public LemoiLocalClientConfig setRootPath(String rootPath) {
        this.rootPath = rootPath;
        return this;
    }

    public Integer getBufferSize() {
        if (this.bufferSize == null || this.bufferSize == 0) {
            this.bufferSize = 1024 * 1024;
        }
        return bufferSize;
    }

    public LemoiLocalClientConfig setBufferSize(Integer bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }
}

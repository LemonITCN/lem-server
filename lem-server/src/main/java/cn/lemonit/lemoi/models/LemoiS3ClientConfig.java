package cn.lemonit.lemoi.models;

import cn.lemonit.lemoi.interfaces.LemoiClientConfig;

/**
 * S3客户端配置信息类
 *
 * @author lemonit_cn
 */
public class LemoiS3ClientConfig implements LemoiClientConfig {

    /**
     * 是否使用HTTPS
     * 传入true为https
     * 传入false为http
     */
    private boolean isUseHttps;
    /**
     * 是否使用PathStyle
     * 传入true为PatyStyle
     * 传入false为VirtualHostStyle
     */
    private boolean isUsePathStyle;
    /**
     * 要操作的bucket的名称
     */
    private String bucketName;
    /**
     * oss端点，不需要写协议
     * 如oss.lemonit.cn，或118.89.232.188:19000
     */
    private String endpoint;
    /**
     * oss的accessKey
     */
    private String accessKey;
    /**
     * oss的secretKey
     */
    private String secretKey;
    /**
     * 临时文件夹路径
     * 现用于下载场景下的临时存储
     */
    private String tempDirPath;

    public boolean isUseHttps() {
        return isUseHttps;
    }

    public LemoiS3ClientConfig setUseHttps(boolean useHttps) {
        isUseHttps = useHttps;
        return this;
    }

    public boolean isUsePathStyle() {
        return isUsePathStyle;
    }

    public LemoiS3ClientConfig setUsePathStyle(boolean usePathStyle) {
        isUsePathStyle = usePathStyle;
        return this;
    }

    public String getBucketName() {
        return bucketName;
    }

    public LemoiS3ClientConfig setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public LemoiS3ClientConfig setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public LemoiS3ClientConfig setAccessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public LemoiS3ClientConfig setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public String getTempDirPath() {
        if (tempDirPath == null || tempDirPath.trim().equals("")) {
            tempDirPath = System.getProperty("java.io.tmpdir");
        }
        return tempDirPath;
    }

    public LemoiS3ClientConfig setTempDirPath(String tempDirPath) {
        this.tempDirPath = tempDirPath;
        return this;
    }
}

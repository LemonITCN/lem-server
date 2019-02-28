package cn.lemonit.lemserver.domian;

import java.util.Date;

public class Version {
    private String versionKey;

    private Integer versionId;

    private Date createTime;

    private String appKey;

    private String versionDescription;

    private String versionIcon;

    public Version(String versionKey, Date createTime, String appKey, String versionDescription,String versionIcon) {
        this.versionKey = versionKey;
//        this.versionId = versionId;
        this.createTime = createTime;
        this.appKey = appKey;
        this.versionDescription = versionDescription;
        this.versionIcon = versionIcon;
    }

    public Version() {
        super();
    }

    public String getVersionKey() {
        return versionKey;
    }

    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey == null ? null : versionKey.trim();
    }

//    public Integer getVersionId() {
//        return versionId;
//    }

//    public void setVersionId(Integer versionId) {
//        this.versionId = versionId;
//    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    public String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription == null ? null : versionDescription.trim();
    }

    public String getVersionIcon() {
        return versionIcon;
    }

    public void setVersionIcon(String versionIcon) {
        this.versionIcon = versionIcon;
    }
}
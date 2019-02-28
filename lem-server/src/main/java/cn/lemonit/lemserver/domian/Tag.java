package cn.lemonit.lemserver.domian;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Tag {
    private String tagKey;

    private Integer tagId;

    private String appKey;

    private String tagName;

    private Date createTime;

    private String tagDescription;

    public Tag(String tagKey, String appKey, String tagName, Date createTime, String tagDescription) {
        this.tagKey = tagKey;
//        this.tagId = tagId;
        this.appKey = appKey;
        this.tagName = tagName;
        this.createTime = createTime;
        this.tagDescription = tagDescription;
    }

    public Tag() {
        super();
    }

    public String getTagKey() {
        return tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey == null ? null : tagKey.trim();
    }

//    public Integer getTagId() {
//        return tagId;
//    }

//    public void setTagId(Integer tagId) {
//        this.tagId = tagId;
//    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription == null ? null : tagDescription.trim();
    }
}
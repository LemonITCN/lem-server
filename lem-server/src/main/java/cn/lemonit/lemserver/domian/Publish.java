package cn.lemonit.lemserver.domian;

import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class Publish {
    private String publishKey;

    private Integer publishId;

    private String tagKey;

    private String versionKey;

    private Boolean forceUpdate;

    private Date publishTime;

    public Publish(String publishKey, String tagKey, String versionKey,Date publishTime,Boolean forceUpdate) {
        this.publishKey = publishKey;
//        this.publishId = publishId;
        this.tagKey = tagKey;
        this.versionKey = versionKey;
        this.publishTime = publishTime;
        this.forceUpdate = forceUpdate;
    }

    public Publish() {
        super();
    }

    public String getPublishKey() {
        return publishKey;
    }

    public void setPublishKey(String publishKey) {
        this.publishKey = publishKey == null ? null : publishKey.trim();
    }

//    public Integer getPublishId() {
//        return publishId;
//    }

//    public void setPublishId(Integer publishId) {
//        this.publishId = publishId;
//    }

    public String getTagKey() {
        return tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey == null ? null : tagKey.trim();
    }

    public String getVersionKey() {
        return versionKey;
    }

    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey == null ? null : versionKey.trim();
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }
}
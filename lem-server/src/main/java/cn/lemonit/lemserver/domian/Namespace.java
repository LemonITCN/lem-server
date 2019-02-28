package cn.lemonit.lemserver.domian;

import org.springframework.stereotype.Component;
import javax.validation.constraints.Pattern;

import javax.validation.constraints.NotNull;

import java.util.Date;
import java.lang.String;

@Component
public class Namespace {
//    @NotNull(message="invalid_args")
    private String spaceKey;

    private Integer spaceId;

//    @NotNull(message="invalid_args")
    private String spaceName;

    private Date createTime;

    private String spaceDescription;

    public Namespace(String spaceKey, String spaceName, Date createTime, String spaceDescription) {
        this.spaceKey = spaceKey;
        this.spaceName = spaceName;
        this.createTime = createTime;
        this.spaceDescription = spaceDescription;
    }
    public Namespace() {
        super();
    }

    public String getSpaceKey() {
        return spaceKey;
    }

    public void setSpaceKey(String spaceKey) {
        this.spaceKey = spaceKey == null ? null : spaceKey.trim();
    }

//    public Integer getSpaceId() {
//        return spaceId;
//    }
//
//    public void setSpaceId(Integer spaceId) {
//        this.spaceId = spaceId;
//    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName == null ? null : spaceName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSpaceDescription() {
        return spaceDescription;
    }

    public void setSpaceDescription(String spaceDescription) {
        this.spaceDescription = spaceDescription == null ? null : spaceDescription.trim();
    }
}
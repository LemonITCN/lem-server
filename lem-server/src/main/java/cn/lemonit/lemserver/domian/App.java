package cn.lemonit.lemserver.domian;

import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;

import java.util.Date;

@Component
public class App {
    private String appKey;

//    @NotNull(message="invalid_args")
    private String appName;

    private String bundleIdentifier;

    private Integer appId;

    private Date createTime;

    private String spaceKey;

    private String platform;

    private String appDescription;

    private String appIcon;

    public App(String appKey, String appName, Date createTime, String spaceKey, String platform, String appDescription,String appIcon,String bundleIdentifier) {
        this.appKey = appKey;
        this.appName = appName;
        this.createTime = createTime;
        this.spaceKey = spaceKey;
        this.platform = platform;
        this.appDescription = appDescription;
        this.appIcon = appIcon;
        this.bundleIdentifier = bundleIdentifier;
    }

    public App() {
        super();
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    public String getBundleIdentifier() {
        return bundleIdentifier;
    }

    public void setBundleIdentifier(String bundleIdentifier) {
        this.bundleIdentifier = bundleIdentifier == null ? null : bundleIdentifier.trim();
    }

//    public Integer getAppId() {
//        return appId;
//    }
//
//    public void setAppId(Integer appId) {
//        this.appId = appId;
//    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSpaceKey() {
        return spaceKey;
    }

    public void setSpaceKey(String spaceKey) {
        this.spaceKey = spaceKey == null ? null : spaceKey.trim();
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription == null ? null : appDescription.trim();
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }
}
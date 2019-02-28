package cn.lemonit.lemserver.service;

import cn.lemonit.lemserver.domian.App;

import java.util.List;

public interface AppService {
    int deleteByPrimaryKey(String appKey);

    int insert(App record);

    int insertSelective(App record);

    App selectByPrimaryKey(String appKey);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKeyWithBLOBs(App record);

    int updateByPrimaryKey(App record);

    int selectBySpacekey(String spaceKey);

    App selectByName(String appName);

    List listByPageinfo(Integer start, Integer end);

    App isAppExits(String spaceKey,String appName,String platform);

}
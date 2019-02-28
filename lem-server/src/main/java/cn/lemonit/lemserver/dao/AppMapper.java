package cn.lemonit.lemserver.dao;

import cn.lemonit.lemserver.domian.App;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppMapper {
    int deleteByPrimaryKey(String appKey);

    int insert(App record);

    int insertSelective(App record);

    App selectByPrimaryKey(String appKey);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKeyWithBLOBs(App record);

    int updateByPrimaryKey(App record);

    int selectBySpacename(String spaceKey);

    App selectByName(String appName);

    List listByPageinfo(Integer start, Integer end);

    App isAppExits(String spaceKey,String appName,String platform);
}
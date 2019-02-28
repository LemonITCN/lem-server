package cn.lemonit.lemserver.service;

import cn.lemonit.lemserver.domian.Version;

import java.util.List;

public interface VersionService {
    int deleteByPrimaryKey(String versionKey);

    int insert(Version record);

    int insertSelective(Version record);

    Version selectByPrimaryKey(String versionKey);

    int updateByPrimaryKeySelective(Version record);

    int updateByPrimaryKeyWithBLOBs(Version record);

    int updateByPrimaryKey(Version record);

    int selectByAppkey(String appKey);

    List listByPageinfo(Integer start, Integer end);

}

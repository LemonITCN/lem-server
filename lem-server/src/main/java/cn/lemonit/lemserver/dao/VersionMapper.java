package cn.lemonit.lemserver.dao;

import cn.lemonit.lemserver.domian.Version;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VersionMapper {
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
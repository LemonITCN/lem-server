package cn.lemonit.lemserver.dao;

import cn.lemonit.lemserver.domian.Publish;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface PublishMapper {
    int deleteByPrimaryKey(String publishKey);

    int insert(Publish record);

    int insertSelective(Publish record);

    Publish selectByPrimaryKey(String publishKey);

    int updateByPrimaryKeySelective(Publish record);

    int updateByPrimaryKey(Publish record);

    Publish selectByTagkey(String tagKey);

    Publish selectByVersionkey(String versionKey);

    int updateByTagKey(Publish record);
}
package cn.lemonit.lemserver.service;

import cn.lemonit.lemserver.domian.Publish;

import java.util.HashMap;

public interface PublishService {
    int deleteByPrimaryKey(String publishKey);

    int deleteByTagKey(String tagKey);

    int insert(Publish record);

    int insertSelective(Publish record);

    Publish selectByPrimaryKey(String publishKey);

    int updateByPrimaryKeySelective(Publish record);

    int updateByPrimaryKey(Publish record);

    Publish selectByTagkey(String tagKey);

    int updateByTagKey(Publish record);

    Publish selectByVersionkey(String versionKey);

}

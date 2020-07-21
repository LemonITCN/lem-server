package cn.lemonit.lemserver.service;

import cn.lemonit.lemserver.domian.Tag;

import java.util.List;

public interface TagService {
    int deleteByPrimaryKey(String tagKey);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(String tagKey);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKeyWithBLOBs(Tag record);

    int updateByPrimaryKey(Tag record);

    Tag selectByNameInApp(Tag record);

    int selectByAppkey(String appKey);

    List listByPageinfo(Integer start, Integer end,String appKey);

}

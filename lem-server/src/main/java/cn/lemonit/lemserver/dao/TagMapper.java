package cn.lemonit.lemserver.dao;

import cn.lemonit.lemserver.domian.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
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

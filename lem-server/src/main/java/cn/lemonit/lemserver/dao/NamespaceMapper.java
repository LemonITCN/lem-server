package cn.lemonit.lemserver.dao;

import cn.lemonit.lemserver.domian.Namespace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NamespaceMapper {
    int deleteByPrimaryKey(String spaceKey);

    int insert(Namespace record);

    int insertSelective(Namespace record);

    Namespace selectByPrimaryKey(String spaceKey);

    int updateByPrimaryKeySelective(Namespace record);

    int updateByPrimaryKeyWithBLOBs(Namespace record);

    int updateByPrimaryKey(Namespace record);

    Namespace selectByName(String spaceName);

    List listByPageinfo(Integer start,Integer end);
}
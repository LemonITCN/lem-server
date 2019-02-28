package cn.lemonit.lemserver.service;

import cn.lemonit.lemserver.domian.Namespace;

import java.util.List;

public interface NamespaceService {
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

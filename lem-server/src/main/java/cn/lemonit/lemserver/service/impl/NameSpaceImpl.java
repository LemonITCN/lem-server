package cn.lemonit.lemserver.service.impl;

import cn.lemonit.lemserver.dao.NamespaceMapper;
import cn.lemonit.lemserver.domian.Namespace;
import cn.lemonit.lemserver.service.NamespaceService;
import cn.lemonit.lemserver.utils.BaseBusinessException;
import cn.lemonit.lemserver.utils.ErrorMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NameSpaceImpl implements NamespaceService {
    @Autowired
    private NamespaceMapper namespaceMapper;

    @Override
    public int deleteByPrimaryKey(String spaceKey){
        if (spaceKey==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        Namespace namespace = namespaceMapper.selectByPrimaryKey(spaceKey);
        if (namespace==null){
            throw new BaseBusinessException(ErrorMsg.namespace_does_not_exits.toString());
        }
        return namespaceMapper.deleteByPrimaryKey(spaceKey);
    }

    @Override
    public int insert(Namespace record){
        return namespaceMapper.insert(record);
    }

    @Override
    public int insertSelective(Namespace record){
        return namespaceMapper.insertSelective(record);
    }

    @Override
    public Namespace selectByPrimaryKey(String spaceKey){
        if (spaceKey==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        Namespace namespace = namespaceMapper.selectByPrimaryKey(spaceKey);
        if (namespace==null){
            throw new BaseBusinessException(ErrorMsg.namespace_does_not_exits.toString());
        }
        return namespace;
    }

    @Override
    public int updateByPrimaryKeySelective(Namespace record){
        if(record.getSpaceKey()==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        return namespaceMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(Namespace record){
        return namespaceMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(Namespace record){
        return namespaceMapper.updateByPrimaryKey(record);
    }

    @Override
    public Namespace selectByName(String spaceName){
        if(spaceName==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        Namespace namespace = namespaceMapper.selectByName(spaceName);
        if(namespace!=null){
            throw new BaseBusinessException(ErrorMsg.duplicate_namespace.toString());
        }
        return namespace;
    }

    @Override
    public List listByPageinfo(Integer start,Integer end){
        return namespaceMapper.listByPageinfo(start,end);
    }

}

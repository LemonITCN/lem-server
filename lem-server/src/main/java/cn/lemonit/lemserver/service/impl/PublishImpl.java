package cn.lemonit.lemserver.service.impl;

import cn.lemonit.lemserver.dao.PublishMapper;
import cn.lemonit.lemserver.dao.TagMapper;
import cn.lemonit.lemserver.dao.VersionMapper;
import cn.lemonit.lemserver.domian.Publish;
import cn.lemonit.lemserver.service.PublishService;
import cn.lemonit.lemserver.utils.BaseBusinessException;
import cn.lemonit.lemserver.utils.ErrorMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PublishImpl implements PublishService {
    @Autowired
    private PublishMapper publishMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private VersionMapper versionMapper;

    @Override
    public int deleteByPrimaryKey(String publishKey){
        if(publishKey==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        return publishMapper.deleteByPrimaryKey(publishKey);
    };

    @Override
    public int insert(Publish record){
        return publishMapper.insert(record);
    };

    @Override
    public int insertSelective(Publish record){
        if (record.getVersionKey()==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        if(tagMapper.selectByPrimaryKey(record.getTagKey())==null){
            throw new BaseBusinessException(ErrorMsg.tag_does_not_exit.toString());
        }
        if(versionMapper.selectByPrimaryKey(record.getVersionKey())==null){
            throw new BaseBusinessException(ErrorMsg.version_does_not_exit.toString());
        }
        return publishMapper.insertSelective(record);
    };

    @Override
    public Publish selectByPrimaryKey(String publishKey){
        return publishMapper.selectByPrimaryKey(publishKey);
    };

    @Override
    public int updateByPrimaryKeySelective(Publish record){
        return publishMapper.updateByPrimaryKeySelective(record);
    };

    @Override
    public int deleteByTagKey(String tagKey){
        Publish publish = publishMapper.selectByTagkey(tagKey);
        return publishMapper.deleteByPrimaryKey(publish.getPublishKey());
    };


    @Override
    public int updateByPrimaryKey(Publish record){
        return publishMapper.updateByPrimaryKey(record);
    };

    @Override
    public Publish selectByTagkey(String tagKey){
        if(tagKey==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        return publishMapper.selectByTagkey(tagKey);
    };

    @Override
    public Publish selectByVersionkey(String versionKey){
        if(versionKey==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        return publishMapper.selectByVersionkey(versionKey);
    };

    @Override
    public int updateByTagKey(Publish record){
        if (record.getVersionKey()==null){
            return deleteByTagKey(record.getTagKey());
        }
        if(tagMapper.selectByPrimaryKey(record.getTagKey())==null){
            throw new BaseBusinessException(ErrorMsg.tag_does_not_exit.toString());
        }
        return publishMapper.updateByTagKey(record);
    };

}

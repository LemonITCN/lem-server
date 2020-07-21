package cn.lemonit.lemserver.service.impl;

import cn.lemonit.lemserver.dao.AppMapper;
import cn.lemonit.lemserver.dao.TagMapper;
import cn.lemonit.lemserver.domian.Tag;
import cn.lemonit.lemserver.service.TagService;
import cn.lemonit.lemserver.utils.BaseBusinessException;
import cn.lemonit.lemserver.utils.ErrorMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private AppMapper appMapper;
    @Override
    public int deleteByPrimaryKey(String tagKey){
        if(tagKey==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        return tagMapper.deleteByPrimaryKey(tagKey);
    };

    @Override
    public int insert(Tag record){
        return tagMapper.insert(record);
    };

    @Override
    public int insertSelective(Tag record){
        if(record.getAppKey()==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        if(appMapper.selectByPrimaryKey(record.getAppKey())==null){
            throw new BaseBusinessException(ErrorMsg.app_does_not_exits.toString());
        }
        return tagMapper.insertSelective(record);
    };

    @Override
    public Tag selectByPrimaryKey(String tagKey){
        if(tagKey==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        Tag tag = tagMapper.selectByPrimaryKey(tagKey);
        if(tag==null){
            throw new BaseBusinessException(ErrorMsg.tag_does_not_exit.toString());
        }
        return tag;
    };

    @Override
    public int updateByPrimaryKeySelective(Tag record){
        if(record.getAppKey()==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        return tagMapper.updateByPrimaryKeySelective(record);
    };

    @Override
    public int updateByPrimaryKeyWithBLOBs(Tag record){
        return tagMapper.updateByPrimaryKeyWithBLOBs(record);
    };

    @Override
    public int updateByPrimaryKey(Tag record){
        return tagMapper.updateByPrimaryKey(record);
    };

    @Override
    public Tag selectByNameInApp(Tag info){
        if(info.getTagKey()==null||info.getAppKey()==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        Tag tag = tagMapper.selectByNameInApp(info);
        if(tag!=null){
            throw new BaseBusinessException(ErrorMsg.duplicate_tagname.toString());
        }
        return tag;
    };

    @Override
    public int selectByAppkey(String appKey){
        if (appKey==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        return tagMapper.selectByAppkey(appKey);
    };

    @Override
    public List listByPageinfo(Integer start, Integer end,String appKey){
        return tagMapper.listByPageinfo(start,end,appKey);
    };
}

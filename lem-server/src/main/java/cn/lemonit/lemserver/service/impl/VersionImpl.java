package cn.lemonit.lemserver.service.impl;

import cn.lemonit.lemserver.dao.VersionMapper;
import cn.lemonit.lemserver.domian.Version;
import cn.lemonit.lemserver.service.VersionService;
import cn.lemonit.lemserver.utils.BaseBusinessException;
import cn.lemonit.lemserver.utils.ErrorMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VersionImpl implements VersionService {
    @Autowired
    private VersionMapper versionMapper;

    @Override
    public int deleteByPrimaryKey(String versionKey){
        if (versionKey==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        Version version = versionMapper.selectByPrimaryKey(versionKey);
        if (version==null){
            throw new BaseBusinessException(ErrorMsg.version_does_not_exit.toString());
        }
        return versionMapper.deleteByPrimaryKey(versionKey);
    };

    @Override
    public int insert(Version record){
        return versionMapper.insert(record);
    };

    @Override
    public int insertSelective(Version record){
        return versionMapper.insertSelective(record);
    };

    @Override
    public Version selectByPrimaryKey(String versionKey){
        if (versionKey==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        Version version = versionMapper.selectByPrimaryKey(versionKey);

        return version;
    };

    @Override
    public int updateByPrimaryKeySelective(Version record){
        return versionMapper.updateByPrimaryKeySelective(record);
    };

    @Override
    public int updateByPrimaryKeyWithBLOBs(Version record){
        if (record.getVersionKey()==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        return versionMapper.updateByPrimaryKeyWithBLOBs(record);
    };

    @Override
    public int updateByPrimaryKey(Version record){
        return versionMapper.updateByPrimaryKey(record);
    };

    @Override
    public int selectByAppkey(String appKey){
        if (appKey==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        return versionMapper.selectByAppkey(appKey);
    };

    @Override
    public List listByPageinfo(Integer start, Integer end){
        return versionMapper.listByPageinfo(start,end);
    };
}

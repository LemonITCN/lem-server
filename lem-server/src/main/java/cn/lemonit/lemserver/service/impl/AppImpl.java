package cn.lemonit.lemserver.service.impl;

import cn.lemonit.lemserver.dao.AppMapper;
import cn.lemonit.lemserver.domian.App;
import cn.lemonit.lemserver.service.AppService;
import cn.lemonit.lemserver.utils.BaseBusinessException;
import cn.lemonit.lemserver.utils.ErrorMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppImpl implements AppService {
    @Autowired
    private AppMapper appMapper;

    @Override
    public int deleteByPrimaryKey(String appKey){
        if (appKey==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        App app = appMapper.selectByPrimaryKey(appKey);
        if (app==null){
            throw new BaseBusinessException(ErrorMsg.app_does_not_exits.toString());
        }
        return appMapper.deleteByPrimaryKey(appKey);
    };

    @Override
    public int insert(App record){
        return appMapper.insert(record);
    };

    @Override
    public int insertSelective(App record){
        if(record.getAppName()==null||record.getPlatform()==null||record.getBundleIdentifier()==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        if(!(record.getPlatform().equals("ios")||record.getPlatform().equals("android"))){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        return appMapper.insertSelective(record);
    };

    @Override
    public App selectByPrimaryKey(String appKey){
        if (appKey==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        App app = appMapper.selectByPrimaryKey(appKey);
        if (app==null){
            throw new BaseBusinessException(ErrorMsg.app_does_not_exits.toString());
        }
        return app;
    };

    @Override
    public int updateByPrimaryKeySelective(App record){
        if (record.getAppKey()==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        return appMapper.updateByPrimaryKeySelective(record);
    };

    @Override
    public int updateByPrimaryKeyWithBLOBs(App record){
        return appMapper.updateByPrimaryKeySelective(record);
    };

    @Override
    public int updateByPrimaryKey(App record){
        if (record.getAppKey()==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        return appMapper.updateByPrimaryKey(record);
    };

    @Override
    public int selectBySpacekey(String spaceKey){
        if (spaceKey==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        return appMapper.selectBySpacekey(spaceKey);
    };

    @Override
    public App selectByName(String appName){
        if(appName==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        App app = appMapper.selectByName(appName);
        if(app!=null){
            throw new BaseBusinessException(ErrorMsg.duplicate_appname.toString());
        }
        return app;
    };

    @Override
    public List listByPageinfo(Integer start, Integer end){
        return appMapper.listByPageinfo(start,end);
    };

    @Override
    public App isAppExits(String spaceKey,String appName,String platform){
        if(spaceKey==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        if(appName==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        if(platform==null){
            throw new BaseBusinessException(ErrorMsg.invalid_args.toString());
        }
        App app = appMapper.isAppExits(spaceKey,appName,platform);
        if (app!=null){
            throw new BaseBusinessException(ErrorMsg.duplicate_appname.toString());
        }
        return app;
    };

}

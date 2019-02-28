package cn.lemonit.lemserver.controller;

import cn.lemonit.lemserver.domian.Publish;
import cn.lemonit.lemserver.domian.Result;
import cn.lemonit.lemserver.service.PublishService;
import cn.lemonit.lemserver.utils.ErrorMsg;
import cn.lemonit.lemserver.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/v1/publish")
public class PublishController {
    @Autowired
    private PublishService publishService;

    public Date getDate (){
        return new Timestamp(new Date().getTime());
    };

    //关联publish
    @PostMapping("")
    public Result createPublish (@RequestBody Publish publish){
        publish.setPublishTime(getDate());
        if(publishService.selectByTagkey(publish.getTagKey())==null){
            publish.setPublishKey(UUID.randomUUID().toString());
            publishService.insertSelective(publish);
        }else {
            publishService.updateByTagKey(publish);
        }

        return ResultUtil.success(new HashMap<>());

    }

    //获取version
    @GetMapping("")
    public Result queryVersion(@RequestParam String tagKey){
        Publish publish = publishService.selectByTagkey(tagKey);
        HashMap response = new HashMap();
        response.put("tagKey",publish.getTagKey());
        response.put("versionKey",publish.getVersionKey());
        response.put("forceUpdate",publish.getForceUpdate());
        response.put("publishTime",publish.getPublishTime().getTime()+"");
        return ResultUtil.success(response);
    }


}

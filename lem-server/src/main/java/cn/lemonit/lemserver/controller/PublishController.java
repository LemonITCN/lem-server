package cn.lemonit.lemserver.controller;

import cn.lemonit.lemserver.domian.Publish;
import cn.lemonit.lemserver.domian.Result;
import cn.lemonit.lemserver.domian.Version;
import cn.lemonit.lemserver.service.PublishService;
import cn.lemonit.lemserver.service.VersionService;
import cn.lemonit.lemserver.utils.ErrorMsg;
import cn.lemonit.lemserver.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/v1/publish")
public class PublishController {
    @Autowired
    private PublishService publishService;

    @Autowired
    private VersionService versionService;

    public Date getDate (){
        return new Timestamp(new Date().getTime());
    };

    //关联publish
    @PostMapping("")
    @ApiOperation(value = "绑定version到tag",httpMethod = "POST",produces = MediaType.APPLICATION_JSON_VALUE,notes = "绑定version到tag")
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
        System.out.println("tagKey："+tagKey+"······");
        Publish publish = publishService.selectByTagkey(tagKey);
        if(publish==null){
            return ResultUtil.error("empty_data");
        }
        Version version = versionService.selectByPrimaryKey(publish.getVersionKey());
        HashMap response = new HashMap();
        response.put("tagKey",publish.getTagKey());
        response.put("versionDescription",version.getVersionDescription());
        response.put("versionKey",publish.getVersionKey());
        response.put("forceUpdate",publish.getForceUpdate());
        Calendar calendar = Calendar.getInstance();
        response.put("createTime",version.getCreateTime());
        return ResultUtil.success(response);
    }


}

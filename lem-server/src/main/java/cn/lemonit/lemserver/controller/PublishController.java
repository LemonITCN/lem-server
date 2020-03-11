package cn.lemonit.lemserver.controller;

import cn.lemonit.lemserver.domian.Publish;
import cn.lemonit.lemserver.domian.Result;
import cn.lemonit.lemserver.domian.Tag;
import cn.lemonit.lemserver.domian.Version;
import cn.lemonit.lemserver.service.PublishService;
import cn.lemonit.lemserver.service.TagService;
import cn.lemonit.lemserver.service.VersionService;
import cn.lemonit.lemserver.utils.ResultUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;


@RestController
@RequestMapping("/v1/publish")
public class PublishController {
    @Autowired
    private PublishService publishService;

    @Autowired
    private VersionService versionService;

    @Autowired
    private TagService tagService;

    @Autowired
    private RestTemplate restTemplate;

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

    //POST获取version，不同人员对应不同版本
    @PostMapping("/postVersion")
    @ApiOperation(value = "不同人员对应不同版本",httpMethod = "POST",produces = MediaType.APPLICATION_JSON_VALUE,notes = "不同人员对应不同版本")
    public Result postVersion (@RequestBody HashMap map){
        String tagKey = (String) map.get("tagKey");
        System.out.println("tagKey："+map+"······");
        Tag tag = tagService.selectByPrimaryKey(tagKey);
        Publish publish = publishService.selectByTagkey(tagKey);
        if(publish==null){
            return ResultUtil.error("empty_data");
        }
        if (tag.getUrl()==null||tag.getUrl().equals("")){
            //tag没配url
            Version version = versionService.selectByPrimaryKey(publish.getVersionKey());
            HashMap response = new HashMap();
            response.put("tagKey",publish.getTagKey());
            response.put("versionDescription",version.getVersionDescription());
            response.put("versionKey",publish.getVersionKey());
            response.put("forceUpdate",publish.getForceUpdate());
            response.put("createTime",version.getCreateTime());
            return ResultUtil.success(response);
        }else {
            HttpEntity<Map> httpEntity = new HttpEntity<>(map);
            ResponseEntity<String> response1  = restTemplate.exchange(tag.getUrl(), HttpMethod.POST, httpEntity,String.class);
            if(response1.getBody().equals("")||response1.getBody()==null){
                //新扬接口返回空
                Version version = versionService.selectByPrimaryKey(publish.getVersionKey());
                HashMap response = new HashMap();
                response.put("tagKey",publish.getTagKey());
                response.put("versionDescription",version.getVersionDescription());
                response.put("versionKey",publish.getVersionKey());
                response.put("forceUpdate",publish.getForceUpdate());
                response.put("createTime",version.getCreateTime());
                return ResultUtil.success(response);
            }else {
                HashMap response = new HashMap();
                Version version1 = versionService.selectByPrimaryKey(response1.getBody());
                if(version1==null){
                    version1 = versionService.selectByPrimaryKey(publish.getVersionKey());
                }
                response.put("versionDescription",version1.getVersionDescription());
                response.put("versionKey",response1.getBody());
                response.put("forceUpdate",1);
                response.put("createTime",version1.getCreateTime());
                return ResultUtil.success(response);
            }
        }
    }

}

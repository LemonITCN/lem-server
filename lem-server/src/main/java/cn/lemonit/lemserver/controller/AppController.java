package cn.lemonit.lemserver.controller;

import cn.lemonit.lemoi.Lemoi;
import cn.lemonit.lemoi.exceptions.ConfigInvalidException;
import cn.lemonit.lemserver.domian.App;
import cn.lemonit.lemserver.domian.Result;
import cn.lemonit.lemserver.service.TagService;
import cn.lemonit.lemserver.service.VersionService;
import cn.lemonit.lemserver.utils.LemoiUtil;
import cn.lemonit.lemserver.utils.ResultUtil;
import cn.lemonit.lemserver.utils.ErrorMsg;
import cn.lemonit.lemserver.service.AppService;
import cn.lemonit.lemserver.service.impl.AppImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.lang.*;

@RestController
@RequestMapping("/v1/app")
public class AppController {
    @Autowired
    private AppService appService;
    @Autowired
    private TagService tagService;
    @Autowired
    private VersionService versionService;

    private String localPtah = System.getProperty("java.io.tmpdir");

    public Date getDate (){
        return new Timestamp(new Date().getTime());
    };

    //创建app
    @PostMapping("")
    public Result createApp (@RequestParam(required = false,name = "appIcon") MultipartFile appIcon,String appName,String platform,String spaceKey,String appDescription) throws IOException, ConfigInvalidException {
        appService.isAppExits(spaceKey,appName,platform);

        String uuid = UUID.randomUUID().toString();
        App app = new App();
        app.setAppName(appName);
        app.setPlatform(platform);
        app.setAppKey(uuid);
        app.setAppDescription(appDescription);
        app.setCreateTime(getDate());
        app.setSpaceKey(spaceKey);
        if (appIcon!=null&&!appIcon.isEmpty()){
            String suffixName = appIcon.getOriginalFilename().substring(appIcon.getOriginalFilename().lastIndexOf("."));
            LemoiUtil.upload(appIcon,uuid);
            //例：3f947b8a-9c4d-4c48-af3d-db189354b8a6.jpg
            app.setAppIcon(uuid+suffixName);
        }
        appService.insertSelective(app);
        return ResultUtil.success(appService.selectByPrimaryKey(uuid));

    }

    //查询某个app
    @GetMapping("")
    public Result selectByPrimaryKey (@RequestParam String appKey){
        return ResultUtil.success(appService.selectByPrimaryKey(appKey));
    }

    //app图标
    @GetMapping("/icon")
    public void appicon(HttpServletRequest request, HttpServletResponse response, @RequestParam String appKey,@RequestParam(required = false) Integer size) throws IOException {
        App app = appService.selectByPrimaryKey(appKey);
        String appIcon = app.getAppIcon();
        LemoiUtil.download(appIcon);
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(appIcon,"UTF-8"));
        OutputStream out = response.getOutputStream();
        InputStream in = null;
        if(size==null){
            //没尺寸取原图
            in = new FileInputStream(localPtah+appIcon);
        }else {
            //转化尺寸返回
            String appIcon_sized = appKey+"_"+"size"+appIcon.substring(appIcon.lastIndexOf("."));
            InputStream in_old = new FileInputStream(localPtah+appIcon);
            OutputStream out_size = new FileOutputStream(localPtah+appIcon_sized);
            LemoiUtil.resizeImage(in_old,out_size,size);
            in = new FileInputStream(localPtah+appIcon_sized);
        }
        byte [] buffer = new byte[1024];
        int len = 0;
        while ((len=in.read(buffer))!=-1){
            out.write(buffer,0,len);
        }
        in.close();
    }

    //查询app列表
    @GetMapping("/list")
    public Result selectapp (@RequestParam(defaultValue="1") Integer page, @RequestParam(defaultValue = "10") Integer limit){
        return  ResultUtil.success(appService.listByPageinfo((page-1)*limit,limit));
    }

    //修改某个app的信息
    @PutMapping("")
    public Result updateByPrimaryKeySelective (@RequestParam(required = false,name = "appIcon") MultipartFile appIcon,String appKey,String appName,String appDescription) throws IOException, ConfigInvalidException {
        App app = appService.selectByPrimaryKey(appKey);
        appService.isAppExits(app.getSpaceKey(),appName,app.getPlatform());
        app.setAppName(appName);
        app.setAppDescription(appDescription);
        if (appIcon!=null&&!appIcon.isEmpty()){
            String suffixName = appIcon.getOriginalFilename().substring(appIcon.getOriginalFilename().lastIndexOf("."));
            LemoiUtil.upload(appIcon,appKey);
            //例：3f947b8a-9c4d-4c48-af3d-db189354b8a6.jpg
            app.setAppIcon(appKey+suffixName);
        }
        appService.updateByPrimaryKeySelective(app);
        return ResultUtil.success(appService.selectByPrimaryKey(appKey));
    }

    //删除APP
    @DeleteMapping("")
    public Result deleteByPrimaryKey(@RequestBody HashMap app){
        String appKey = app.get("appKey").toString();

//        app下是否有tag和version
        if (tagService.selectByAppkey(appKey)!=0){
            return ResultUtil.error(ErrorMsg.app_has_tag_or_version.toString());
        }
        if (versionService.selectByAppkey(appKey)!=0){
            return ResultUtil.error(ErrorMsg.app_has_tag_or_version.toString());
        }

        //删除
        appService.deleteByPrimaryKey(appKey);

        return ResultUtil.success(null);
    }
}


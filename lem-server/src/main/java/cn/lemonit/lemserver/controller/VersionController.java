package cn.lemonit.lemserver.controller;

import cn.lemonit.lemoi.exceptions.ConfigInvalidException;
import cn.lemonit.lemserver.domian.App;
import cn.lemonit.lemserver.domian.Result;
import cn.lemonit.lemserver.domian.Version;
import cn.lemonit.lemserver.service.AppService;
import cn.lemonit.lemserver.service.PublishService;
import cn.lemonit.lemserver.service.VersionService;
import cn.lemonit.lemserver.utils.ErrorMsg;
import cn.lemonit.lemserver.utils.LemoiUtil;
import cn.lemonit.lemserver.utils.ResultUtil;
import cn.lemonit.lemserver.utils.TencentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.*;

@RestController
@RequestMapping("/v1/version")
public class VersionController {
    @Autowired
    private VersionService versionService;
    @Autowired
    private AppService appService;
    @Autowired
    private PublishService publishService;

    public Date getDate (){
        return new Timestamp(new Date().getTime());
    };

    //上传version
    @PostMapping("")
    public Result createVersion ( @RequestParam(required = false,name = "file") MultipartFile file,@RequestParam(required = false,name = "versionIcon") MultipartFile versionIcon ,@RequestParam String appKey, @RequestParam(defaultValue="") String versionDescription) throws IOException, ConfigInvalidException {

        //检查appkey
        if(appKey==null){
            return ResultUtil.error(ErrorMsg.invalid_args.toString());
        }

        String uuid = UUID.randomUUID().toString();

        //创建version
        Version version = new Version();
        version.setCreateTime(getDate());
        version.setAppKey(appKey);
        version.setVersionDescription(versionDescription);
        version.setVersionKey(uuid);
        if(file!=null&&!file.isEmpty()){
            TencentUtil.upload(file,uuid);
        }
        if (versionIcon!=null&&!versionIcon.isEmpty()){
            String suffixName = versionIcon.getOriginalFilename().substring(versionIcon.getOriginalFilename().lastIndexOf("."));
            TencentUtil.upload(versionIcon,uuid);
            //例：3f947b8a-9c4d-4c48-af3d-db189354b8a6.jpg
            version.setVersionIcon(uuid+suffixName);
        }
        versionService.insertSelective(version);

        return ResultUtil.success(versionService.selectByPrimaryKey(uuid));
    }

    //修改version
    @PutMapping("")
    public Result updateVersion(@RequestParam(required = false,name = "file") MultipartFile file,@RequestParam(required = false,name = "versionIcon") MultipartFile versionIcon , @RequestParam String versionKey, @RequestParam(defaultValue="") String versionDescription) throws IOException, ConfigInvalidException {
        Version version = new Version();
        version.setCreateTime(getDate());
        version.setVersionDescription(versionDescription);
        version.setVersionKey(versionKey);
        if(file!=null&&!file.isEmpty()){
            TencentUtil.upload(file,versionKey);
        }
        if (versionIcon!=null&&!versionIcon.isEmpty()){
            String suffixName = versionIcon.getOriginalFilename().substring(versionIcon.getOriginalFilename().lastIndexOf("."));
            TencentUtil.upload(versionIcon,versionKey);
            //例：3f947b8a-9c4d-4c48-af3d-db189354b8a6.jpg
            version.setVersionIcon(versionKey+suffixName);
        }
        versionService.updateByPrimaryKeySelective(version);
        return ResultUtil.success(versionService.selectByPrimaryKey(versionKey));
    }

    //创建文件
    public void createFile(String filename,String content) throws IOException {
        String localPtah = System.getProperty("java.io.tmpdir");
        File dir = new File(localPtah);
        // 一、检查放置文件的文件夹路径是否存在，不存在则创建
        if (!dir.exists()) {
            dir.mkdirs();// mkdirs创建多级目录
        }
        File checkFile = new File(localPtah + filename);
        FileWriter writer = null;
        try {
            // 二、检查目标文件是否存在，不存在则创建
            if (!checkFile.exists()) {
                checkFile.createNewFile();// 创建目标文件
            }
            // 三、向目标文件中写入内容
            // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
            writer = new FileWriter(checkFile, false);
            writer.append(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer)
                writer.close();
        }
    }

    //ios plist文件
    @GetMapping("/plist")
    public void getplist(HttpServletRequest request, HttpServletResponse response,@RequestParam String versionKey,@RequestParam(required = false) Integer size ) throws IOException {
        Version version = versionService.selectByPrimaryKey(versionKey);
        App app = appService.selectByPrimaryKey(version.getAppKey());
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(versionKey+".plist","UTF-8"));
        String plistContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">" +
                "<plist version=\"1.0\">" +
                "<dict>" +
                "<key>items</key>" +
                "<array>" +
                "<dict>" +
                "<key>assets</key>" +
                "<array>" +
                "<dict>" +
                "<key>kind</key>" +
                "<string>software-package</string>" +
                "<key>url</key>" +
//                "<string>http://192.168.11.117:8091/v1/version/download?versionKey="+versionKey+"</string>" +
                "<string>https://lem-repo-1255447022.cos.ap-beijing.myqcloud.com/"+versionKey+".ipa</string>" +
                "</dict>" +
                "<dict>" +
                "<key>kind</key>" +
                "<string>display-image</string>" +
                "<key>needs-shine</key>" +
                "<true/>" +
                "<key>url</key>" +
                "<string>https://oss.lemonit.cn/lem/v1/version/icon?versionKey="+versionKey+"</string>" +
                "</dict>" +
                "<dict>" +
                "<key>kind</key>" +
                "<string>full-size-image</string>" +
                "<key>needs-shine</key>" +
                "<true/>" +
                "<key>url</key>" +
                "<string>https://oss.lemonit.cn/lem/v1/version/icon?versionKey="+versionKey+"size="+size+"</string>" +
                "</dict>" +
                "</array>" +
                "<key>metadata</key>" +
                "<dict>" +
                "<key>bundle-identifier</key>" +
                "<string>"+app.getBundleIdentifier()+"</string>" +
                "<key>bundle-version</key>" +
                "<string>1.0</string>" +
                "<key>kind</key>" +
                "<string>software</string>" +
                "<key>title</key>" +
                "<string>"+app.getAppName()+"</string>" +
                "</dict>" +
                "</dict>" +
                "</array>" +
                "</dict>"+
                "</plist>";
        createFile(versionKey+".plist",plistContent);
        OutputStream out = response.getOutputStream();

        String localPtah = System.getProperty("java.io.tmpdir");
        InputStream in = new FileInputStream(localPtah+versionKey+".plist");
        byte [] buffer = new byte[1024];
        int len = 0;
        while ((len=in.read(buffer))!=-1){
            out.write(buffer,0,len);
        }
        in.close();
    }

    //下载文件
    @GetMapping("/download")
    public Result download (@RequestParam String versionKey){
        HashMap response = new HashMap();
        response.put("ossUrl","https://lem-repo-1255447022.cos.ap-beijing.myqcloud.com/"+versionKey+".apk");
        return ResultUtil.success(response);
    }

    @GetMapping("/icon")
    public void geticon(HttpServletRequest request, HttpServletResponse response, @RequestParam String versionKey,@RequestParam(required = false) Integer size) throws IOException  {
        String localPtah = System.getProperty("java.io.tmpdir");
        Version version = versionService.selectByPrimaryKey(versionKey);
        String versionIcon = version.getVersionIcon();
        if (versionIcon==null){
            versionIcon = appService.selectByPrimaryKey(version.getAppKey()).getAppIcon();
        }
        LemoiUtil.download(versionIcon);
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(versionIcon,"UTF-8"));
        OutputStream out = response.getOutputStream();
        InputStream in = null;
        if(size==null){
            //没尺寸取原图
            in = new FileInputStream(localPtah+versionIcon);
        }else {
            //转化尺寸返回
            String versionIcon_sized = versionKey+"_"+"size"+versionIcon.substring(versionIcon.lastIndexOf("."));
            System.out.print(versionIcon_sized);
            InputStream in_old = new FileInputStream(localPtah+versionIcon);
            OutputStream out_size = new FileOutputStream(localPtah+versionIcon_sized);
            LemoiUtil.resizeImage(in_old,out_size,size);
            in = new FileInputStream(localPtah+versionIcon_sized);
        }
        byte [] buffer = new byte[1024];
        int len = 0;
        while ((len=in.read(buffer))!=-1){
            out.write(buffer,0,len);
        }
        in.close();
    }

    @DeleteMapping("")
    public Result deleteByPrimaryKey(@RequestBody HashMap version){
        String versionKey = version.get("versionKey").toString();

        //是否有tag和version绑定
        if(publishService.selectByVersionkey(versionKey)!=null){
            return ResultUtil.error(ErrorMsg.version_has_related_tag.toString());
        };

        //删除
        versionService.deleteByPrimaryKey(versionKey);

        return ResultUtil.success(null);
    }

    //查询version列表
    @GetMapping("/list")
    public Result selectversion (@RequestParam(defaultValue="1") Integer page, @RequestParam(defaultValue = "10") Integer limit){
        return  ResultUtil.success(versionService.listByPageinfo((page-1)*limit,limit));
    }

}

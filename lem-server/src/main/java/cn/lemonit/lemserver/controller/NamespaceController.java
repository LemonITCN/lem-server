package cn.lemonit.lemserver.controller;

import cn.lemonit.lemserver.domian.Namespace;
import cn.lemonit.lemserver.domian.Result;
import cn.lemonit.lemserver.utils.ResultUtil;
import cn.lemonit.lemserver.utils.ErrorMsg;
import cn.lemonit.lemserver.service.NamespaceService;
import cn.lemonit.lemserver.service.AppService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.lang.*;

@RestController
@RequestMapping("/v1/namespace")
public class NamespaceController {
    @Autowired
    private NamespaceService namespaceService;
    @Autowired
    private AppService appService;

    public Date getDate (){
        return new Timestamp(new Date().getTime());
    };

    //创建namespace
    @PostMapping("")
    public Result createNamespace (@RequestBody Namespace namespace, BindingResult bindingResult){

        //namespace是否存在
        namespaceService.selectByName(namespace.getSpaceName());

        //创建namespace
        namespace.setCreateTime(getDate());
        namespace.setSpaceKey(UUID.randomUUID().toString());
        namespaceService.insertSelective(namespace);

        return ResultUtil.success(namespaceService.selectByPrimaryKey(namespace.getSpaceKey()));

    }

    //查询某个命名空间
    @GetMapping("")
    public Result selectByPrimaryKey (@RequestParam String spaceKey){
        return ResultUtil.success(namespaceService.selectByPrimaryKey(spaceKey));
    }

    //查询命名空间列表
    @GetMapping("/list")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
    public Result selecAll (@RequestParam(defaultValue="1") Integer page, @RequestParam(defaultValue = "10") Integer limit){
        return  ResultUtil.success(namespaceService.listByPageinfo((page-1)*limit,limit));
    }

    //修改某个命名空间的信息
    @PutMapping("")
    public Result updateByPrimaryKeySelective (@RequestBody Namespace namespace, BindingResult bindingResult){
        namespaceService.selectByName(namespace.getSpaceName());
        namespaceService.updateByPrimaryKeySelective(namespace);
        return ResultUtil.success(namespaceService.selectByPrimaryKey(namespace.getSpaceKey()));
    }

    //删除命名空间
    @DeleteMapping("")
    public Result deleteByPrimaryKey(@RequestBody HashMap space){
        String spaceKey = space.get("spaceKey").toString();

        //namespace下是否有app
        if (appService.selectBySpacekey(spaceKey)!=0){
            return ResultUtil.error(ErrorMsg.namespace_has_apps.toString());
        }

        //删除
        namespaceService.deleteByPrimaryKey(spaceKey);

        return ResultUtil.success(null);
    }
}


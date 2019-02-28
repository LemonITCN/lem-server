package cn.lemonit.lemserver.utils;

public enum ErrorMsg {
  // https://github.com/LemonITCN/lem/wiki/LEM-RestfulAPI-ErrCodes

  // 当服务器中发生了某些异常或错误的时候，不希望把具体的错误信息暴露给客户端的时候，返回此错误码
  common_server_internal_error,

  // 当参数缺失，或者类型不对的时候，返回此错误码
  invalid_args,

  // 当调用删除接口删除不存在的资源时，返回此错误码
  resource_does_not_exit,

  // 当命名空间已存在时，返回此错误码
  duplicate_namespace,

  // 当要删除的命名空间下有app时，返回此错误码
  namespace_has_apps,

  // 当app已存在时，返回此错误码
  duplicate_appname,

  // 当要删除的app下有tag或version时，返回此错误码
  app_has_tag_or_version,

  // 当指定的命名空间不存在时，返回此错误码
  namespace_does_not_exits,

  // 当上传文件的类型不正确时，返回此错误码
  unsupport_filetype,

  // 当指定的app不存在时，返回此错误码
  app_does_not_exits,

  // 当app已存在要创建的tag时，返回此错误码
  duplicate_tagname,

  // 当要删除的tag已关联version时，返回此错误码
  tag_has_related_version,

  // 当要删除的version已关联tag时，返回此错误码
  version_has_related_tag,

  // 指定的tag不存在，返回此错误码
  tag_does_not_exit,

  // 指定的version不存在，返回此错误码
  version_does_not_exit
}

<p align="center">
  <img src="https://s3.ax1x.com/2021/02/01/yZxwN9.png" border="0" />
</p>
<p align="center">
  <a href="https://www.apache.org/licenses/LICENSE-2.0" target="_blank">
    <img src="https://img.shields.io/badge/license-Apache%202-green.svg" />
  </a>
  <a href="https://search.maven.org/search?q=insp" target="_blank">
    <img src="https://img.shields.io/maven-central/v/cn.is4j.insp/insp4j" />
  </a>
  <a href="https://github.com/zengzhihong/insp4j" target="_blank">
    <img src="https://img.shields.io/github/stars/zengzhihong/insp4j?style=social" />
  </a>
  <a href="https://gitee.com/zengzhihong/insp4j" target="_blank">
    <img src="https://gitee.com/zengzhihong/insp4j/badge/star.svg?theme=dark" />
  </a>
</p>
<p align="center"><strong>QQ群：336752559</strong></p>

## 简介

* insp4j为inspector的缩写，中文含义检查员。
* 基于Spring-EL、AOP，更加灵活易用的权限控制框架，支持权限分组，可用于一套系统多种权限隔离校验，使用简单、易于扩展，支持Servlet、Reactive。
* 参考了SpringSecurity、Expression的设计，定制扩展灵活。

## 需求背景

一个大的项目里会存在各种子业务系统，比如xx营销平台，提供给B端商户使用，其中包含的子业务系统有：<br>
商城系统（多门店）、仓库系统（多仓库）、运营系统（多部门），系统交给不同的人员进行维护管理，这里统称操作员。<br>
每个操作员能操作的功能，操作的门店/仓库/部门都是不一样的，甚至是不同的用户体系，不同的粗细粒度，<br>
一般我们每个子业务系统会给不同的开发小组开发，如果没有设计统一的权限处理方案，各个小组会各自实现自己所负责的业务系统权限，很难管理。<br>
insp4j就是抽象了一个group，把每个不同的子系统的权限设计区分隔离，不同的group，不同的用户、操作权限、数据权限，这些信息统一封装在InspAuthentication，需要业务系统来构造。<br>
insp4j没有实现用户认证、授权，更没涉及到数据库层面上的数据范围过滤，只对业务系统构造的InspAuthentication、@Insp注解上定义的权限标识基于AOP实现拦截校验，是轻量级的权限控制实现。

## 快速开始

* 导包

```xml

<dependencies>
    <!-- 如果是Servlet项目则引入 -->
    <dependency>
        <groupId>cn.is4j.insp</groupId>
        <artifactId>insp4j-web-spring-boot-starter</artifactId>
        <version>${latest.version}</version>
    </dependency>
    <!-- 如果是Reactive项目则引入 -->
    <dependency>
        <groupId>cn.is4j.insp</groupId>
        <artifactId>insp4j-reactive-spring-boot-starter</artifactId>
        <version>${latest.version}</version>
    </dependency>
</dependencies>
```

* 配置

```java
/**
 * 实现接口cn.is4j.insp.web.service.InspWebAuthenticationService返回认证用户的权限 并交由spring ioc管理
 */
@Component
public class InspWebAuthenticationServiceImpl implements InspWebAuthenticationService {

    @Autowired
    private DeptService deptService;
    @Autowired
    private MerchantService merchantService;

    /**
     * 建议走缓存 每次拦截都会进入该方法 这里只是演示
     */
    @Override
    public InspAuthentication loadAuthentication(HttpServletRequest httpServletRequest, InspMetadataSource metadataSource) {
        // groupName可以用来做用户/权限隔离
        // 如系统用户在sys_user表,权限在sys_authorities表，一般用户（商户）在biz_merchant表，权限在biz_merchant_authorities表
        if ("system".equals(metadataSource.getGroupName())) {
            String userId = SecurityUtil.getUserId();
            List<String> funcAuthorities = SecurityUtil.getUser().getAuthorities();
            List<String> dataAuthorities = deptService.listDeptId();
            return new InspAuthentication(userId, funcAuthorities, dataAuthorities);
        }
        if ("merchant".equals(metadataSource.getGroupName())) {
            String userId = merchantService.getIdByToken(httpServletRequest.getHeader("token"));
            List<String> funcAuthorities = merchantService.listFuncAuthorities(userId);
            List<String> dataAuthorities = merchantService.listDataAuthorities(userId);
            return new InspAuthentication(userId, funcAuthorities, dataAuthorities);
        }
        throw new RuntimeException("error groupName");
    }
}
```

* 使用

```java

@RestController
@RequestMapping("/dept")
public class DeptController {

    //操作权限
    @Insp("hasFunc('dept:list')")
    @GetMapping("/list")
    public R<?> list() {
        return ok();
    }

    // 操作权限加数据权限 有没有操作该id的权限
    // 支持Spring-EL表达式
    @Insp(value = "hasFuncData('dept:update',#id)")
    @GetMapping("/updateById")
    public R<?> updateById(@RequestParam Long id) {
        return ok();
    }

    // 权限分组 业务系统/运营系统 可能用户账号体系不一样，权限体系也是分开设计的，就需要用到groupName来实现分组
    // 一个分组可以单独一套用户/权限
    @Insp(value = "hasFuncData('dept:delete',#id)", groupName = "system")
    @GetMapping("/deleteById")
    public R<?> deleteById(@RequestParam Long id) {
        return ok();
    }

}
```

* 异常

  没有权限时默认异常,可定制

```json
{
  "code": 403,
  "message": "deny of access"
}
```

## 计划

- 系统与系统（微服务）之间的调用权限
- 用户的认证与授权?OAuth?.待定
- Spring-EL method IDE识别，点击跳转直接跳转到目标实现?.暂时还没研究出来(inject language or reference)不太理想

## ps

这种基于AOP对注解上的权限标识做拦截是很简单的，相信大家都会，目前提供的功能比较简单，不喜勿喷<br>
当下主要是解决我们项目里各种系统权限控制不统一，每个子系统不同小组在开发，各自做各自的，增加一个子系统就要做一套权限，没有统一模式。<br>

## 开源协议

This project is licensed under the Apache-2.0 License - see the [LICENSE.md](LICENSE.md) file for details
 
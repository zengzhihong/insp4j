
# Insp轻量级权限框架

## 简介
 
基于Spring-EL、AOP，更加灵活易用的权限控制框架，支持权限分组，可用于一套系统多种权限隔离校验，使用简单、易于扩展，支持Servlet、Reactive。

## 快速开始

* 导包

```
<!-- Servlet ->
<dependency>
    <groupId>cn.is4j.insp</groupId>
    <artifactId>insp4j-web</artifactId>
    <version>1.0.1</version>
</dependency>
```

```
<!-- Reactive ->
<dependency>
    <groupId>cn.is4j.insp</groupId>
    <artifactId>insp4j-reactive</artifactId>
    <version>1.0.1</version>
</dependency>
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
    public InspAuthentication loadAuthentication(HttpServletRequest httpServletRequest, String groupName) {
        // groupName可以用来做用户/权限隔离
        // 如系统用户在sys_user表,权限在sys_authorities表，一般用户（商户）在biz_merchant表，权限在biz_merchant_authorities表
        if("system".equals(groupName)){
            String userId = SecurityUtil.getUserId();
            List<String> funcAuthorities = SecurityUtil.getUser().getAuthorities();
            List<String> dataAuthorities = deptService.listDeptId();
            return new InspAuthentication(userId, funcAuthorities, dataAuthorities);
        }
        if("merchant".equals(groupName)){
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
    @Insp(value = "hasFuncData('dept:update',#id)", groupName = "system")
    @GetMapping("/updateById")
    public R<?> info(@RequestParam Long id) {
        return ok();
    }

}
```
* 异常

    没有权限时默认异常,可定制
    
```json
{"code":403,"message":"deny of access"}
```

## 开源协议
 
This project is licensed under the Apache-2.0 License - see the [LICENSE.md](LICENSE.md) file for details
 
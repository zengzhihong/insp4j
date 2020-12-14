# v1.2.0

- feat: 添加CHANGELOG.md文件
- refactor: insp4j-web项目更名为insp4j-web-spring-boot-starter、insp4j-reactive项目更名为insp4j-reactive-spring-boot-starter

# v1.1.3

- feat: 新增角色权限 hasRole hasAnyRole

# v1.1.2

- fix: 解决InspAuthenticationService依赖的bean无法被正常代理问题(代理失效)

# v1.1.1

- update: 自定义异常处理传递metadataSource

# v1.1.0

- update: 优化InspContext ThreadLocal实现,支持父子线程获取上下文
- refactor: 重构el表达式,支持el参数传递到InspAuthenticationService,更加灵活控制权限
- update: 优化ExpressionRoot校验逻辑
- feat: 支持自定义异常处理bean
- style: 代码优化

# v1.0.1

- style: 包名规范
- style: pom scm

# v1.0.0

- feat: 框架设计,初始提交
- feat: aop支持注解拦截权限校验
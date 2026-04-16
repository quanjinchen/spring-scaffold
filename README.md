# spring-scaffold 底座能力说明

## 1. 项目定位

`spring-scaffold` 是一套面向后台类应用的基础脚手架，核心目标是沉淀可复用的通用底座能力，而不是承载某个具体业务域。

它的价值主要体现在：

- 统一技术栈与分层结构
- 统一基础协议与运行规范
- 统一认证、权限、日志、缓存、文件等基础设施
- 为后续业务模块提供稳定扩展点

从定位上看，它更像“应用底座”而不是“业务系统”。

## 2. 模块结构

当前模块如下：

- `common`
- `system`
- `framework`
- `file`
- `console`

依赖关系如下：

```text
console
├─ framework
│  └─ system
│     └─ common
└─ file
   └─ common
```

各模块职责如下：

- `common`：公共协议、工具、校验、分页、加解密等基础能力
- `system`：系统级领域模型与持久层抽象
- `framework`：运行时基础设施与通用装配层
- `file`：独立的对象存储文件基础设施
- `console`：启动模块与底座能力集成出口

这套结构的核心意义是：

- 公共能力不散落在业务代码中
- 运行机制集中维护
- 新业务接入时不需要重复搭建底层设施

## 3. 技术底座

项目当前使用的核心技术包括：

- `Spring Boot 2.7.18`
- `Java 8`
- `MyBatis-Plus 3.5.7`
- `Sa-Token 1.39.0`
- `Redis`
- `SpringDoc / OpenAPI`
- `AWS S3 SDK`
- `Apache Tika`
- `MapStruct`
- `EasyExcel`
- `RestTemplate + Apache HttpClient`

这些技术在项目中分别承担不同的底座职责：

- Spring Boot 负责应用装配与运行容器
- MyBatis-Plus 负责持久层增强
- Sa-Token 负责认证与权限框架
- Redis 负责状态与缓存支撑
- S3 SDK 负责对象存储抽象
- EasyExcel 负责导出类基础能力

## 4. common 模块底座能力

`common` 是整个项目的公共协议层和基础组件层。

### 4.1 统一协议能力

核心类：

- `RespInfo`
- `ResultCode`
- `BizException`

能力：

- 统一接口返回结构
- 统一错误码语义
- 统一业务异常表达方式

这让所有上层模块都围绕同一套交互协议开发。

### 4.2 分页抽象能力

核心类：

- `PageReqParam`
- `PageData`
- `PageUtils`

能力：

- 统一分页入参与出参
- 提供分页结果转换工具
- 为不同分页实现方式提供统一抽象

### 4.3 实体基类能力

核心类：

- `BaseEntity`

能力：

- 统一实体公共字段定义
- 统一 ID、审计字段、逻辑删除字段风格

### 4.4 数据保护能力

核心类：

- `EncryptField`
- `Encryptor`
- `DefaultEncryptor`
- `EncryptTypeHandler`
- `AesCbcUtils`
- `JsonCrypto*`

能力：

- 支持字段级持久化加解密
- 支持 JSON 链路的字段级加解密扩展
- 为敏感数据处理提供统一基础设施

### 4.5 校验能力

核心类：

- `validation/config/ValidatorConfig`
- `annotation/*`
- `validation/validator/*`

能力：

- 开启 fail-fast 校验
- 支持方法参数校验
- 支持常用自定义校验器扩展

### 4.6 枚举标准化能力

核心类：

- `CodeEnum`
- `AutoEnumTypeHandler`

能力：

- 统一带 code 枚举的持久化语义
- 为领域枚举标准化提供底座支持

### 4.7 通用工具能力

核心类：

- `JsonUtils`
- `SpringUtils`
- `PasswordUtils`

能力：

- JSON 工具
- Spring 上下文工具
- 通用安全辅助工具

## 5. system 模块底座能力

`system` 的定位是系统级领域基础层。

它主要承担两类职责：

- 定义系统级实体模型
- 定义统一 Mapper 抽象

这个模块的意义在于：

- 为上层基础设施提供统一的数据语义
- 为权限、审计、上下文等能力提供模型依赖
- 将领域模型与运行时装配解耦

`system` 本身不强调业务流程，而强调“系统公共模型”的稳定性。

## 6. framework 模块底座能力

`framework` 是项目里最核心的基础设施装配层，几乎所有可复用的运行时能力都集中在这里。

### 6.1 持久层增强装配

核心类：

- `mybatisplus/config/MyBatisPlusConfig`

能力：

- 统一扫描 Mapper
- 统一接入分页拦截器
- 统一接入乐观锁拦截器
- 统一接入数据权限拦截器
- 统一接入字段加密 TypeHandler
- 统一接入默认枚举 TypeHandler

### 6.2 缓存与状态基础设施

核心类：

- `redis/config/RedisConfig`
- `redis/config/PrefixKeySerializer`
- `redis/manager/RedisManager`
- `redis/manager/RedisManagerImpl`

能力：

- 统一 RedisTemplate 配置
- 支持 key 前缀隔离
- 提供统一 Redis 访问封装

这部分是整个项目的状态型基础设施入口。

### 6.3 认证与权限框架能力

核心类：

- `satoken/SaTokenConfig`
- `satoken/SaPermissionImpl`
- `satoken/PermissionService`
- `satoken/LoginUserContext`

能力：

- 统一认证接入
- 统一权限校验接入
- 当前登录上下文获取
- 统一过滤器与排除路径配置

这部分属于安全基础设施，而不是具体业务功能。

### 6.4 异步执行能力

核心类：

- `threadpool/ExecutorConfig`
- `threadpool/ContextDecorator`
- `async/AsyncManager`
- `async/AsyncManagerImpl`

能力：

- 提供统一异步线程池
- 提供统一异步执行入口
- 支持上下文透传

### 6.5 请求链路与日志能力

核心类：

- `web/filter/TraceFilter`
- `web/trace/TraceContext`
- `web/trace/TraceContextHolder`
- `web/interceptor/RequestInterceptor`
- `web/log/WebLogAspect`

能力：

- 统一请求 Trace 标识
- 统一链路上下文管理
- 统一请求日志采集
- 统一客户端环境信息采集

### 6.6 常见命名语义说明

为了避免阅读代码时只看名字却误解职责，这里补充项目中几类常见命名的语义边界。

#### 6.6.1 Utils

典型类：

- `common/utils/JsonUtils`
- `framework/utils/IpUtils`
- `common/utils/PasswordUtils`

语义：

- `Utils` 主要表示通用静态工具能力
- 通常不承载业务状态
- 主要用于字符串、JSON、密码、IP、时间、文件等纯辅助处理

理解方式：

- 更偏“纯工具函数集合”
- 输入给定，输出基本确定
- 不强调当前请求、当前登录态这类上下文语义

#### 6.6.2 Context

典型类：

- `web/trace/TraceContext`
- `web/trace/TraceContextHolder`
- `satoken/LoginUserContext`

语义：

- `Context` 表示“当前环境中的一组上下文信息”
- 常用于表达当前请求、当前线程、当前登录用户、当前链路等运行时状态
- 这类对象通常不是业务处理器，而是上下文读取入口或上下文载体

理解方式：

- `TraceContext` 是当前请求链路信息的载体
- `TraceContextHolder` 是当前线程中的链路上下文存取入口
- `LoginUserContext` 是当前登录用户信息的读取入口

为什么 `LoginUserContext` 不叫 `LoginUserUtils`：

- 它虽然使用方式上像工具，但本质上读取的是“当前登录上下文”
- 它依赖运行时登录态，而不是纯静态计算
- 因此使用 `Context` 比 `Utils` 更准确

#### 6.6.3 Manager

典型类：

- `system/manager/UserManager`
- `system/manager/SysMenuManager`
- `file/manager/FileManager`

语义：

- `Manager` 是数据访问与领域对象操作的封装层
- 负责承接 `service` 下钻的数据查询、保存、删除、关系维护
- `manager impl` 才允许直接依赖 `mapper`

理解方式：

- `Manager` 更接近“数据访问封装器”
- 关注的是实体、查询条件、持久化动作
- 不负责完整业务编排

#### 6.6.4 Service

典型类：

- `console/service/UserService`
- `console/service/MenuService`
- `console/service/AdminAuthService`

语义：

- `Service` 是业务编排层
- 负责业务校验、流程组织、跨 manager 协作、接口出参组织
- 成功响应统一由 `service/serviceImpl` 返回 `RespInfo`

理解方式：

- `Service` 更接近“业务流程执行器”
- 关注的是接口行为和业务语义，而不是单点数据访问

#### 6.6.5 Filter 与 Aspect

典型类：

- `web/filter/TraceFilter`
- `web/log/WebLogAspect`
- `operationlog/aspect/OperateLogAspect`

语义：

- `Filter` 作用于 HTTP 请求入口，位于请求进入 Controller 之前
- `Aspect` 作用于方法执行过程，用于在方法前后统一织入逻辑

理解方式：

- `TraceFilter` 负责请求最前面的链路初始化、耗时统计、上下文清理
- `WebLogAspect` 负责在 Controller 方法前后打印请求参数和响应信息
- `OperateLogAspect` 负责对带 `@OperateLog` 的方法记录业务操作日志

#### 6.6.6 ThreadLocal、MDC、上下文透传

相关类：

- `web/trace/TraceContextHolder`
- `threadpool/ContextDecorator`
- `web/filter/TraceFilter`

语义：

- `ThreadLocal` 可以理解为“当前线程私有的小型上下文存储”
- `MDC` 是日志框架提供的线程级键值存储，常用于给日志附加 `traceId`
- 当异步任务切到线程池中的其他线程时，原线程的上下文默认不会自动带过去
- `ContextDecorator` 的作用就是把原线程中的 `TraceContext` 和 `MDC traceId` 复制到异步线程，执行后再清理

理解方式：

- `TraceFilter` 在请求开始时创建链路上下文
- `TraceContextHolder` 负责把上下文绑定到当前线程
- `ContextDecorator` 负责异步线程中的上下文透传

### 6.6 Web 上下文能力

核心类：

- `utils/WebFrameworkUtils`
- `utils/IpUtils`
- `utils/UserAgentUtils`

能力：

- 获取当前请求与响应
- 获取客户端网络信息
- 获取客户端环境信息

### 6.7 全局异常处理能力

核心类：

- `web/GlobalExceptionHandler`

能力：

- 统一业务异常处理
- 统一认证鉴权异常处理
- 统一参数绑定与校验异常处理
- 统一请求体解析异常处理

### 6.8 HTTP 客户端封装能力

核心类：

- `http/RestTemplateConfig`
- `http/RestTemplateHelper`

能力：

- 统一 HTTP 客户端配置
- 统一外部请求调用入口
- 内置重试能力

### 6.9 数据权限接入能力

核心类：


能力：

- 提供数据权限注解入口
- 提供数据权限上下文承载能力
- 提供持久层拦截挂载点

当前这里已经具备底座入口，后续只需要补具体规则实现。

### 6.10 导入导出基础能力

核心类：

- `utils/ExcelHeaderUtil`
- `utils/ReportUtils`

能力：

- 提供 Excel 头结构构造能力
- 提供统一导出输出能力

### 6.11 开发协同能力

核心类：

- `config/OpenApiConfig`

能力：

- 自动生成 OpenAPI 文档
- 提供统一接口文档访问入口

## 7. file 模块底座能力

`file` 模块是独立的文件基础设施模块，当前只保留对象存储路线。

### 7.1 对象存储装配能力

核心类：

- `config/FileStorageProperties`
- `config/OssConfig`

能力：

- 统一对象存储配置装配
- 初始化存储客户端
- 执行存储桶检查与初始化
- 兼容 S3 协议对象存储

### 7.2 文件管理能力

核心类：

- `manager/FileManager`
- `manager/impl/FileManagerImpl`
- `entity/FileRecord`

能力：

- 统一文件写入
- 统一文件读取
- 统一文件删除
- 统一文件元数据管理

### 7.3 文件工具能力

核心类：

- `utils/FileUtils`
- `utils/DataUrlUtils`

能力：

- 文件类型识别
- 文件名与后缀处理
- 数据内容解析与转换

这意味着文件能力已经独立成可复用基础设施，而不是耦合在具体业务模块中。

## 8. console 模块在底座中的作用

`console` 在底座体系中的作用主要有三点：

- 作为启动模块承载全部基础设施装配
- 作为底座能力的集成出口
- 作为默认接入样例验证底座可运行性

因此 `console` 更接近“底座装配验证层”，而不是 README 需要重点展开的业务层。

## 9. 当前已经沉淀的底座能力清单

从基础设施视角看，`spring-scaffold` 当前已经沉淀出以下核心能力：

- 多模块分层架构
- 统一返回协议
- 统一异常协议
- 统一错误码体系
- 分页抽象能力
- 实体基类规范
- 数据保护能力
- fail-fast 校验能力
- 自定义校验扩展能力
- 枚举标准化 TypeHandler
- 持久层增强装配
- Redis 基础设施
- 状态与缓存支撑能力
- 认证与权限框架能力
- 登录上下文能力
- 异步执行能力
- 请求链路追踪能力
- 统一请求日志能力
- Web 上下文能力
- 数据权限接入能力
- HTTP 客户端封装能力
- 导入导出基础能力
- OpenAPI 文档能力
- 对象存储文件基础设施

## 10. 适合继续沉淀的方向

基于当前底座结构，后续最值得继续下沉的方向包括：

- 数据权限规则标准化
- 账号安全策略抽象
- 通知中心
- 更强的导入导出模板能力
- 更完整的外部系统集成规范
- 文件访问控制与预签名能力
- 更细粒度的审计与链路采集能力

## 11. 总结

`spring-scaffold` 当前最有价值的地方，不是实现了多少具体功能，而是已经把一套后台系统常见的通用能力沉淀为稳定底座。

它的结构可以概括为：

- `common` 负责公共协议与基础组件
- `system` 负责系统级模型基础
- `framework` 负责运行时基础设施
- `file` 负责文件基础设施
- `console` 负责装配与运行验证

如果后续继续沿着这个方向演进，业务团队可以把更多精力放在业务本身，而不是反复搭建协议、缓存、认证、日志、文件、异常处理等通用设施。

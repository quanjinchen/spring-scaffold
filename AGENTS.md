# AGENTS.md

工作约定

读取文件使用 UTF8 编码。
写入文件时注意编码正确，避免写入乱码文件。

代码注释约定

后续在本项目中新增或修改代码时，必须添加必要的中文注释。

注释要求

1. 注释使用中文。
2. 注释应说明业务意图、关键流程、字段映射、边界条件或特殊处理原因。
3. 复杂方法、复杂分支、关键接口、重要数据结构必须补充注释。
4. 不要添加无意义注释，例如“给变量赋值”“调用方法”等显而易见的描述。
5. 如果是联调代码、兼容逻辑、临时兜底逻辑，必须注明原因。
6. 修改已有代码时，如果原逻辑不直观，应一并补充注释，而不是只改功能。

代码分层与注入约定

后续在本项目中新增或修改代码时，默认遵守以下分层和注入规则。

1. 依赖注入默认使用注解方式引入，优先使用 `@Resource`，不使用构造器注入作为默认风格。
2. 各模块中的 `manager` 默认采用 `interface + impl` 结构，包括 `system`、`framework`、`file` 等模块，不要只在部分模块执行。
3. `controller` 依赖 `service` 接口，不直接依赖 `service impl`。
4. `service` 负责业务编排，不直接依赖 `mapper`。
5. `service` 如需访问数据层，必须通过 `manager` 接口调用。
6. `manager` 负责封装对 `mapper` 的访问，`manager impl` 才允许直接依赖 `mapper`。
7. 禁止 `controller -> mapper`、`service -> mapper` 这类跨层直连。
8. 新增查询、保存、删除、关系维护等通用数据访问逻辑时，优先补充到 `manager` 层，而不是直接写在 `service` 中。
9. 只要是 `Manager` 命名和职责的类，就统一按 `interface + impl` 维护，并且默认使用注解方式注入依赖。
10. 工具型类、配置类、纯辅助类不要机械地强拆成 `interface + impl`，但如果已经抽象为 `manager`，就继续遵守 `manager` 的分层约定。

查询实现约定

1. 简单单表查询、条件较少的查询，可以使用 MyBatis-Plus 的 Lambda 写法。
2. 如果查询逻辑较复杂，例如多表关联、复杂聚合、嵌套条件、动态拼装 SQL、结果映射复杂等场景，优先使用 Mapper XML 编写 SQL。
3. 不要为了统一风格，强行用 Lambda 表达式承载复杂连表查询。
4. 复杂查询的 SQL 应放在 Mapper XML 中，便于阅读、维护、调试和后续优化。

统一响应与异常约定

后续在本项目后端中新增或修改接口、业务逻辑时，默认遵守以下响应与异常处理规则。

1. `RespInfo` 的成功响应统一放在 `service/serviceImpl` 中返回，`controller` 不再手写 `RespInfo.success(...)`、`RespInfo.created(...)`。
2. `controller` 只负责参数接收、路由声明、权限注解、日志注解和直接转发 `service` 返回结果，不负责拼装业务成功响应。
3. 业务失败统一通过异常机制处理，不在 `controller` 中手动返回失败 `RespInfo`。
4. 业务异常优先使用 `ResultCode` 抛出，不要优先手写 `new BizException(...)`。
5. `ResultCode` 已实现 `Assert`，后续业务代码优先使用断言式写法，例如：
   `ResultCode.USER_NOT_FOUND.assertNotNull(user);`
   `ResultCode.CAPTCHA_INVALID.assertIsTrue(verified);`
   `ResultCode.INVALID_REQUEST_ID.assertFail("请求头 X-REQUEST-ID 不能为空");`
6. 如果只是根据空值、布尔值、集合状态、字符串状态做业务校验，优先使用 `ResultCode.xxx.assert...(...)`，不要额外写 `if (...) { throw ... }`。
7. 如果确实需要直接构造异常，再使用 `ResultCode.xxx.newException(...)`；只有在 `ResultCode` 无法表达的场景下，才允许直接写 `new BizException(...)`。
8. `GlobalExceptionHandler` 负责统一把异常转换成 `RespInfo`，新增异常处理逻辑时，优先收敛到全局异常处理器，不要在各处重复处理。
9. 新增业务错误码时，优先补充到 `ResultCode`，并提供清晰中文语义，避免在业务代码里散落硬编码错误信息。
10. 如果参考 IAM 项目的实现方式，默认优先对齐以下风格：
    `ResultCode implements Assert`
    `serviceImpl` 返回 `RespInfo`
    `controller` 不拼装成功响应
    业务校验优先使用 `ResultCode.assert...(...)`

# AGENTS.md

后端开发约定

一、基础约定

1. 读取文件统一使用 `UTF-8` 编码。
2. 写入文件时必须保证编码正确，禁止写入乱码文件。
3. 新增或修改代码时，优先保持与现有模块一致的代码风格，不做无意义风格漂移。

二、分层约定

1. `controller` 只负责接口声明、参数接收、权限注解、日志注解、直接转发 `service` 结果。
2. `service` 负责业务编排与业务校验。
3. `service` 不直接依赖 `mapper`，数据访问必须通过 `manager`。
4. `manager` 负责封装数据访问逻辑，`manager impl` 才允许直接依赖 `mapper`。
5. 禁止 `controller -> mapper`、`service -> mapper` 这类跨层直连。
6. `controller` 依赖 `service` 接口，不直接依赖 `service impl`。
7. `manager` 默认采用 `interface + impl` 结构。
8. 工具类、配置类、纯辅助类不要机械拆成 `interface + impl`，但只要已经定义成 `manager`，就继续遵守这一套分层规则。
9. 查询场景尽量避免 `service -> service` 调用，优先由当前 `service` 直接调用对应 `manager` 完成查询。
10. `controller` 一般只调用自己的 `service`，不要在一个 `controller` 中直接调用其他业务域的 `service`。
11. 如果某块接口属于独立业务职责，例如用户角色、角色菜单、组织用户等，应拆到独立 `controller` 中实现，保持业务逻辑边界清晰。
12. `service` 中涉及新增、修改的业务时，如果字段具有唯一性要求，必须先通过 `manager` 查询数据库，校验是否已存在重复数据。不要自己伪造一个实体类出来进行校验
13. 修改场景做唯一字段校验时，必须去数据库查询下，排除当前正在修改的数据本身，避免把自己判重。
14. `serviceImpl` 中唯一值与数据库比对校验、以及其他紧邻主流程的业务校验，默认直接和主流程写在一起，不要为了形式拆成多个私有校验方法。

   示例代码：

   ```java
   @Override
   public RespInfo<Void> updateRole(RoleUpdateReqParam reqParam) {
       // 修改角色时，只校验角色名称这类有唯一语义的字段
       RoleEntity existRole = roleManager.getByName(reqParam.getRoleName());
       if (existRole != null && !Objects.equals(existRole.getId(), reqParam.getId())) {
           ResultCode.ROLE_NAME_EXIST.assertFail();
       }

       RoleEntity roleEntity = roleManager.getById(reqParam.getId());
       ResultCode.DATA_NOT_EXIST.assertNotNull(roleEntity);

       roleEntity.setRoleName(reqParam.getRoleName());
       roleManager.updateById(roleEntity);
       return RespInfo.success();
   }
   ```
   
三、service 方法命名约定

1. `service` 方法命名要直接体现业务语义，不使用含糊命名。
2. 分页查询方法统一使用 `list` 开头，例如 `listUser`。
3. 全量获取方法统一使用 `listAll` 开头，例如 `listAllUser`。
4. 单个对象查询方法名必须写清楚查询依据，例如 `getUserById`、`getUserByUsername`。
5. 新增方法名要写清楚新增的对象，例如 `createUser`。
6. 修改方法名要写清楚修改的对象，例如 `updateUser`。
7. 删除方法名要写清楚删除的对象，例如 `deleteUser`。
8. `controller` 方法命名与 `service` 方法命名规则保持一致，优先使用同一套业务语义命名，例如 `listUser`、`listAllUser`、`getUserById`、`createUser`、`updateUser`、`deleteUser`。


四、接口对象命名约定

1. 所有接口输出到前台的对象，统一使用 `DTO` 结尾。
2. 所有前台输入到后台的对象，统一使用 `ReqParam` 结尾。
3. 所有用于数据库查询、传递查询条件的对象，统一使用 `Query` 结尾。
4. 输出到前台的对象即使不是实体直接转换，而是多表组装、附加统计字段、树结构、授权信息、汇总信息等组合结果，也继续使用 `DTO` 结尾。
5. 返回前台的对象命名要体现业务语义，例如 `RoleDetailDTO`、`RoleGrantInfoDTO`、`MenuTreeDTO`，不要因为是组装对象就改用 `VO`、`Info`、`Data` 这类不稳定后缀。
6. 禁止混用命名，不要再使用同时承担请求、响应、查询语义的通用对象名。
7. 入参与出参必须分开定义，禁止复用同一个类同时作为请求和响应。
8. `ReqParam` 必须补充 `@Schema` 说明。
9. `ReqParam` 有必填、非空、范围、长度等限制时，必须补充对应校验注解。
10. 如果 `ReqParam` 注解已经完成了必填、非空这类基础参数校验，`service/serviceImpl` 中不要再重复写同样的基础校验；`service` 只保留业务语义校验，例如唯一性校验、状态流转校验、关联关系校验等。
11. 接口如果配置了权限注解，例如 `@SaCheckPermission`，必须同步在 Swagger 的 `@Operation` 备注中标明权限信息，便于联调和排查，推荐格式为 `权限：system:user:query`。

五、注释约定

1. 注释统一使用中文。
2. 注释应说明业务意图、关键流程、字段映射、边界条件、兼容原因或特殊处理原因。
3. 复杂方法、复杂分支、关键接口、重要数据结构必须补充必要注释。
4. 不要添加“给变量赋值”“调用方法”这类无意义注释。
5. 联调逻辑、兼容逻辑、兜底逻辑必须注明原因。
6. 修改已有代码时，如果原逻辑不直观，应一并补充必要注释，而不是只改功能不补说明。

六、注入约定

1. 项目内默认使用注解方式注入依赖。
2. 优先使用 `@Resource`。
3. 不把构造器注入作为本项目默认风格。

七、查询对象与转换约定

1. `manager` 方法如果只有一个简单参数，可以直接传单个参数。
2. `manager` 方法如果存在多个查询参数，必须整合为 `Query` 对象，不要平铺多个参数。
3. `service` 调用 `manager` 查询时，优先将 `ReqParam` 转换为 `Query` 后再下钻。
4. 如果 `ReqParam` 和 `Query` 字段基本一致，优先通过 `Converter` 转换，不要在 `service` 中手写逐个赋值。
5. 领域对象、DTO、Query 之间的转换优先收敛到 `Converter`。
6. `Converter` 只用于明确、稳定、具备分层价值的对象转换。
7. 优先使用 `Converter` 的场景包括：
   `Entity -> DTO`
   `List<Entity> -> List<DTO>`
   `ReqParam -> Query`
8. 不要使用 `Converter` 做 `ReqParam -> Entity` 转换。
9. `ReqParam -> Entity` 默认在 `service` 中按业务语义手动组装，便于同时处理默认值、兼容逻辑、字段裁剪、业务校验等逻辑。
10. 不要为了统一形式，把明显带业务语义的装配逻辑强行塞进 `Converter`。
11. 不要为了分层增加没有业务价值的薄封装方法；如果某个方法只是简单转调一次，优先整合到实际业务方法中。

八、查询实现约定

1. 简单单表查询、条件较少的查询，可以使用 MyBatis-Plus Lambda 写法。
2. 复杂查询如多表关联、动态 SQL、复杂聚合、结果映射复杂等场景，优先使用 Mapper XML。
3. 不要为了统一风格，强行用 Lambda 承载复杂连表查询。
4. 新增查询、保存、删除、关系维护等通用数据访问逻辑时，优先补到 `manager` 层。

九、分页实现约定

1. 分页查询默认优先对齐 IAM 项目的实现方式。
2. 优先使用 `PageHelper.startPage(pageNum, pageSize)` 开启分页。
3. 数据查询优先走普通 `list` 查询，再结合 `PageInfo` 组装 `PageData`。
4. 同一模块内分页风格保持一致，不混用多套分页实现。
5. 如果模块已明确采用 IAM 风格分页，则后续继续使用 `PageHelper + PageInfo + PageData`。

十、响应与异常约定

1. 成功响应统一由 `service/serviceImpl` 返回 `RespInfo`。
2. `controller` 不负责拼装成功响应。
3. 失败场景统一通过异常机制处理，不在 `controller` 中手动返回失败响应。
4. 业务异常优先使用 `ResultCode` 表达。
5. 业务校验优先使用 `ResultCode.xxx.assert...(...)` 断言式写法。
6. 新增业务错误码优先补到 `ResultCode`，并使用清晰中文语义。
7. 全局异常转换统一收敛到 `GlobalExceptionHandler`。

十一、日志约定

1. `OperateLog` 的 `action` 使用中文业务描述。
2. 不使用英文动作名作为默认风格。
3. 日志描述应能直接体现业务行为，例如“创建用户”“删除角色”“重置密码”。

十二、接口请求方式约定

1. 除了“仅根据单个 ID 查询详情”这类接口外，其他接口统一使用 `POST` 请求实现。
2. 单个 ID 查询详情允许使用 `GET` 请求。
3. 路由地址统一使用小写短横线风格，不使用驼峰，不使用下划线，例如 `/create-user`、`/list-user`、`/get-user-by-id`。
4. 路由语义与 `controller/service` 方法语义保持一致，优先采用“动作 + 对象 + 条件”的命名方式。
5. 分页查询接口路由统一写成 `/list-user` 这类形式，全量查询统一写成 `/list-all-user`。
6. 单个对象查询接口路由必须写清楚查询依据，例如 `/get-user-by-id`、`/get-user-by-username`。
7. 新增、修改、删除接口路由统一写成 `/create-user`、`/update-user`、`/delete-user` 这类形式。
8. 使用 `GET` 查询单个 ID 详情时，ID 必须拼接在路径最后，例如 `/get-user-by-id/{id}`。
9. 不要把列表查询、条件查询、删除、重置、分配关系等接口实现为 `GET`。
10. 只要请求中包含复杂条件、查询对象、删除参数、重置参数、关系分配参数，统一使用 `POST + @RequestBody`。

十三、方法拆分约定

1. 业务逻辑优先一次写完整，不要机械拆成很多小方法。
2. 只有在以下场景下，才建议拆分方法：
   同一段逻辑被两个及以上地方复用；
   该段逻辑可以独立解耦，且代码规模明显偏大，通常超过 10 行；
   拆分后能明显提升可读性，而不是增加来回跳转成本。
3. 仅被单处调用、逻辑较短、上下文强依赖当前方法的代码，不要强行提炼成私有方法。
4. 方法拆分的目标是提升可读性和复用性，不是为了形式上把方法拆短。

十四、数据库约定

1. 业务表、关系表默认统一继承 `BaseEntity` 对应的基础字段结构，包括 `id`、`create_by`、`create_time`、`update_by`、`update_time`、`deleted`。
2. 主键字段 `id` 统一使用数据库自增，表结构使用 `bigint not null auto_increment`，实体主键策略与之保持一致。
3. 逻辑删除统一使用字段 `deleted`，约定 `0` 为正常，`1` 为删除。
4. 逻辑删除统一通过 MyBatis-Plus 实现，并在配置文件中配置逻辑删除规则，不在 Java 配置类中单独硬编码。
5. 所有建表 SQL 中的字段都必须补充中文 `comment` 说明，表本身也应补充表级 `comment`。
6. `create_time` 和 `update_time` 统一在 SQL 层维护：
   `create_time` 使用 `datetime not null default current_timestamp`；
   `update_time` 使用 `datetime not null default current_timestamp on update current_timestamp`。
7. `create_by` 和 `update_by` 统一通过 MyBatis-Plus 自动填充实现：
   有登录态时写当前登录用户 ID；
   无登录态时默认写 `0`，不能因为接口未鉴权而报错。
8. 记录相关表必须按实际查询场景补充索引，不要只建主键；索引设计优先结合逻辑删除查询习惯，优先使用 `(业务字段, deleted)` 这类组合索引。
9. 初始化数据库时，如果当前不启用 Flyway，则以 `console/src/main/resources/db/init.sql` 作为最终初始化脚本，结构必须与当前代码约定保持一致。
10. 数据库表命名统一使用 `tb_` 前缀，例如 `tb_user`、`tb_org`、`tb_sys_role_user`。
11. 实体与数据库表的映射默认依赖 MyBatis-Plus 的类名转下划线规则加统一表前缀实现，不再使用 `@TableName` 显式指定表名。
12. 设计实体命名时，要同时考虑去掉 `@TableName` 后的默认映射结果，确保实体名、表前缀配置、最终表名三者语义一致。

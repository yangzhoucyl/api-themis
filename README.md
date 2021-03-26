# Themis
    
##  描述
        Bank-Themis是一个可配置的接口参数校验插件,使用Redis和Mysql完成接口参数动态校验,支持自定义返回消息及热部署校验规则,所有规则cache到java内存中,
    没有任何网络开销,因此不必担心性能损耗。
    
## 上手指南

### 依赖环境
- spring-boot 2.1.9以上
- jdk 1.8
- jedis 2.9.3

### 如何使用
- 引入依赖
  <dependency>
      <groupId>org.themis</groupId>
      <artifactId>themis-verify</artifactId>
      <version>1.0.0-SNAPSHOT</version>
  </dependency>
- 本地bootstrap.yml配置文件根节点新增paramCheck.filter: true开启参数拦截配置。
- 启动类加入注解@MapperScan({"org.themis.check"})  
- 启动项目后会自动在数据库创建check_route_config、及check_parameter_config配置表:
    check_route_config: 储存需要校验的接口及校验类型.
    check_parameter_config: 储存校验接口参数的具体校验规则.

#### 配置说明
- check_route_config.route: 存储需要校验接口的匹配路径(/yk/acc/xw_bank/v1/list_open_account.do)
- check_route_config.name: 校验接口名,可为空.
- check_route_config.status: 启用状态(1启用,0禁用)
- check_route_config.process_type: 接口处理类型非空,默认processType
- check_route_config.type_val: 处理类型具体值 DEFAULT为默认校验，当接口校验规则未根据不同参数类型校验时填入要校验的类型值.
    
##### check_parameter_config:
- route_config_id: 对应route记录主键id.
- param_name: 需要校验的参数对象类型以"."进行分割(
        {"obj":{"student":{"name":"val"}},obj.student.name)
        或([{"obj":["student":{"name":"val"}],[]}],obj.student.name).
- check_rule: 参数校验规则默认为空，可填入正则表达式.
- pattern: 校验类型**REGULAR**为正则校验模式,**DEFAULT***为非空校验.
- message: 校验不通过时返回信息
        
#### 原理

##### 初始化时
    Themis在springboot启动时会根据配置文件配置的filter情况决定是否开启参数拦截校验,
        在开启校验配置时会初始化配置表里的规则并加入本地缓存对象[CheckRuleSingleton.class]
        CheckRuleSingleton.class使用ConcurrentHashMap存储所有规则数据并根据url后缀进行分组,使用url作为规则匹配key,
        由于CheckRuleSingleton使用单例模式创建因此不必担心重复创建对象造成的额外内存开销。

##### Themis如何工作的
- 一旦配置启用了Themis,Themis就会在启动的时候向WebMvcConfigurer注册一个实现HandlerInterceptor的拦截器,拦截器默认添加全路径拦截.
- 在请求进入web服务的时候过滤器会拦截请求中的json数据并存入当前请求的缓存中，由ParameterInterceptor进行处理.
- ParameterInterceptor会在请求进入时拦截请求，判断入参类型然后根据入参类型和本地加载的校验规则进行参数校验.

#### 如何配置及更新校验规则
##### 配置
- 调用接口/nk/config/save_route.do 保存匹配规则,及 /nk/config/save_route.do/save_check_rule.do保存校验规则.
- 数据库插入保存

##### 更新新加入的配置
- 调用接口/nk/config/update_rule_chache.do进行缓存更新

##### 查询规则
- 调用接口/nk/config/list_config_route.do进行已配置接口查询，支持分页
    
    
    

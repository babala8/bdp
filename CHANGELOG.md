## Tag版本记录

### v1.0.0

- 初步完成 addnotes-plan 和 data-insight 整合


### v1.0.1

- 数据库脚本更新

### v1.1.0
微服务改造：
- 拆分模块间的依赖，只能互相依赖Client模块
- 拆分base模块为，channel-center 和 user-center，并修改接口前缀
- 整合Nacos注册中心、Nacos配置中心、SkyWalking链路追踪系统
- 整合ELK日志系统、FileBeats 以 sidecar方式采集服务日志
- 同时支持单体构建和分布式构建

### 2.0.0

模块提取改造：
- ...

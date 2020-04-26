# treasury-brain

金库聚合工程，本工程用于聚合各基础模块和业务模块，以提供完整的服务

请认真阅读并遵循：[金库项目开发指南](http://ubuntu/microservice/reference-doc/development-guide/blob/master/developer-guides/README.md)

## 开发环境说明

1. 请遵循 [分支使用规范](http://ubuntu/zj-public/gitlab-user-doc/blob/master/Gitlab/Use-Branch.md)
2. 项目采用gradle构建，为了能下载私有依赖，需要配置南京公司maven私服
    
    参考 [配置Gradle构建工具](http://ubuntu/zj-public/gitlab-user-doc/blob/master/Gradle/Gradle-Config.md)
    
3. 搭建开发环境

    参考 [快速开始](http://ubuntu/microservice/reference-doc/development-guide/blob/master/startup/README.md)
    
4. 版本提交步骤
    
    参考 [版本提交步骤](http://ubuntu/zj-public/gitlab-user-doc/blob/master/Gitlab/Version-Submit.md)

5. 在线接口文档说明

    1. 启动项目，在浏览器输入接口文档地址**http://localhost:8080/doc.html**
    2. 输入用户名和密码 *admin/123456*
    3. 在*文档管理-个性化设置*中将**启用SwaggerBootstrapUi提供的增强功能**选项打勾，保存后刷新页面
    4. 点击*Authorization*，在*Authorization(apiKey)*的参数值中输入**Basic emhhbmdzYW46MTIzNDU2**，并点击保存
    5. 查看接口并测试


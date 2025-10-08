# Xiaomi BMS Project

## 项目简介
Xiaomi BMS (Battery Management System) 是一个用于管理电池信号和车辆信息的系统。该项目包含多个模块，包括信号处理、规则解析、服务层实现等。

## 项目结构
```
- src/
  - main/
    - java/
      - com/zyw/
        - ConsumerApplication.java
        - MainApplication.java
        - config/
        - controller/
        - entity/
        - exception/
        - mapper/
        - service/
          - handler/
            - processor/
              - mq/
              - parser/
          - impl/
    - resources/
      - application.yml
  - test/
    - java/
```

### 主要模块
- **controller**: 控制器层，处理 HTTP 请求。
- **entity**: 实体类，定义了项目中的数据模型。
- **service**: 服务层，包含业务逻辑。
  - **handler**: 处理器，用于信号分析和请求验证。
  - **processor**: 信号处理器。
    - **mq**: 消息队列相关处理。
    - **parser**: 规则解析器。
- **mapper**: 数据访问层，负责与数据库交互。
- **exception**: 异常处理模块。

## 环境要求
- JDK 11 或更高版本
- Maven 3.6 或更高版本

## 配置
项目的配置文件位于 `src/main/resources/application.yml`，可以根据需要修改。

## 构建与运行
1. 使用 Maven 构建项目：
   ```
   mvn clean install
   ```
2. 运行主类：
   ```
   java -jar target/your-application.jar
   ```

## 测试
测试类位于 `src/test/java` 目录下，可以使用以下命令运行测试：
```bash
mvn test
```

## 贡献
欢迎提交 Issue 或 Pull Request 来贡献代码。

## 许可证
该项目遵循 MIT 许可证。

# 基于Dubbo 2.7.7的Nacos和Zookeeper的集成
## 前言
> 上一次使用Dubbo还是用的Dubbo `2.7.3`版本的，最近有个项目打算用Dubbo来开发，查阅了文档，发现其最新的版本是3.x版本了，但是相关文档貌似还没有整理好，官方显示是在`建设中`,所以我使用了所谓稳定点的版本`2.7.7`，发现`2.7.7`版本已经有了些许的改动了，所以自己踩了一遍坑，做了这个`Demo`来记录下。

## 问题纪要

> 由于版本变更以及之前没有使用过Nacos作为Dubbo的注册中心，以至于集成的过程中遇到了一些问题，所以这里先给说明下遇到的问题

### 注解变更

由于`@service `和Spring中的`@repository`和`@service`太容易混淆啦，许多人在`issues`上提出了该问题，在`2.7.7`版本的时候调整为了` @DubboService` 和 `@DubboReference`

### 无需`@EnableDubbo`

无需再启动类上添加`@EnableDubbo`注解了，在我测试的过程中，发现了只要在代码中使用了`@DubboService`或`@DubboReference`就会自动启用Dubbo。

> 使用Nacos做注册中心的时候，我发现没有引入相关`DubboService`的时候，我的消费服务虽然配置好了duboo相关的参数，都是nacos服务列表没有显示出来，当我使用了`DubboService`的时候，服务列表就出现了

### Nacos 1.4.0服务列表不显示

不知道是否某些地方配置的问题，我使用`Nacos 1.4.0`的时候服务列表总是显示不出来（即使我的`nacos-client`也同步使用`1.4.0`)，换成`1.3.2`或`1.3.1`就可以显示出来了，但是调用是正常的，/(ㄒoㄒ)/~~。

## Demo解读

### 目录解读

```bash
├─common-api # 公共APi的包
├─consume-service-nacos # Nacos作为注册中心的消费者模块
├─consumer-service-zookeeper # zookeeper作为注册中心的消费者模块
├─provider-service-nacos # Nacos作为注册中心的服务提供者模块
└─provider-service-zookeeper # zookeeper作为注册中心的服务提供者模块

```

### 关于不同注册中心

Dobbo如果需要使用不同的注册中心的话不需要修改业务逻辑代码，只需要修改`POM文件`和`配置文件`即可,所以本文将先说明下公共的业务代码，然后再分别提供Nacos和Zookeeper的pom和配置文件。

### common-api

> 这里就定义了一个Model和一个接口，这里需要注意的是如果返回值是Model的话需要是可序列化的一个对象。

```bash
│common-api
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─martain
│  │  │          └─common
│  │  │              └─api
│  │  │                  │  IUserService.java  # 接口
│  │  │                  └─vo
│  │  │                          User.java # model

```

```java
public class User implements Serializable {
    private String id;
    private String userName;
    private Integer age;
	...getter and setter ...
}
```

```java
public interface IUserService {
    /**
     * 通过Id获取用户
     * @param userId 用户ID
     * @return 用户实体
     */
    User getUserById(String userId);
}
```

### 服务提供者

```bash
│  ├─java
│  │  └─com
│  │      └─martain
│  │          └─provide
│  │              │  ProviderServiceXXX.java # 启动类
│  │              └─service
│  │                      UserService.java # 服务实现类
│  │
│  └─resources
│          application.yml # 配置文件
│  pom.xml	# pom 文件

```

#### POM文件

##### Nacos

```xml
    <properties>
        <spring-boot.version>2.3.0.RELEASE</spring-boot.version>
        <dubbo.version>2.7.7</dubbo.version>
        <nacos.version>1.3.2</nacos.version>
    </properties>

    <dependencies>
        <!-- Spring Boot dependencies -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <version>${spring-boot.version}</version>
        </dependency>
		<!-- api -->
        <dependency>
            <groupId>com.martain</groupId>
            <artifactId>common-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!-- Dubbo Spring Boot Starter -->
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>${dubbo.version}</version>
        </dependency>
        <!-- Dubbo Registry Nacos -->
        <dependency>
            <groupId>com.alibaba.nacos</groupId>
            <artifactId>nacos-client</artifactId>
            <version>${nacos.version}</version>
        </dependency>
    </dependencies>
```

##### Zookeeper

```xml
    <properties>
        <spring-boot.version>2.3.0.RELEASE</spring-boot.version>
        <dubbo.version>2.7.7</dubbo.version>
    </properties>

    <dependencies>
        <!-- Spring Boot dependencies -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <version>${spring-boot.version}</version>
        </dependency>
		<!-- Dubbo Spring Boot Starter -->
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>${dubbo.version}</version>
        </dependency>
		<!-- 公共api -->
        <dependency>
            <groupId>com.martain</groupId>
            <artifactId>common-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!-- Zookeeper dependencies -->
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-dependencies-zookeeper</artifactId>
            <version>${dubbo.version}</version>
            <type>pom</type>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

    </dependencies>
```

#### 配置文件

```yaml
spring:
  application:
    name: provider-service
dubbo:
  application:
    name: provider-service
  registry:
    address: zookeeper://127.0.0.1:2181 # 如果是nacos 就填 nacos://127.0.0.1:8848
    timeout: 10000
  protocol:
    name: dubbo
    port: 20880
  scan:
    base-packages: com.martain.provide.service
  metadata-report:
    address: zookeeper://127.0.0.1:2181 # 如果是nacos 就填 nacos://127.0.0.1:8848
```





#### userService

> 接口实现类上需要添加`@DubboService`注解,表名这个类中的接口是需要注册到注册中心去的。而这个注解也有许多可选参数，详情可查看dubbo的源码了解这些参数的作用。
>
> > dubbo官方推荐做法是尽量在provider端多做配置，比如timeout这种配置，应该在服务提供者端配置，而不是在消费者端配置，因为提供者更清楚他提供的方法大致会花费多长时间。（虽然按照dubbo的配置覆盖规则，在consumer端的配置会覆盖provider端的配置）。
>

<details>
<summary>@DubboService</summary>

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface DubboService {
    Class<?> interfaceClass() default void.class;

    String interfaceName() default "";

    String version() default "";

    String group() default "";

    String path() default "";

    boolean export() default true;

    String token() default "";

    boolean deprecated() default false;

    boolean dynamic() default true;

    String accesslog() default "";

    int executes() default 0;

    boolean register() default true;

    int weight() default 0;

    String document() default "";

    int delay() default 0;

    /** @deprecated */
    String local() default "";

    String stub() default "";

    String cluster() default "";

    String proxy() default "";

    int connections() default 0;

    int callbacks() default 1;

    String onconnect() default "";

    String ondisconnect() default "";

    String owner() default "";

    String layer() default "";

    int retries() default 2;

    String loadbalance() default "random";

    boolean async() default false;

    int actives() default 0;

    boolean sent() default false;

    String mock() default "";

    String validation() default "";

    int timeout() default 0;

    String cache() default "";

    String[] filter() default {};

    String[] listener() default {};

    String[] parameters() default {};

    String application() default "";

    String module() default "";

    String provider() default "";

    String[] protocol() default {};

    String monitor() default "";

    String[] registry() default {};

    String tag() default "";

    Method[] methods() default {};
}
```

</details>

```java
package com.martain.provide.service;

import com.martain.common.api.IUserService;
import com.martain.common.api.vo.User;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class UserService implements IUserService {

    public User getUserById(String userId) {
        User user = new User();
        user.setId(userId);
        user.setUserName("小明");
        user.setAge(18);
        return user;
    }
}
```

#### Application 启动类

> 启动类上添加`@EnableAutoConfiguration`注解即可，这个注解也是spring的自动配置的注解。

```java
package com.martain.provide;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableAutoConfiguration
public class ProviderServiceZookeeper {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ProviderServiceZookeeper.class).run(args);
    }
}
```

### 服务消费者

> 服务消费者

```bash
│  pom.xml # POM文件
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─martain
│  │  │          └─consumer
│  │  │              │  ConsumerServiceZookeeper.java # 启动类
│  │  │              └─controller
│  │  │                      UserController.java # web控制器
│  │  │
│  │  └─resources
│  │          application.yml # 配置文件
```

#### POM文件

##### Nacos

```xml
	<properties>
        <spring-boot.version>2.3.3.RELEASE</spring-boot.version>
        <dubbo.version>2.7.7</dubbo.version>
        <nacos.version>1.3.2</nacos.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>${spring-boot.version}</version>
        </dependency>
        <dependency>
            <groupId>com.martain</groupId>
            <artifactId>common-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>${dubbo.version}</version>
        </dependency>

        <!-- Dubbo Registry Nacos -->
        <dependency>
            <groupId>com.alibaba.nacos</groupId>
            <artifactId>nacos-client</artifactId>
            <version>${nacos.version}</version>
        </dependency>
    </dependencies>
```

##### Zookeeper

```xml
   <properties>
        <spring-boot.version>2.3.0.RELEASE</spring-boot.version>
        <dubbo.version>2.7.7</dubbo.version>
    </properties>

    <dependencies>
        <!-- Spring Boot dependencies -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>${spring-boot.version}</version>
        </dependency>

        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>${dubbo.version}</version>
        </dependency>

        <dependency>
            <groupId>com.martain</groupId>
            <artifactId>common-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

        <!-- Zookeeper dependencies -->
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-dependencies-zookeeper</artifactId>
            <version>${dubbo.version}</version>
            <type>pom</type>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

    </dependencies>
```

#### 配置文件

```yaml
dubbo:
  application:
    name: consumer-service
  registry:
    address: nacos://127.0.0.1:8848 # 如果是zookeeper 就填 zookeeper://127.0.0.1:2181
```



#### UserController

> 需要在接口上添加`@DubboReference`注解声名该接口的服务器要是dubbo服务，需要去远程调用。
>

<details>
<summary>@DubboReference</summary>

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface DubboReference {
 Class<?> interfaceClass() default void.class;

 String interfaceName() default "";

 String version() default "";

 String group() default "";

 String url() default "";

 String client() default "";

 boolean generic() default false;

 boolean injvm() default true;

 boolean check() default true;

 boolean init() default false;

 boolean lazy() default false;

 boolean stubevent() default false;

 String reconnect() default "";

 boolean sticky() default false;

 String proxy() default "";

 String stub() default "";

 String cluster() default "";

 int connections() default 0;

 int callbacks() default 0;

 String onconnect() default "";

 String ondisconnect() default "";

 String owner() default "";

 String layer() default "";

 int retries() default 2;

 String loadbalance() default "";

 boolean async() default false;

 int actives() default 0;

 boolean sent() default false;

 String mock() default "";

 String validation() default "";

 int timeout() default 0;

 String cache() default "";

 String[] filter() default {};

 String[] listener() default {};

 String[] parameters() default {};

 String application() default "";

 String module() default "";

 String consumer() default "";

 String monitor() default "";

 String[] registry() default {};

 String protocol() default "";

 String tag() default "";

 String merger() default "";

 Method[] methods() default {};

 String id() default "";
}
```
</details>

```java
package com.martain.consumer.controller;

import com.martain.common.api.IUserService;
import com.martain.common.api.vo.User;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @DubboReference
    IUserService userService;

    @GetMapping("/getUserInfo/{userId}")
    public Object getUserInfo(@PathVariable String userId){
        return userService.getUserById(userId);
    } 
}
```

#### Application 启动类

> 和普通的springboot项目一致即可

```java
package com.martain.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerServiceZookeeper {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerServiceZookeeper.class,args)
    }
}
```

#### 测试

访问 [http://127.0.0.1:8080/user/getUserInfo/123](http://127.0.0.1:8080/user/getUserInfo/123)

## 附件

![image-20201204144935046](https://gitee.com/MartainTao/pic/raw/master/20201204144938.png)
# Lion

<p align="center" >
  <img src="https://github.com/micyo202/lion/raw/master/lion.png" alt="lion" title="lion">
</p>

[![Build Status](https://travis-ci.org/micyo202/lion.svg?branch=master)](https://travis-ci.org/micyo202/lion)
[![Codecov](https://codecov.io/gh/micyo202/lion/branch/master/graph/badge.svg)](https://codecov.io/gh/micyo202/lion)
[![Version](https://img.shields.io/badge/Version-1.0.0-orange.svg)](https://github.com/micyo202/lion)
[![Since](https://img.shields.io/badge/Since-2019-199EC4.svg)](https://github.com/micyo202/lion)
[![Java](https://img.shields.io/badge/Java-1.8-yellow.svg)](https://github.com/micyo202/lion)
[![Scala](https://img.shields.io/badge/Scala-2.11.12-D72B2A.svg)](https://github.com/micyo202/lion)
[![Gradle](https://img.shields.io/badge/Gradle-5.3.1-01BC7E.svg)](https://github.com/micyo202/lion)
[![Spring Boot](https://img.shields.io/badge/SpringBoot-2.1.2.RELEASE-FF69B4.svg)](https://github.com/micyo202/lion)
[![Spring Cloud](https://img.shields.io/badge/SpringCloud-Greenwich.RELEASE-5DBF3D.svg)](https://github.com/micyo202/lion)
[![License MIT](https://img.shields.io/badge/License-MIT-lightgrey.svg)](https://github.com/micyo202/lion/blob/master/LICENSE)
[![GitHub Stars](https://img.shields.io/github/stars/micyo202/lion.svg?style=social&label=Stars)](https://github.com/micyo202/lion)
[![GitHub Forks](https://img.shields.io/github/forks/micyo202/lion.svg?style=social&label=Fork)](https://github.com/micyo202/lion)

本项目是使用Gradle构建，基于SpringBoot 2.1.2.RELEASE、SpringCloud Greenwich.RELEASE体系实现的一套完整微服务架构，支持**Java**、**Scala**混编，支持**Docker**容器化部署，规划将包含**大数据**、**区块链**等相关模块，项目初期孵化中...

利用Spring Boot Admin来监控各个独立Service的运行状态，利用Hystrix Dashboard来查看近实时的接口运行状态和调用频率，利用Zipkin进行查看链路跟踪等。

项目整合了 **spring-boot 2.1.2 + jpa + mybatis**框架

项目初期构建，正在不断完善中，敬请期待...

## 引言

为了帮助初学者快速理解入门微服务，这里简单介绍一下微服务的基本概念及常用组件说明，希望能给初学者带来帮助，如有解释不当的地方还望及时指出，谢谢！（对微服务相关知识已有了解的可直接跳过引言部分）

### 系统架构设计
要知道什么是微服务架构，你得先知道什么系统架构设计。

系统架构设计描述了在应用系统的内部，如何根据业务、技术、组织、灵活性、可扩展性以及可维护性等多种因素，将应用系统划分成不同的部分，并使这些部分彼此之间相互分工、相互协作，从而为用户提供某种特定的价值方式。

### 什么是微服务？
微服务是一个概念、一个项目开发的架构思想。

### 微服务架构
微服务架构是一种架构模式，它提倡将单一应用程序划分成一组小的服务，服务之间互相协调、互相配合，为用户提供最终价值。每个服务运行在其独立的进程中，服务于服务间采用轻量级的通信机制互相沟通（通常是基于 HTTP 的 RESTful API）。每个服务都围绕着具体业务进行构建，并且能够被独立地部署到生产环境、类生产环境等。另外，应尽量避免统一的、集中式的服务管理机制，对具体的一个服务而言，应根据业务上下文，选择合适的语言、工具对其进行构建。

### 微服务架构优势？
复杂度可控、独立部署、轻量级通讯、技术选型灵活、容错、扩展

### SOA与微服务架构区别
SOA实现 | 微服务架构实现
--- | ---
企业级，自顶向下开展实施 | 团队级，自底向上开展实施
服务由多个子系统组成，粒度大 | 一个系统被拆分成多个服务，粒度细
企业服务总线，几种式的服务架构 | 无集中式总线，松散的服务架构
集成方式复制（ESB/WS/SOAP） | 集成方便简单（HTTP/REST/JSON）
单块架构系统，相互依赖，部署复杂 | 服务能独立部署

### Dubbo VS Spring Cloud
核心要素 | Dubbo | Spring Cloud
--- | --- | ---
服务注册中心 | Zookeeper、Redis | Spring Cloud Netflix Eureka、Consul
服务调度方式 | RPC | REST API
服务网关 | 无 | Spring Cloud Netflix Zuul
断路器 | 不完善 | Spring Cloud Netflix Hystrix
分布式配置 | 无 | Spring Cloud Config
分布式追踪系统 | 无 | Spring Cloud Sleuth
消息总线 | 无 | Spring Cloud Bus
数据流 | 无 | Spring Cloud Stream（基于Redis、RabbitMQ、Kafka实现的消息微服务）
批量任务 | 无 | Spring Cloud Task

### 为什么选用Spring Cloud而不是Dubbo？
Dubbo只是实现了服务治理，而Spring Cloud子项目分别覆盖了微服务架构下的众多部件，而服务治理只是其中的一个方面。
Dubbo提供了各种Filter，可以通过扩展Filter来完善。
我们必须采用与一站式时代、泛 SOA 时代不同的技术栈，而 Spring Cloud 就是其中的佼佼者。

从核心要素来看，Spring Cloud 更胜一筹，在开发过程中只要整合Spring Cloud的子项目就可以顺利的完成各种组件的融合，而Dubbo缺需要通过实现各种Filter来做定制，开发成本以及技术难度略高。

### 什么是Spring Cloud？
Spring Cloud 是一系列框架的有序集合。
它利用 Spring Boot 的开发便利性巧妙地简化了分布式系统基础设施的开发，如服务发现注册、配置中心、消息总线、负载均衡、断路器、数据监控等，都可以用 Spring Boot 的开发风格做到一键启动和部署。
Spring 并没有重复制造轮子，它只是将目前各家公司开发的比较成熟、经得起实际考验的服务框架组合起来，通过 Spring Boot 风格进行再封装屏蔽掉了复杂的配置和实现原理，最终给开发者留出了一套简单易懂、易部署和易维护的分布式系统开发工具包。

### 常用组件说明
#### Ribbon
Ribbon主要功能是为 REST 客户端实现负载均衡。

工作时候做4件事：

- 优先选择在同一个 Zone 且负载较少的 Eureka Server；
- 定期从 Eureka 更新并过滤服务实例列表；
- 根据用户指定的策略，在从 Server 取到的服务注册列表中选择一个实例的地址；
- 通过 RestClient 进行服务调用。

#### Hystrix
Hystrix 库, 实现了断路器的模式。“断路器” 本身是一种开关装置，当某个服务单元发生故障之后，通过断路器的故障监控（类似熔断保险丝），向调用方返回一个符合预期的、可处理的备选响应（FallBack），而不是长时间的等待或者抛出调用方无法处理的异常，这样就保证了服务调用方的线程不会被长时间、不必要地占用，从而避免了故障在分布式系统中的蔓延，乃至雪崩。

注解方式：
@EnableHystrix
相当于
@EnableCircuitBreaker

#### Feign
Feign 是一个声明式的 Web Service 客户端，它的目的就是让 Web Service 调用更加简单。它整合了 Ribbon 和 Hystrix，从而让我们不再需要显式地使用这两个组件。Feign 还提供了 HTTP 请求的模板，通过编写简单的接口和插入注解，我们就可以定义好 HTTP 请求的参数、格式、地址等信息。接下来，Feign 会完全代理 HTTP 的请求，我们只需要像调用方法一样调用它就可以完成服务请求。

Feign 具有如下特性：

- 可插拔的注解支持，包括 Feign 注解和 JAX-RS 注解
- 支持可插拔的 HTTP 编码器和解码器
- 支持 Hystrix 和它的 Fallback
- 支持 Ribbon 的负载均衡
- 支持 HTTP 请求和响应的压缩

#### Zuul
Zuul路由是微服务架构的不可或缺的一部分，提供动态路由、监控、弹性、安全等的边缘服务。Zuul 是 Netflix 出品的一个基于 JVM 路由和服务端的负载均衡器。

#### Spring Cloud Config
在分布式系统中，由于服务数量巨多，为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，有分布式配置中心组件spring cloud config ，它支持配置服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。在spring cloud config 组件中，分两个角色，一是config server，二是config client

#### Spring Cloud Bus
Spring Cloud Bus 通过轻量消息代理连接各个分布的节点。这会用在广播状态的变化（例如配置变化）或者其他的消息指令。Spring Bus 的一个核心思想是通过分布式的启动器对 Spring Boot 应用进行扩展，也可以用来建立一个多个应用之间的通信频道。目前唯一实现的方式是用 Amqp 消息代理作为通道。
Spring Cloud Bus 被国内很多都翻译为消息总线，也挺形象的。大家可以将它理解为管理和传播所有分布式项目中的消息既可，其实本质是利用了 MQ 的广播机制在分布式的系统中传播消息，目前常用的有 Kafka 和 RabbitMQ。利用 Bus 的机制可以做很多的事情，其中配置中心客户端刷新就是典型的应用场景之一。

## 一、项目开发环境&工具
- MacOS Mojave / Windows 10
- CentOS 7

- JDK 1.8
- Scala 2.11.12

- MySql 8.0.13
- MongoDB 4.0.4
- Redis 5.0.2
- Elasticsearch 6.5.2
- Solr 7.5.0

- RabbitMQ 3.7.9
- Kafka 2.1.0

- Hadoop 3.1.1
- Hbase 2.1.1
- Hive 3.1.1
- Spark 2.4.0

- Gradle 5.3.1
- IntelliJ IDEA 2018.3 / Eclipse 2018-12

## 二、组件情况
- 服务注册、发现: eureka
- 服务监控：spring boot admin
- 配置中心：spring cloud config -> native / git
- 消息总线：spring cloud bus -> amqp
- 负载均衡：feign / ~~ribbon~~
- 断路器: hystrix
- 路由网关：~~gateway~~ / zuul
- 集群监控：hystrix dashboard -> turbine
- 链路追踪：spring cloud sletuh -> zipkin
- 安全认证：spring security -> oauth2（待实现）
- ORM框架：jpa + mybatis
- 数据源监控：druid
- api文档输出：swagger2
- 分布式锁：redis（待实现）
- 消息队列：rabbitmq
- 分布式事物：3PC+TCC（待实现）

## 三、服务启动顺序
以下服务从上往下按顺序启动（注：带**删除线**的服务为相关测试模块不用启动）

- lion-eureka-server（端口：8101、8102...）
- lion-admin-server（端口：8200）
- lion-config-server（端口：8300，*启动后请等待lion-config-server成功注册到eureka后再启动之后各服务，否则会找不到配置服务*）
- lion-zipkin-server（端口：9411）
- lion-zuul-server（端口：8400）
- ~~lion-gateway-server（端口：8450）~~
- lion-turbine-server（端口：8500）
- ~~lion-bigdata（端口：8801）~~
- ~~lion-blockchain （端口：8802）~~
- lion-demo-provider（端口：8601、8602、8603...）
- lion-demo-consumer（端口：8701、8702、8703...）
- ~~lion-demo-ribbon（端口：8781）~~
- ~~lion-demo-sample（端口：8782）~~
- ~~lion-demo-gray（端口：8783）~~
- ~~lion-demo-scala（端口：8784）~~
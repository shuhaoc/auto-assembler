# 概览

## 核心概念

Auto assembler，自动装载器，用于自动完成domain object与pojo之间或pojo之间的转换。

AutoAssembler主要有两类转换方法，即assemble和disassemble。
assemble是将源对象装载为目标对象，源对象一般是domain object或POJO，目标对象一定是POJO；
相反地，disassemble是指目标对象反装载为源对象。

AutoAssembler对POJO的约束是：
* 具有公开的无参构造方法
* 使用公开的setter/getter访问属性
* 允许使用继承，但是所有fields的setter和getter和该field在同一个类中定义

对于不支持空参构造的非POJO对象，主要是domain object，可以使用ConvertibleBuilder作为装载或反装载的中介类。
ConvertibleBuilder实现类必须符合POJO相同的setter/getter的约束。

## 主要API

Auto assembler提供的API除了AutoAssembler类以外，还包括一系列注解、接口和工具类，注解包括：
* FieldMapping 配置字段映射
* Convertible 配置嵌套转换
* RuntimeType & MappedClass 配置多态类型
* SkippedField 配置字段跳过转换

接口包括：
* ConvertibleEnum 定义可转换枚举，实现枚举与其他类型比如Integer互转
* ConvertibleBuilder 定义转换对象的Builder，用于解决无参构造问题和封装构造复杂性
* ClassifiedConverter 定义字段转换器，或者使用Guava的Converter

工具类包括：
* AutoAssemblers 提供已构造好的单例对象
* AutoAssemblerBuilder 用于自定义AutoAssembler

AutoAssembler对象比较重，使用时建议使用单例，比如AutoAssemblers.getDefault()。


# 功能说明

## 基本转换功能

### 转换器

转换器（ClassifiedConverter）是AutoAssembler用于进行字段类型转换的“元器件”。
默认的AutoAssembler（使用AutoAssemblers.getDefault()获取的单例）拥有一组内建的转换器，支持以下类型的互相转换：


|类型1      |类型2      |备注|
|----------|-----------|---|
|String|Integer| |
|String|Long| |
|String|Float| |
|String|Double| |
|String|Boolean| |
|String|BigDecimal| |
|String|Date| |
|String|LocalDateTime||
|String|LocalDate| |
|String|LocalTime| |
|Date|LocalDateTime|`yyyy-MM-dd HH:mm:ss`|
|Date|LocalDate|`yyyy-MM-dd`|
|Date|LocalTime|`HH:mm:ss`|
|Date|java.sql.Date| |
|Date|java.sql.Time| |
|Date|java.sql.Timestamp| |
|String|Enum| |
|ConvertibleEnum<T>|T| |
|int|Integer| |
|long|Long| |
|float|Float| |
|double|Double| |
|BigDecimal|Integer| |
|BigDecimal|Long| |
|BigDecimal|Float| |
|BigDecimal|Double| |


AutoAssembler内部持有一个转换器表，在转换字段时使用“instanceof”规则查找合适转换器，其中越是后面注册的转换器优先级越高。
比如类型A和B已经注册有转换器，那么A的实例，可以是子类的实例，就可以与B的实例互相转换。

除了内建的转换器以外，还可以在构造时使用AutoAssemblerBuilder注册ClassifiedConverter或`com.google.common.base.Converter`作为转换器。
另外，针对特定的字段，还可以使用`@FieldMapping`注解的`customConverterClass`属性注册自定义转换器。


### 逐字段转换



## 字段映射

## 多态转换

## 转换Builder


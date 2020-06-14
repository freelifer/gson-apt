# gson-apt

> gson-apt使用android编译时生成代码(APT)自动生成Gson TypeAdatper代码，提供高性能解析json
> 两种模式，第一种极速模式，所有类都递归生成，但是生成代码比较多

## HOW TO DO?

do that...

## WHAT

### JSON值类型对应java类型

|ID|JSON value值类型|java类型|
|:-:|:-:|:-:|
|1|数值(number)|int、long、float、double|
|2|字符串（string）|String|
|3|true、false|boolean|
|4|对象(object)|Object|
|5|数组(array)|Object[]、List\<Object\>|
|6|null|各个类型的默认值|


## TODO-LIST

- [ ] 多种方案比较 JSONObject、Gson、Gson TypeAdatper比较
- [x] 基础解析功能 ----------------------- (2017/12/22)
- [ ] 支持java float类型
- [ ] 支持java数组类型，int[]、long[]、float[]、double[]、String[]
- [ ] 支持List类型，List\<String\>、List\<Object\>
- [ ] 支持Date日期时间格式2017/12/22 11:22:59
- [ ] 支持debug、release模式(release版本不会触发解析崩溃)
- [ ] 支持日志打印json格式
- [ ] 支持日志打印json格式

## 测试

#### JSONObject性能数据
#### Gson性能数据
#### Gson TypeAdatper性能数据


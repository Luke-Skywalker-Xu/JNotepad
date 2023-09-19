# 解决jlink error指南

## 现象

jlink时，如果出现如下错误，参考本文档

```
"automatic module cannot be used with jlink"
```

## 解决方法：

1. 为jar生成module-info.class

```shell
jdeps --ignore-missing-deps --module-path <jar_dir_path> --add-modules <module_name --generate-module-info <out_dir_path> <jar_path>
javac --patch-module <module_name>=<jar_path> <module-info.java>
jar uf <jar_path> -C <module_name> <module-info.class>
```

以本次icu4j为例，先将依赖的jar包copy到libs目录，然后执行：

```shell
jdeps --ignore-missing-deps --module-path libs --add-modules com.ibm.icu --generate-module-info libs/tmpOut libs/icu4j-73.2.jar
javac --patch-module com.ibm.icu=libs/icu4j-73.2.jar libs/tmpOut/com.ibm.icu/module-info.java
jar uf libs/icu4j-73.2.jar -C libs/tmpOut/com.ibm.icu module-info.class
```

2. pom中添加依赖

```xml

<dependency>
    <groupId>com.ibm.icu</groupId>
    <artifactId>icu4j</artifactId>
    <version>73.2</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/libs/icu4j-73.2.jar</systemPath>
</dependency>
```

## Reference

1. [java_jlink_automatic_module_cannot_be_used_with_jlink](https://tacosteemers.com/articles/java_jlink_automatic_module_cannot_be_used_with_jlink.html)
# 打包
## 准备
1. 下载maven 3.8.8， [Download](https://dlcdn.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.zip)
2. 下载GraalVM。在[GraalVM下载页](https://www.graalvm.org/downloads/#)选择Java17，操作系统后，点击Download下载。

# Windows下基于GraalVM的打包
## 配置maven和GraalVM
1. 将下载的压缩包解压到目录，例如：d:\tools\maven,D:\tools\graalvm-17\graalvm-jdk-17.0.8+9.1
2. 配置环境变量
```
GRAALVM_HOME=D:\tools\graalvm-17\graalvm-jdk-17.0.8+9.1
M2_HOME=d:\tools\maven
```
![env.png](images%2Fenv.png)
3. 配置PATH环境变量
![path.png](images%2Fpath.png)
4. 安装visual studio build tools
安装说明：https://www.graalvm.org/latest/docs/getting-started/windows/
a) 下载安装程序：https://visualstudio.microsoft.com/thank-you-downloading-visual-studio/?sku=BuildTools&rel=16
b) 参考网页中的安装步骤
5. 验证安装成功
使用下面命令启动编译环境
```
cmd.exe /k F:\vs\ide\VC\Auxiliary\Build\vcvars64.bat
```
需要把F:\vs\ide替换为本地的visual stuido安装路径。
6. 进入项目tool目录，执行build.bat进行打包，生成可执行文件。
```
build.bat
```
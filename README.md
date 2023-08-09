# JNotepad

![](https://img.shields.io/badge/Windows-Passing-49%2C198%2C84.svg?style=falt&logo=Windows)
![](https://img.shields.io/badge/Ubuntu-Passing-49%2C198%2C84.svg?style=falt&logo=Ubuntu)
![](https://img.shields.io/badge/MacOS-Passing-49%2C198%2C84.svg?style=falt&logo=Apple)


JNotepad 是一个使用 JavaFX 构建的简单文本编辑器，允许用户创建、打开、编辑和保存文本文件。它支持多个标签，每个标签包含一个文本编辑区域。该编辑器提供基本功能，如创建新文件、打开现有文件、保存文件和使用不同名称保存文件。

## 功能介绍

- 创建空白文本区域的新文件。
- 打开现有文本文件进行编辑。
- 实时更新和自动保存文件。
- 使用“另存为”功能将文件另存为不同的名称。
- 在状态栏中显示行数、列数和字符计数信息。


## 安装教程

1.  Windows 平台，可以直接使用我编译好的可执行程序或自己编译

[gitee-download]: https://gitee.com/jcnc-org/JNotepad/releases
[java-download]: https://www.oracle.com/cn/java/technologies/downloads/
[qq-url]: http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=zOfwWb1lcle68cbEdJCjSIp3Itx0nEC0&authKey=bOsZFT9OVYZpZQbS6IYO4onBQoeBorF5nanMEi1G%2FgPbzmUkOweXBo9qB0G34R5K&noverify=0&group_code=386279455



- [下载][gitee-download]

2. Linux/MacOS 平台，查看入门指南


## 入门指南

要使用 JNotepad，请按照以下步骤进行:

1. 下载并安装 Java（如果尚未安装）。
- [下载][gitee-download]

2. 克隆或下载 JNotepad 项目。
<pre><code>git clone https://gitee.com/jcnc-org/JNotepad.git</code></pre>
3. 在您偏好的 Java IDE 中打开项目。

## 使用方法

1. 运行 `JNotepad` 类以启动应用程序。
2. 主窗口将显示菜单栏、标签区域和状态栏。
3. 使用菜单栏执行各种操作：
  - `文件 > 新建`：创建一个带有空白文本区域的新标签。
  - `文件 > 打开`：打开现有文本文件进行编辑。
  - `文件 > 保存`：将当前活动标签的内容保存到关联文件中。
  - `文件 > 另存为`：将当前活动标签的内容保存为新文件。
4. 在每个标签的文本区域中编辑内容。
5. 状态栏将显示有关光标位置和文本统计信息的信息。

## 依赖项

    <dependencies>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>17.0.1</version>
        </dependency>
    <dependencies>
 ## 软件运行截图
- Windows 平台
  ![Windows](screenshot/windows-1.png)
- MacOS 平台   
  ![MacOS](screenshot/Mac0S-1.png)

### 参与贡献

1. Fork 本仓库
1. 加入JCNC社区
1. [加入QQ群:386279455][qq-url]
1. 新建分支
1. 提交代码
1. 新建 Pull Request

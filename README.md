# JNotepad

![](https://img.shields.io/badge/Windows-Passing-49%2C198%2C84.svg?style=falt&logo=Windows)
![](https://img.shields.io/badge/Ubuntu-Passing-49%2C198%2C84.svg?style=falt&logo=Ubuntu)
![](https://img.shields.io/badge/MacOS-Passing-49%2C198%2C84.svg?style=falt&logo=Apple)


JNotepad(Java Notepad)是一款简约而强大的跨平台文本编辑器，旨在提供用户友好的界面和丰富的功能。无论你是在Linux、Windows还是macOS系统上使用，JNotepad都能满足你对文本编辑和查看的需求。 JNotepad使用Java语言编写，并基于JavaFX框架开发，具有良好的可扩展性和稳定性。
## 功能介绍

- 文本编辑和查看：JNotepad提供了完善的文本编辑和查看功能，使你能够轻松创建、编辑和浏览各种类型的文本文件。

- 跨平台支持：不论你使用哪种操作系统，JNotepad都能够无缝地适应，并提供一致的用户体验。

- 轻量级设计：JNotepad采用简约而现代的设计风格，界面清晰简洁，使得使用起来非常直观和便捷。

- 基于Java：JNotepad使用Java语言编写，并基于JavaFX框架开发，具有良好的可扩展性和稳定性。


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

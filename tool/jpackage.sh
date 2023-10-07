#!/bin/bash
#请在jlink之后通过文件管理器打开tool双击这个脚本，而不是直接使用Idea执行
cd ..
jpackage \
  --name JNotepad \
  --type app-image \
  -m org.jcnc.jnotepad/org.jcnc.jnotepad.JnotepadApp \
  --runtime-image ./target/JNotepad/ \
  --icon src/main/resources/img/icon.ico \
  --app-version 1.1.13 \
  --vendor "JCNC"
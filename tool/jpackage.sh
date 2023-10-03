#!/bin/bash

cd ..
jpackage \
  --name JNotepad \
  --type app-image \
  -m org.jcnc.jnotepad/org.jcnc.jnotepad.LunchApp \
  --runtime-image ./target/JNotepad/ \
  --icon src/main/resources/img/icon.ico \
  --app-version 1.1.13 \
  --vendor "JCNC"

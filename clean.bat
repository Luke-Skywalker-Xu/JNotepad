@echo off
set "folderPath1=D:\IDEAProjects\JCNCProjects\JNotepad\JNotepad"
set "folderPath2=D:\IDEAProjects\JCNCProjects\JNotepad\target"

if exist "%folderPath1%" (
    echo Deleting folder 1: %folderPath1%
    rmdir /s /q "%folderPath1%"
    echo Folder 1 deleted successfully.
) else (
    echo Folder 1 does not exist: %folderPath1%
)

if exist "%folderPath2%" (
    echo Deleting folder 2: %folderPath2%
    rmdir /s /q "%folderPath2%"
    echo Folder 2 deleted successfully.
) else (
    echo Folder 2 does not exist: %folderPath2%
)

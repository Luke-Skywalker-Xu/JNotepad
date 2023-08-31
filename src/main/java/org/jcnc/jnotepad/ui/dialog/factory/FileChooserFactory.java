package org.jcnc.jnotepad.ui.dialog.factory;

import javafx.stage.FileChooser;

import java.io.File;

/**
 * 文件选择对话框工厂
 *
 * @author gewuyou
 */
public interface FileChooserFactory {
    /**
     * 创建原始文件选择对话框
     *
     * @return javafx.stage.FileChooser
     * @since 2023/8/31 20:29
     */
    FileChooser createFileChooser();

    /**
     * 创建详细的文件选择对话框
     *
     * @param title           对话框名字
     * @param filename        选中的文件名
     * @param directory       初始目录
     * @param extensionFilter 文件类型数组
     * @return javafx.stage.FileChooser
     * @apiNote
     * @since 2023/8/31 20:35
     */
    FileChooser createFileChooser(String title, String filename, File directory, FileChooser.ExtensionFilter extensionFilter);
}

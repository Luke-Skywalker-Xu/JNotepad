package org.jcnc.jnotepad.ui.dialog.factory.impl;

import javafx.stage.FileChooser;
import org.jcnc.jnotepad.ui.dialog.factory.FileChooserFactory;

import java.io.File;

/**
 * 文本文件选择对话框
 *
 * @author gewuyou
 * @see FileChooserFactory
 */
public class TextFileChooserFactory implements FileChooserFactory {

    private TextFileChooserFactory() {

    }

    private static final TextFileChooserFactory INSTANCE = new TextFileChooserFactory();

    public static TextFileChooserFactory getInstance() {
        return INSTANCE;
    }

    /**
     * 创建原始文件选择对话框
     *
     * @return javafx.stage.FileChooser
     * @since 2023/8/31 20:29
     */
    @Override
    public FileChooser createFileChooser() {
        return new FileChooser();
    }

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
    @Override
    public FileChooser createFileChooser(String title, String filename, File directory, FileChooser.ExtensionFilter extensionFilter) {
        FileChooser fileChooser = createFileChooser();
        // 设置窗口名称
        fileChooser.setTitle(title);
        if (filename != null) {
            // 设置原始文件名称
            fileChooser.setInitialFileName(filename);
        }
        if (directory != null) {
            // 设置打开的目录
            fileChooser.setInitialDirectory(directory);
        }
        // 设置文件类型
        fileChooser.getExtensionFilters().add(extensionFilter);
        return fileChooser;
    }
}

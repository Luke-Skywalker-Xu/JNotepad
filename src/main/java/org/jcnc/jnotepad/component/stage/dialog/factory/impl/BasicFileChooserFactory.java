package org.jcnc.jnotepad.component.stage.dialog.factory.impl;

import javafx.stage.FileChooser;
import org.jcnc.jnotepad.component.stage.dialog.factory.FileChooserFactory;

import java.io.File;
import java.util.List;

/**
 * 基本文件选择对话框
 *
 * @author gewuyou
 * @see FileChooserFactory
 */
public class BasicFileChooserFactory implements FileChooserFactory {

    private static final BasicFileChooserFactory INSTANCE = new BasicFileChooserFactory();

    private BasicFileChooserFactory() {

    }

    public static BasicFileChooserFactory getInstance() {
        return INSTANCE;
    }

    /**
     * 创建原始文件选择对话框
     *
     * @return javafx.stage.FileChooser
     */
    @Override
    public FileChooser createFileChooser() {
        return new FileChooser();
    }

    /**
     * 创建详细的文件选择对话框
     *
     * @param title            对话框名字
     * @param filename         选中的文件名
     * @param directory        初始目录
     * @param extensionFilters 文件类型过滤器
     * @return javafx.stage.FileChooser
     * @apiNote
     */
    @Override
    public FileChooser createFileChooser(String title, String filename, File directory, FileChooser.ExtensionFilter... extensionFilters) {
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
        for (FileChooser.ExtensionFilter extensionFilter : extensionFilters) {
            // 设置文件类型
            fileChooser.getExtensionFilters().add(extensionFilter);
        }
        return fileChooser;
    }

    /**
     * 创建详细的文件选择对话框
     *
     * @param title            对话框名字
     * @param filename         选中的文件名
     * @param directory        初始目录
     * @param extensionFilters 文件类型过滤器集合
     * @return javafx.stage.FileChooser
     * @apiNote
     */
    public FileChooser createFileChooser(String title, String filename, File directory, List<FileChooser.ExtensionFilter> extensionFilters) {
        return createFileChooser(title, filename, directory, extensionFilters.toArray(new FileChooser.ExtensionFilter[0]));
    }
}

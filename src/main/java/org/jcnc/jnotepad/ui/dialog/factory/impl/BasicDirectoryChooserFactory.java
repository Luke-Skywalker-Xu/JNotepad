package org.jcnc.jnotepad.ui.dialog.factory.impl;

import javafx.stage.DirectoryChooser;
import org.jcnc.jnotepad.ui.dialog.factory.DirectoryChooserFactory;

import java.io.File;

/**
 * @author : cccqyu@outlook.com
 * @createTime 2023/10/2  1:54
 * @description 基本文件夹选择对话框
 */
public class BasicDirectoryChooserFactory implements DirectoryChooserFactory {

    private static final BasicDirectoryChooserFactory INSTANCE = new BasicDirectoryChooserFactory();

    private BasicDirectoryChooserFactory() {

    }

    public static BasicDirectoryChooserFactory getInstance() {
        return INSTANCE;
    }

    /**
     * 创建原始文件选择对话框。
     *
     * @return javafx.stage.DirectoryChooser 原始文件夹选择对话框对象
     */
    @Override
    public DirectoryChooser createDirectoryChooser() {
        return new DirectoryChooser();
    }

    /**
     * 创建详细的文件选择对话框。
     *
     * @param title            对话框标题
     * @param directory        初始目录
     * @return javafx.stage.FileChooser 详细文件选择对话框对象
     */
    @Override
    public DirectoryChooser createDirectoryChooser(String title, File directory) {

        DirectoryChooser  directoryChooser= createDirectoryChooser();
        // 设置窗口名称
        directoryChooser.setTitle(title);

        if (directory != null) {
            // 设置打开的目录
            directoryChooser.setInitialDirectory(directory);
        }

        return directoryChooser;
    }
}

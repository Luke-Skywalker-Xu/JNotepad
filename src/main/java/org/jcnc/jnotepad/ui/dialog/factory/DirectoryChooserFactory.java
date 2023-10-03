package org.jcnc.jnotepad.ui.dialog.factory;

import javafx.stage.DirectoryChooser;

import java.io.File;


/**
 * 文件夹选择对话框工厂
 *
 * <p>文件夹选择对话框工厂,该工厂接口用于创建文件夹选择对话框，包括原始和详细两种类型。</p>
 *
 * @author cccqyu
 */
public interface DirectoryChooserFactory {
    /**
     * 创建原始文件选择对话框。
     *
     * @return javafx.stage.DirectoryChooser 原始文件夹选择对话框对象
     */
    DirectoryChooser createDirectoryChooser();

    /**
     * 创建详细的文件选择对话框。
     *
     * @param title            对话框标题
     * @param directory        初始目录
     * @return javafx.stage.FileChooser 详细文件选择对话框对象
     */
    DirectoryChooser createDirectoryChooser(String title, File directory);
}

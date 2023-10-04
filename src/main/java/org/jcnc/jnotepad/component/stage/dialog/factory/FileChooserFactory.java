package org.jcnc.jnotepad.component.stage.dialog.factory;

import javafx.stage.FileChooser;

import java.io.File;

/**
 * 文件选择对话框工厂
 *
 * <p>该工厂接口用于创建文件选择对话框，包括原始和详细两种类型。</p>
 *
 * @author gewuyou
 */
public interface FileChooserFactory {
    /**
     * 创建原始文件选择对话框。
     *
     * @return javafx.stage.FileChooser 原始文件选择对话框对象
     */
    FileChooser createFileChooser();

    /**
     * 创建详细的文件选择对话框。
     *
     * @param title            对话框标题
     * @param filename         默认选中的文件名
     * @param directory        初始目录
     * @param extensionFilters 文件类型过滤器
     * @return javafx.stage.FileChooser 详细文件选择对话框对象
     * @apiNote 该方法用于创建一个带有标题、默认文件名、初始目录和文件类型过滤器的详细文件选择对话框。
     */
    FileChooser createFileChooser(String title, String filename, File directory, FileChooser.ExtensionFilter... extensionFilters);
}

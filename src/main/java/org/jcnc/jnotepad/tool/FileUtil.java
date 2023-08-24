package org.jcnc.jnotepad.tool;

import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import org.jcnc.jnotepad.ui.LineNumberTextArea;
import org.jcnc.jnotepad.ui.tab.JNotepadTabPane;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author 一个大转盘
 */
public class FileUtil {
    private FileUtil() {
    }

    /**
     * 把一个文件中的内容读取成一个String字符串<br>
     * 注意：该方法不支持多线程操作
     *
     * @param jsonFile json文件
     * @return String
     */
    public static String getJsonStr(File jsonFile) {
        String jsonStr;
        try (
                Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8)
        ) {

            int ch;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            LogUtil.getLogger(FileUtil.class).error("读取配置失败!", e);
            return null;
        }
    }

    /**
     * 保存页面方法
     *
     * @param currentClass 调用该方法的类
     * @apiNote 将当前选中的标签页进行弹出窗口式的保存
     * @see LogUtil
     */
    public static void saveTab(Class<?> currentClass) {
        Tab selectedTab = JNotepadTabPane.getInstance().getSelected();
        if (selectedTab != null) {
            // 创建一个文件窗口
            FileChooser fileChooser = new FileChooser();
            // 设置保存文件名称
            fileChooser.setInitialFileName(selectedTab.getText());
            // 设置保存文件类型
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("文本文档", "*.txt"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                try (
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file))
                ) {
                    // 获取当前Tab页的文本编辑区
                    LineNumberTextArea textArea = (LineNumberTextArea) selectedTab.getContent();
                    // 将保存后的文件设置为已关联
                    textArea.setRelevance(true);
                    String text = textArea.getMainTextArea().getText();
                    // 写入文件内容
                    writer.write(text);
                    writer.flush();
                    // 更新Tab页标签上的文件名
                    selectedTab.setText(file.getName());
                    // 将文件对象保存到Tab页的UserData中
                    selectedTab.setUserData(file);
                } catch (IOException ignored) {
                    LogUtil.getLogger(currentClass).info("已忽视IO异常!");
                }
            }
        }
    }

}

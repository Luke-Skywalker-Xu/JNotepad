package org.jcnc.jnotepad.tool;

import javafx.stage.FileChooser;
import org.jcnc.jnotepad.ui.tab.JNotepadTab;
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
            StringBuffer sb = new StringBuffer();
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
        JNotepadTab selectedTab = JNotepadTabPane.getInstance().getSelected();
        if (selectedTab != null) {
            // 创建一个文件窗口
            FileChooser fileChooser = new FileChooser();
            // 设置保存文件名称
            fileChooser.setInitialFileName(selectedTab.getText());
            // 设置保存文件类型
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("文本文档", "*.txt"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                LogUtil.getLogger(currentClass).info("正在保存文件:{}", file.getName());
                selectedTab.save();
                // 将保存后的文件设置为已关联
                selectedTab.getLineNumberTextArea().setRelevance(true);
                // 更新Tab页标签上的文件名
                selectedTab.setText(file.getName());
                // 将文件对象保存到Tab页的UserData中
                selectedTab.setUserData(file);
            }
        }
    }

}

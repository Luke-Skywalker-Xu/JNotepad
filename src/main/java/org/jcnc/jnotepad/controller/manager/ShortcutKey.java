package org.jcnc.jnotepad.controller.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import org.jcnc.jnotepad.Interface.ShortcutKeyInterface;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.ui.menu.JNotepadMenuBar;
import org.slf4j.Logger;

import java.io.*;
import java.util.Map;
import java.util.Objects;

import static org.jcnc.jnotepad.constants.PathConstants.SHORTCUT_KEY_CONFIGURATION_FILE_PATH;

/**
 * @author 一个大转盘<br>
 */
public class ShortcutKey implements ShortcutKeyInterface {
    Logger logger = LogUtil.getLogger(this.getClass());

    /**
     * 按配置创建快捷键
     *
     * @apiNote 此方法通过配置json文件初始化快捷键
     * @since 2023/8/23 21:56
     */

    @Override
    public void createShortcutKeyByConfig() {
        String jsonData = getJsonPathDataBasedOnJson();
        // 转json对象
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Map<String, String> shortcutKeyConfig = gson.fromJson(jsonData, new TypeToken<Map<String, String>>() {
        }.getType());
        for (Map.Entry<String, String> stringObjectEntry : shortcutKeyConfig.entrySet()) {
            // 保证json的key必须和变量名一致
            MenuItem menuItem = JNotepadMenuBar.getMenuBar().getItemMap().get(stringObjectEntry.getKey());
            String shortKeyValue = stringObjectEntry.getValue();
            if ("".equals(shortKeyValue) || Objects.isNull(menuItem)) {
                continue;
            }
            logger.info("快捷键对象:{}->已分配快捷键:{}", menuItem.getText(), shortKeyValue);
            // 动态添加快捷键
            menuItem.setAccelerator(KeyCombination.keyCombination(shortKeyValue));
        }
    }

    /**
     * 根据json路径返回解析的Json数据
     *
     * @return java.lang.String Json数据
     */
    private String getJsonPathDataBasedOnJson() {
        String rootPath = System.getProperty("user.dir");
        // 构建JSON文件路径
        String jsonFilePath = rootPath + SHORTCUT_KEY_CONFIGURATION_FILE_PATH;
        StringBuilder jsonData = new StringBuilder();
        InputStream inputStream = getClass().getResourceAsStream(SHORTCUT_KEY_CONFIGURATION_FILE_PATH);
        File file = new File(jsonFilePath);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonData.append(line);
                }
            } catch (IOException e) {
                logger.error("读取配置失败!", e);
            }
        } else {
            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonData.append(line);
                    }
                } catch (IOException e) {
                    logger.error("读取配置失败!", e);
                }
            }
        }
        return jsonData.toString();
    }
}

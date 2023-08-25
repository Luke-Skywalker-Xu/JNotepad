package org.jcnc.jnotepad.app.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import org.jcnc.jnotepad.app.entity.ShortcutKey;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.tool.PopUpUtil;
import org.jcnc.jnotepad.ui.menu.JNotepadMenuBar;
import org.slf4j.Logger;

import java.io.*;
import java.util.*;

import static org.jcnc.jnotepad.constants.AppConstants.CONFIG_NAME;
import static org.jcnc.jnotepad.constants.AppConstants.CONFIG_SHORTCUT_KEY_NAME;
import static org.jcnc.jnotepad.constants.TextConstants.JNOTEPAD_CONFIG;

/**
 * 加载应用配置类
 * <br/>空出了加载文件的具体实现
 *
 * @author gewuyou
 */
public abstract class LoadJnotepadConfig {
    Logger logger = LogUtil.getLogger(this.getClass());


    public final void load() {
        // 判断是否存在这个配置文件
        try (InputStream inputStream = new FileInputStream(CONFIG_NAME)) {
            logger.info("正在加载配置文件...");
            // 存在则加载
            loadConfig(inputStream);
        } catch (IOException e) {
            logger.info("未检测到配置文件!");
            // 不存在则创建
            createConfig();
            logger.info("已创建默认配置文件!");
        }
    }

    /**
     * 解析配置文件
     *
     * @param inputStream 输入流
     * @return java.util.List<java.util.LinkedHashMap < java.lang.String, java.lang.String>>
     * @since 2023/8/25 15:18
     */
    protected List<LinkedHashMap<String, String>> parseConfig(InputStream inputStream) {
        StringBuffer jsonData = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }
        } catch (IOException e) {
            PopUpUtil.errorAlert("错误", "读写错误", "配置文件读写错误!");
        }
        // 转json对象
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<Object>> mainConfig = null;
        try {
            mainConfig = mapper.readValue(jsonData.toString(), new TypeReference<HashMap<String, List<Object>>>() {
            });
        } catch (JsonProcessingException e) {
            PopUpUtil.errorAlert("错误", "解析错误", "配置文件解析错误!");
            logger.error("配置文件解析错误!", e);
        }
        if (mainConfig == null) {
            logger.error("未获取到主要配置文件!");
            return Collections.emptyList();
        }
        return mainConfig.get(CONFIG_SHORTCUT_KEY_NAME)
                .stream()
                .map(e -> {
                    if (e instanceof LinkedHashMap) {
                        return (LinkedHashMap<String, String>) e;
                    } else {
                        throw new IllegalArgumentException("Invalid element type");
                    }
                })
                .toList();
    }

    private void createConfig() {
        List<ShortcutKey> shortcutKeyList = getShortcutKeys();
        JnotepadConfig.getInstance().setShortcutKeyList(shortcutKeyList);
        for (ShortcutKey shortcutKey : shortcutKeyList) {
            // 保证json的key必须和变量名一致
            MenuItem menuItem = JNotepadMenuBar.getMenuBar().getItemMap().get(shortcutKey.getButtonName());
            String shortKeyValue = shortcutKey.getShortcutKeyValue();
            if ("".equals(shortKeyValue) || Objects.isNull(menuItem)) {
                continue;
            }
            // 动态添加快捷键
            menuItem.setAccelerator(KeyCombination.keyCombination(shortKeyValue));
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_NAME))) {
            writer.write(JNOTEPAD_CONFIG);
        } catch (IOException e) {
            PopUpUtil.errorAlert("错误", "读写错误", "配置文件读写错误!");
        }
    }

    /**
     * 获取快捷键集合
     *
     * @return java.util.List<org.jcnc.jnotepad.app.entity.ShortcutKey> 快捷键集合
     * @since 2023/8/24 14:19
     */
    private static List<ShortcutKey> getShortcutKeys() {
        List<ShortcutKey> shortcutKeyList = new ArrayList<>();
        // 打开文件
        ShortcutKey shortcutKeyOfOpen = new ShortcutKey("openItem", "ctrl+o");

        // 新建
        ShortcutKey shortcutKeyOfNew = new ShortcutKey("newItem", "ctrl+n");

        // 保存
        ShortcutKey shortcutKeyOfSave = new ShortcutKey("saveItem", "ctrl+s");

        // 保存作为
        ShortcutKey shortcutKeyOfSaveAs = new ShortcutKey("saveAsItem", "ctrl+shift+s");

        // 打开配置文件
        ShortcutKey shortcutKeyOfOpenConfig = new ShortcutKey("openConfigItem", "alt+s");

        shortcutKeyList.add(shortcutKeyOfOpen);
        shortcutKeyList.add(shortcutKeyOfNew);
        shortcutKeyList.add(shortcutKeyOfSave);
        shortcutKeyList.add(shortcutKeyOfSaveAs);
        shortcutKeyList.add(shortcutKeyOfOpenConfig);
        return shortcutKeyList;
    }

    /**
     * 加载配置文件
     *
     * @param inputStream 配置文件的输入流
     */
    protected abstract void loadConfig(InputStream inputStream);
}

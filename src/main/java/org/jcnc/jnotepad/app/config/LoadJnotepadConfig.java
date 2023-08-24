package org.jcnc.jnotepad.app.config;

import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import org.jcnc.jnotepad.app.entity.ShortcutKey;
import org.jcnc.jnotepad.init.Config;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.tool.PopUpUtil;
import org.jcnc.jnotepad.ui.menu.JNotepadMenuBar;
import org.slf4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.jcnc.jnotepad.constants.AppConstants.CONFIG_NAME;
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
        String path = getConfigPath();
        if (!Files.exists(Paths.get(path))) {
            // 不存在则创建
            createConfig();
        }
        // 判断是否存在这个配置文件
        try (InputStream inputStream = new FileInputStream(getConfigPath())) {
            logger.info("正在加载配置文件...");
            // 存在则加载
            loadConfig(inputStream);
        } catch (IOException e) {
            logger.info("未检测到配置文件!");
        }
    }

    void createConfig() {
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
        String jsonConfigPath = getConfigPath();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonConfigPath))) {
            writer.write(JNOTEPAD_CONFIG);
        } catch (IOException e) {
            PopUpUtil.errorAlert("错误", "读写错误", "配置文件读写错误!");
        }
    }

    private static String getConfigPath() {
        return Paths.get(new Config().getAppConfigDir(), CONFIG_NAME).toString();
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

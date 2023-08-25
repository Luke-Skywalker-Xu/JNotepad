package org.jcnc.jnotepad.app.config;

import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.ui.menu.JNotepadMenuBar;
import org.slf4j.Logger;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * 加载快捷键实现
 *
 * @author gewuyou 一个大转盘
 */
public class LoadShortcutKeyConfig extends LoadJnotepadConfig {
    Logger log = LogUtil.getLogger(this.getClass());

    @Override
    protected void loadConfig(InputStream inputStream) {
        List<LinkedHashMap<String, String>> configData = parseConfig(inputStream);
        for (LinkedHashMap<String, String> shortcutKey : configData) {
            // 保证json的key必须和变量名一致
            MenuItem menuItem = JNotepadMenuBar.getMenuBar().getItemMap().get(shortcutKey.get("buttonName"));
            String shortKeyValue = shortcutKey.get("shortcutKeyValue");
            if ("".equals(shortKeyValue) || Objects.isNull(menuItem)) {
                continue;
            }
            log.info("功能名称：{}->快捷键:{}", menuItem.getText(), shortKeyValue);
            // 动态添加快捷键
            menuItem.setAccelerator(KeyCombination.keyCombination(shortKeyValue));
        }
    }
}

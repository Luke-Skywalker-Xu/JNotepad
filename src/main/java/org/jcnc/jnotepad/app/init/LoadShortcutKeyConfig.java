package org.jcnc.jnotepad.app.init;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.ui.menu.JNotepadMenuBar;
import org.slf4j.Logger;

import java.io.InputStream;
import java.util.*;

import static org.jcnc.jnotepad.constants.AppConstants.CONFIG_SHORTCUT_KEY_NAME;

/**
 * 加载快捷键实现
 *
 * @author gewuyou 一个大转盘
 */
public class LoadShortcutKeyConfig extends LoadJnotepadConfig<List<LinkedHashMap<String, String>>> {
    Logger log = LogUtil.getLogger(this.getClass());

    @Override
    protected List<LinkedHashMap<String, String>> parseConfig(InputStream inputStream) {
        JsonNode shortcutKeyNode = getConfigJson(inputStream).get(CONFIG_SHORTCUT_KEY_NAME);
        if (shortcutKeyNode == null || !shortcutKeyNode.isArray()) {
            logger.error("未获取到主要配置文件!");
            return Collections.emptyList();
        }
        List<LinkedHashMap<String, String>> shortcutKeyList = new ArrayList<>();
        for (JsonNode node : shortcutKeyNode) {
            if (node.isObject()) {
                LinkedHashMap<String, String> shortcutKey = new LinkedHashMap<>();
                Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> entry = fields.next();
                    shortcutKey.put(entry.getKey(), entry.getValue().asText());

                }
                shortcutKeyList.add(shortcutKey);
            }
        }
        return shortcutKeyList;
    }

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

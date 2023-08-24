package org.jcnc.jnotepad.app.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.tool.PopUpUtil;
import org.jcnc.jnotepad.ui.menu.JNotepadMenuBar;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static org.jcnc.jnotepad.constants.AppConstants.CONFIG_SHORTCUT_KEY_NAME;

/**
 * 加载快捷键实现
 *
 * @author gewuyou 一个大转盘
 */
public class LoadShortcutKeyConfig extends LoadJnotepadConfig {
    Logger log = LogUtil.getLogger(this.getClass());

    @Override
    protected void loadConfig(InputStream inputStream) {
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
            log.error("配置文件解析错误!", e);
        }
        if (mainConfig == null) {
            log.error("未获取到主要配置文件!");
            return;
        }
        List<LinkedHashMap<String, String>> objectList = mainConfig.get(CONFIG_SHORTCUT_KEY_NAME)
                .stream()
                .map(e -> {
                    if (e instanceof LinkedHashMap) {
                        return (LinkedHashMap<String, String>) e;
                    } else {
                        throw new IllegalArgumentException("Invalid element type");
                    }
                })
                .toList();

        for (LinkedHashMap<String, String> shortcutKey : objectList) {
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

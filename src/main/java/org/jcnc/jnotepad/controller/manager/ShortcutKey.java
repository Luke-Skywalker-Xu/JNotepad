package org.jcnc.jnotepad.controller.manager;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import org.jcnc.jnotepad.Interface.ShortcutKeyInterface;
import org.jcnc.jnotepad.tool.FileUtil;
import org.jcnc.jnotepad.view.manager.ViewManager;

import java.io.File;
import java.util.Map;
import java.util.Objects;

/**
 * @author 一个大转盘
 * @date 2023/8/18
 */
public class ShortcutKey implements ShortcutKeyInterface {
    @Override
    public void createShortcutKeyByConfig() {
        String json = "src/main/resources/config/shortcutKey.json";
        File jsonFile = new File(json);
        // 读取json文件
        String jsonData = FileUtil.getJsonStr(jsonFile);
        // 转json对象
        JSONObject shortcutKeyConfig = JSONUtil.parseObj(jsonData);
        for (Map.Entry<String, Object> stringObjectEntry : shortcutKeyConfig) {
            // 保证json的key必须和变量名一致
            MenuItem menuItem = ViewManager.itemMap.get(stringObjectEntry.getKey());
            String shortKeyValue = stringObjectEntry.getValue().toString();
            if (ObjectUtil.isNotEmpty(shortKeyValue)&&ObjectUtil.isNotEmpty(menuItem)) {
                // 动态添加快捷键
                menuItem.setAccelerator(KeyCombination.keyCombination(shortKeyValue));
            }
        }


    }
}

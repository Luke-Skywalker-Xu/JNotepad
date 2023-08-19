package org.jcnc.jnotepad.controller.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Map<String, String> shortcutKeyConfig = gson.fromJson(jsonData, new TypeToken<Map<String, String>>() {
        }.getType());
        for (Map.Entry<String, String> stringObjectEntry : shortcutKeyConfig.entrySet()) {
            // 保证json的key必须和变量名一致
            MenuItem menuItem = ViewManager.itemMap.get(stringObjectEntry.getKey());
            String shortKeyValue = stringObjectEntry.getValue();

            if ("".equals(shortKeyValue) || Objects.isNull(menuItem)) {
                continue;
            }
            // 动态添加快捷键
            menuItem.setAccelerator(KeyCombination.keyCombination(shortKeyValue));
        }


    }
}

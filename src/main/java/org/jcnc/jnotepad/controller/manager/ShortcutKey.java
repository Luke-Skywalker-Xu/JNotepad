package org.jcnc.jnotepad.controller.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import org.jcnc.jnotepad.Interface.ShortcutKeyInterface;
import org.jcnc.jnotepad.tool.FileUtil;
import org.jcnc.jnotepad.view.manager.ViewManager;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

/**
 * @author 一个大转盘
 * @date 2023/8/18
 */
public class ShortcutKey implements ShortcutKeyInterface {
    @Override
    public void createShortcutKeyByConfig() {
        String rootPath =System.getProperty("user.dir");
        // 构建JSON文件路径
        String jsonFilePath = rootPath +"/config/shortcutKey.json";
        InputStream inputStream = getClass().getResourceAsStream("/config/shortcutKey.json");
        StringBuffer jsonData = new StringBuffer();
        File file = new File(jsonFilePath);
        if(file.exists()){
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonData.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonData.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 转json对象
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Map<String, String> shortcutKeyConfig = gson.fromJson(jsonData.toString(), new TypeToken<Map<String, String>>() {
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

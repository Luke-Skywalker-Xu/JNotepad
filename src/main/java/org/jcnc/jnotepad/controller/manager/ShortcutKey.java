package org.jcnc.jnotepad.controller.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import org.jcnc.jnotepad.Interface.ShortcutKeyInterface;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.view.manager.ViewManager;

import java.io.*;
import java.util.Map;
import java.util.Objects;

import static org.jcnc.jnotepad.constants.PathConstants.SHORTCUT_KEY_CONFIGURATION_FILE_PATH;

/**
 * @author 一个大转盘<br>
 */
public class ShortcutKey implements ShortcutKeyInterface {
    @Override
    public void createShortcutKeyByConfig() {
        String rootPath = System.getProperty("user.dir");
        // 构建JSON文件路径
        String jsonFilePath = rootPath + SHORTCUT_KEY_CONFIGURATION_FILE_PATH;
        InputStream inputStream = getClass().getResourceAsStream(SHORTCUT_KEY_CONFIGURATION_FILE_PATH);
        StringBuffer jsonData = new StringBuffer();
        File file = new File(jsonFilePath);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonData.append(line);
                }
            } catch (IOException e) {
                LogUtil.error(e.getMessage(),e,this.getClass());
            }
        } else {
            // todo new InputStreamReader(inputStream) 实参 'inputStream' 可能为null
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonData.append(line);
                }
            } catch (IOException e) {
                LogUtil.error(e.getMessage(),e,this.getClass());
            }
        }

        // 转json对象
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Map<String, String> shortcutKeyConfig = gson.fromJson(jsonData.toString(), new TypeToken<Map<String, String>>() {
        }.getType());
        for (Map.Entry<String, String> stringObjectEntry : shortcutKeyConfig.entrySet()) {
            // 保证json的key必须和变量名一致
            MenuItem menuItem = ViewManager.getInstance().getItemMap().get(stringObjectEntry.getKey());
            String shortKeyValue = stringObjectEntry.getValue();
            if ("".equals(shortKeyValue) || Objects.isNull(menuItem)) {
                continue;
            }
            // 动态添加快捷键
            menuItem.setAccelerator(KeyCombination.keyCombination(shortKeyValue));
        }
    }
}

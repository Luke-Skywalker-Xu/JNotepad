package org.jcnc.jnotepad.json;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据生成类，用于生成示例数据。
 * @author 许轲
 */
public class DataGenerator {


    private DataGenerator() {
    }
    /// 快捷键常量

    private static final String CTRL_N = "ctrl+n";
    private static final String CTRL_O = "ctrl+o";
    private static final String CTRL_S = "ctrl+s";
    private static final String CTRL_ALT_S = "ctrl+alt+s";
    private static final String ALT_S = "alt+s";

    /**
     * 生成示例 MyData 对象。
     *
     * @return 示例 MyData 对象
     */
    public static MyData generateMyData() {
        MyData myData = new MyData();
        myData.setLanguage("chinese");

        List<MyData.ShortcutKey> shortcutKeys = new ArrayList<>();
        shortcutKeys.add(createShortcutKey("newItem", CTRL_N));
        shortcutKeys.add(createShortcutKey("openItem", CTRL_O));
        shortcutKeys.add(createShortcutKey("saveItem", CTRL_S));
        shortcutKeys.add(createShortcutKey("saveAsItem", CTRL_ALT_S));
        shortcutKeys.add(createShortcutKey("lineFeedItem", ""));
        shortcutKeys.add(createShortcutKey("openConfigItem", ALT_S));
        shortcutKeys.add(createShortcutKey("addItem", ""));
        shortcutKeys.add(createShortcutKey("countItem", ""));

        myData.setShortcutKey(shortcutKeys);
        return myData;
    }

    /**
     * 创建 ShortcutKey 对象。
     *
     * @param buttonName       按钮名称
     * @param shortcutKeyValue 快捷键值
     * @return ShortcutKey 对象
     */
    private static MyData.ShortcutKey createShortcutKey(String buttonName, String shortcutKeyValue) {
        MyData.ShortcutKey shortcutKey = new MyData.ShortcutKey();
        shortcutKey.setButtonName(buttonName);
        shortcutKey.setShortcutKeyValue(shortcutKeyValue);
        return shortcutKey;
    }
}

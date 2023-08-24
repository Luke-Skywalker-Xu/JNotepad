package org.jcnc.jnotepad.view.init;

import org.jcnc.jnotepad.app.config.LoadJnotepadConfig;


/**
 * @author 许轲
 */
public class View {

    private View() {
    }

    private static final View INSTANCE = new View();

    /**
     * 初始化快捷键
     *
     * @param loadJnotepadConfig 加载配置文件
     * @since 2023/8/24 15:29
     */
    public void initShortcutKey(LoadJnotepadConfig loadJnotepadConfig) {
        loadJnotepadConfig.load();
    }
    public static View getInstance() {
        return INSTANCE;
    }
}

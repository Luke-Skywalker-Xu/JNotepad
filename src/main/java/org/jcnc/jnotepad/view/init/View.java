package org.jcnc.jnotepad.view.init;

import org.jcnc.jnotepad.app.config.LoadJnotepadConfig;

import java.util.List;


/**
 * @author 许轲
 */
public class View {

    private View() {
    }

    private static final View INSTANCE = new View();

    /**
     * 初始化配置文件
     *
     * @param loadJnotepadConfigs 需要加载的配置文件数组
     * @since 2023/8/24 15:29
     */
    public void initJnotepadConfigs(List<LoadJnotepadConfig<?>> loadJnotepadConfigs) {
        for (LoadJnotepadConfig<?> loadJnotepadConfig : loadJnotepadConfigs) {
            initJnotepadConfig(loadJnotepadConfig);
        }
    }

    /**
     * 初始化配置文件
     *
     * @param loadJnotepadConfig 配置文件
     * @since 2023/8/24 15:29
     */
    public void initJnotepadConfig(LoadJnotepadConfig<?> loadJnotepadConfig) {
        loadJnotepadConfig.load();
    }

    public static View getInstance() {
        return INSTANCE;
    }
}

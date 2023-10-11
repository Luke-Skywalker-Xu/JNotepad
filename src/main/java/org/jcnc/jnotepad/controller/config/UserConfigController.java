package org.jcnc.jnotepad.controller.config;

import org.jcnc.jnotepad.api.core.controller.config.BaseConfigController;
import org.jcnc.jnotepad.app.config.UserConfig;
import org.jcnc.jnotepad.model.entity.ShortcutKey;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.jcnc.jnotepad.app.common.constants.AppConstants.PROGRAM_FILE_DIRECTORY;
import static org.jcnc.jnotepad.app.common.constants.TextConstants.CHINESE;

/**
 * 应用程序配置控制器
 *
 * <p>该类负责管理应用程序的配置文件，包括加载、持久化和更新配置信息等操作。</p>
 *
 * @author songdragon
 */
public class UserConfigController extends BaseConfigController<UserConfig> {
    /**
     * 配置文件名
     */
    public static final String CONFIG_NAME = "userConfig.json";
    private static final String CTRL_N = "ctrl+n";
    private static final String CTRL_O = "ctrl+o";
    private static final String CTRL_S = "ctrl+s";
    private static final String CTRL_ALT_S = "ctrl+alt+s";
    private static final String ALT_S = "alt+s";
    private static final UserConfigController INSTANCE = new UserConfigController();
    private String configDir;

    private UserConfigController() {
        configDir = Paths.get(AppConfigController.getInstance().getConfig().getRootPath(), PROGRAM_FILE_DIRECTORY, ROOT_CONFIG_DIR).toString();
        loadConfig();
    }

    /**
     * 获取 UserConfigController 的实例。
     *
     * @return UserConfigController 的实例
     */
    public static UserConfigController getInstance() {
        return INSTANCE;
    }

    /**
     * 创建 ShortcutKey 对象。
     *
     * @param buttonName       按钮名称
     * @param shortcutKeyValue 快捷键值
     * @return ShortcutKey 对象
     */
    private static ShortcutKey createShortcutKey(String buttonName, String shortcutKeyValue) {
        ShortcutKey shortcutKey = new ShortcutKey();
        shortcutKey.setButtonName(buttonName);
        shortcutKey.setShortcutKeyValue(shortcutKeyValue);
        return shortcutKey;
    }

    /**
     * 获取配置文件Class类
     *
     * @return 配置文件Class类
     */
    @Override
    protected Class<UserConfig> getConfigClass() {
        return UserConfig.class;
    }

    /**
     * 获取配置文件名称
     *
     * @return 配置文件名称
     */
    @Override
    protected String getConfigName() {
        return CONFIG_NAME;
    }

    /**
     * 获取配置文件文件夹路径
     *
     * @return 配置文件夹路径
     */
    @Override
    protected String getConfigDir() {
        return configDir;
    }

    public void setConfigDir(String configDir) {
        this.configDir = configDir;
    }

    /**
     * 创建配置文件实体
     *
     * @return 默认的配置文件实体
     * @apiNote 返回默认的配置文件实体用于序列化json
     */
    @Override
    public UserConfig generateDefaultConfig() {
        UserConfig config = new UserConfig();
        config.setLanguage(CHINESE);
        config.setTextWrap(false);

        List<ShortcutKey> shortcutKeys = new ArrayList<>();
        shortcutKeys.add(createShortcutKey("newItem", CTRL_N));
        shortcutKeys.add(createShortcutKey("openItem", CTRL_O));
        shortcutKeys.add(createShortcutKey("saveItem", CTRL_S));
        shortcutKeys.add(createShortcutKey("saveAsItem", CTRL_ALT_S));
        shortcutKeys.add(createShortcutKey("lineFeedItem", ""));
        shortcutKeys.add(createShortcutKey("openConfigItem", ALT_S));
        shortcutKeys.add(createShortcutKey("pluginManager", ""));
        shortcutKeys.add(createShortcutKey("countItem", ""));
        shortcutKeys.add(createShortcutKey("aboutItem", ""));

        config.setShortcutKey(shortcutKeys);
        return config;
    }

    /**
     * 获取自动换行设置，默认自动换行。
     *
     * @return true，自动换行；false，不自动换行
     */
    public boolean getAutoLineConfig() {
        return getConfig().isTextWrap();
    }

    public void setAutoLineConfig(boolean isAutoLine) {
        getConfig().setTextWrap(isAutoLine);
    }

    /**
     * 更新配置文件中的语言设置。
     *
     * @param language 更新后的语言设置
     */
    public void updateLanguage(String language) {
        if (getLanguage().equals(language)) {
            return;
        }
        getConfig().setLanguage(language);
        writeConfig();
    }

    /**
     * 获取当前的语言设置。
     *
     * @return 语言设置
     */
    public String getLanguage() {
        return getConfig().getLanguage();
    }

    /**
     * 获取快捷键设置。
     *
     * @return 快捷键设置列表
     */
    public List<ShortcutKey> getShortcutKey() {
        return getConfig().getShortcutKey();
    }

}

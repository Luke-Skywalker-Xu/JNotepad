package org.jcnc.jnotepad.ui.views.root.top.menubar.menu;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import org.jcnc.jnotepad.api.core.views.menu.AbstractBaseMenu;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.controller.config.UserConfigController;
import org.jcnc.jnotepad.controller.i18n.LocalizationController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.jcnc.jnotepad.app.common.constants.TextConstants.*;

/**
 * 语言顶部菜单
 *
 * @author gewuyou
 */
public class LanguageTopMenu extends AbstractBaseMenu {

    private static final LanguageTopMenu INSTANCE = new LanguageTopMenu();

    private final Map<String, RadioMenuItem> languageMenuItems = new HashMap<>();

    UserConfigController userConfigController = UserConfigController.getInstance();

    public static LanguageTopMenu getInstance() {
        return INSTANCE;
    }

    /**
     * 获取菜单名称
     *
     * @return 菜单名称
     */
    @Override
    public String getMenuName() {
        return LANGUAGE;
    }

    /**
     * 获取菜单
     *
     * @return 菜单
     */
    @Override
    public Menu getMenu() {
        return topMenuBar.getLanguageMenu();
    }

    /**
     * 获取菜单项集合
     *
     * @return 菜单项集合
     */
    @Override
    public Map<String, MenuItem> getMenuItems() {
        return Collections.emptyMap();
    }

    /**
     * 初始化菜单栏
     */
    @Override
    public void initMenu() {
        registerMenu();
        logger.info("初始化语言菜单!");
        // 语言菜单
        ToggleGroup languageToggleGroup = new ToggleGroup();
        languageMenuItems.forEach((key, value) -> {
            UiResourceBundle.bindStringProperty(value.textProperty(), key);
            languageToggleGroup.getToggles().add(value);
            topMenuBar.getLanguageMenu().getItems().add(value);
        });
        // 设置当前语言选中状态
        toggleLanguageCheck(userConfigController.getLanguage());
    }


    /**
     * 注册顶部菜单
     *
     * @apiNote 此方法
     */
    @Override
    protected void registerMenu() {
        registerRadioMenuItem(languageMenuItems, topMenuBar.getChineseItem(), UPPER_CHINESE, Locale.CHINESE, this::toggleLanguage);
        registerRadioMenuItem(languageMenuItems, topMenuBar.getEnglishItem(), UPPER_ENGLISH, Locale.ENGLISH, this::toggleLanguage);
    }

    /**
     * 设置当前语言选中状态
     *
     * @param language 语言
     */
    private void toggleLanguageCheck(String language) {
        languageMenuItems.forEach((k, v) -> v.setSelected(language.toUpperCase().equals(k)));
    }

    /**
     * 切换语言
     *
     * @param actionEvent 点击事件
     */
    private void toggleLanguage(ActionEvent actionEvent) {
        if (actionEvent == null) {
            return;
        }
        RadioMenuItem languageItem = (RadioMenuItem) actionEvent.getSource();
        if (languageItem == null) {
            return;
        }
        LocalizationController.setCurrentLocal((Locale) languageItem.getUserData());
    }
}

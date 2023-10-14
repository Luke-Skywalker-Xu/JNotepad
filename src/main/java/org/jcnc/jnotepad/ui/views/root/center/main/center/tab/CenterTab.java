package org.jcnc.jnotepad.ui.views.root.center.main.center.tab;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Tab;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.jcnc.jnotepad.app.utils.FileUtil;
import org.jcnc.jnotepad.app.utils.LoggerUtil;
import org.jcnc.jnotepad.app.utils.TabUtil;
import org.jcnc.jnotepad.controller.config.UserConfigController;
import org.jcnc.jnotepad.ui.component.module.TextCodeArea;
import org.jcnc.jnotepad.ui.views.manager.BottomStatusBoxManager;
import org.jcnc.jnotepad.ui.views.manager.CenterTabPaneManager;
import org.slf4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.jcnc.jnotepad.app.common.constants.TextConstants.READ_ONLY;

/**
 * 封装标签页组件，增加属于标签页的属性，例如：自动换行开关。
 * 每个Tab关联一个TextCodeArea。
 *
 * @author songdragon
 */
public class CenterTab extends Tab {
    private final TextCodeArea textCodeArea;
    /**
     * 是否与本地文件关联
     */
    private final BooleanProperty relevanceProperty = new SimpleBooleanProperty(false);
    /**
     * 是否固定
     */
    private final BooleanProperty fixedProperty = new SimpleBooleanProperty(false);
    /**
     * 只读菜单项
     */
    private final CheckMenuItem readOnly = new CheckMenuItem(READ_ONLY);
    private final BooleanProperty hasLeftTabsProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty hasRightTabsProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty hasOtherTabsProperty = new SimpleBooleanProperty(false);
    Logger logger = LoggerUtil.getLogger(this.getClass());
    /**
     * 默认关闭自动换行
     */
    private boolean autoLine;
    /**
     * 关联文件上次修改时间
     */
    private Long lastModifiedTimeOfAssociatedFile;
    /**
     * 编码
     */
    private Charset charset;

    public CenterTab(String tabTitle) {
        this(tabTitle, new TextCodeArea());
    }

    public CenterTab(String tabTitle, TextCodeArea textArea) {
        this(tabTitle, textArea, Charset.defaultCharset(), null, false);
    }

    public CenterTab(String tabTitle, TextCodeArea textCodeArea, Charset charset, File file, boolean relevanceProperty) {
        super(tabTitle);
        // 在此根据标签页名称设置文件图标
        this.setGraphic(FileUtil.getIconCorrespondingToFileName(tabTitle));
        this.textCodeArea = textCodeArea;
        this.setContent(new VirtualizedScrollPane<>(this.textCodeArea));
        this.autoLine = UserConfigController.getInstance().getAutoLineConfig();
        this.charset = charset;
        this.relevanceProperty.set(relevanceProperty);
        this.setUserData(file);
        // 将监听器于上下文菜单集中处理
        Platform.runLater(() -> {
            initTextAreaListeners();
            this.contextMenuMonitor();
            initFixedStateListener();
        });
    }

    private void initFixedStateListener() {
        fixedProperty.addListener((observable, oldValue, newValue) -> {
            ObservableList<Tab> tabs = CenterTabPane.getInstance().getTabs();
            tabs.forEach(tab -> CenterTabPaneManager.getInstance().checkTabs(tabs, (CenterTab) tab));
        });
    }


    public boolean getRelevanceProperty() {
        return relevanceProperty.get();
    }

    public void setRelevanceProperty(boolean relevanceProperty) {
        this.relevanceProperty.set(relevanceProperty);
    }


    public BooleanProperty relevancePropertyProperty() {
        return relevanceProperty;
    }

    public boolean isAutoLine() {
        return autoLine;
    }

    public void setAutoLine(boolean autoLine) {
        this.autoLine = autoLine;
        textCodeArea.setWrapText(autoLine);
    }

    public TextCodeArea getTextCodeArea() {
        return textCodeArea;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * Monitors the context menu.
     */
    public void contextMenuMonitor() {
        TabUtil.initTabContextMenu(this);
    }


    /**
     * 保存选中的文件标签页
     */
    public void saveSelectedFileTab() {
        // 获取当前选定的中央标签页（CenterTab对象）
        CenterTab tab = CenterTabPaneManager.getInstance().getSelected();

        // 如果没有选定标签页，返回，不执行保存操作
        if (tab == null) {
            return;
        }

        // 从标签页的用户数据中获取文件对象
        File file = (File) tab.getUserData();
        // 获取主文本区域中的文本内容
        String newValue = tab.getTextCodeArea().getText();

        // 如果文件对象为空，记录警告信息并返回，不执行保存操作
        if (file == null) {
            logger.warn("Tab上没有关联文件信息");
            return;
        }

        // 尝试使用BufferedWriter写入文件内容
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, tab.getCharset()))) {
            // 将新的文本内容写入文件
            writer.write(newValue);
            // 记录保存操作的日志信息
            LoggerUtil.getLogger(this.getClass()).info("正在自动保存---");
        } catch (IOException ignored) {
            // 如果发生IO异常，记录忽视的日志信息，但不中断程序执行
            LoggerUtil.getLogger(this.getClass()).info("已忽视IO异常!");
        }
        // 更新最后修改时间
        tab.setLastModifiedTimeOfAssociatedFile(file.lastModified());
    }

    /**
     * 初始化文本监听器方法
     */
    private void initTextAreaListeners() {
        // 监听主要文本区域的文本变化
        textCodeArea.textProperty().addListener((observable, oldValue, newValue) -> {
            BottomStatusBoxManager.getInstance().updateWordCountStatusLabel();
            saveSelectedFileTab();
        });
    }

    /**
     * 保存为指定文件
     *
     * @param file 新文件
     */
    public void save(File file) {
        if (file == null) {
            return;
        }
        this.setUserData(file);
        saveSelectedFileTab();
    }

    public Long getLastModifiedTimeOfAssociatedFile() {
        return lastModifiedTimeOfAssociatedFile;
    }

    public void setLastModifiedTimeOfAssociatedFile(Long lastModifiedTimeOfAssociatedFile) {
        this.lastModifiedTimeOfAssociatedFile = lastModifiedTimeOfAssociatedFile;
    }

    public boolean getNotFixedProperty() {
        return !fixedProperty.get();
    }

    public void setFixedProperty(boolean fixedProperty) {
        this.fixedProperty.set(fixedProperty);
    }


    public BooleanProperty hasLeftTabsPropertyProperty() {
        return hasLeftTabsProperty;
    }


    public BooleanProperty hasRightTabsPropertyProperty() {
        return hasRightTabsProperty;
    }


    public BooleanProperty hasOtherTabsPropertyProperty() {
        return hasOtherTabsProperty;
    }

    public CheckMenuItem getReadOnly() {
        return readOnly;
    }

    public void setHasLeftTabsProperty(boolean hasLeftTabsProperty) {
        this.hasLeftTabsProperty.set(hasLeftTabsProperty);
    }

    public void setHasRightTabsProperty(boolean hasRightTabsProperty) {
        this.hasRightTabsProperty.set(hasRightTabsProperty);
    }

    public void setHasOtherTabsProperty(boolean hasOtherTabsProperty) {
        this.hasOtherTabsProperty.set(hasOtherTabsProperty);
    }
}

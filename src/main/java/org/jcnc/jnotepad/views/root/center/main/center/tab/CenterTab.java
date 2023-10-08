package org.jcnc.jnotepad.views.root.center.main.center.tab;

import javafx.scene.control.Tab;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.jcnc.jnotepad.api.core.views.menu.builder.ContextMenuBuilder;
import org.jcnc.jnotepad.api.core.views.menu.builder.MenuBuilder;
import org.jcnc.jnotepad.component.module.TextCodeArea;
import org.jcnc.jnotepad.controller.config.UserConfigController;
import org.jcnc.jnotepad.controller.event.handler.menuitem.RenameFile;
import org.jcnc.jnotepad.controller.event.handler.menuitem.SaveFile;
import org.jcnc.jnotepad.util.ClipboardUtil;
import org.jcnc.jnotepad.util.LogUtil;
import org.jcnc.jnotepad.util.TabUtil;
import org.jcnc.jnotepad.views.manager.BottomStatusBoxManager;
import org.jcnc.jnotepad.views.manager.CenterTabPaneManager;
import org.slf4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 封装标签页组件，增加属于标签页的属性，例如：自动换行开关。
 * 每个Tab关联一个TextCodeArea。
 *
 * @author songdragon
 */
public class CenterTab extends Tab {
    Logger logger = LogUtil.getLogger(this.getClass());
    private final TextCodeArea textCodeArea;
    /**
     * 默认关闭自动换行
     */
    private boolean autoLine = false;
    /**
     * 是否与本地文件关联
     */
    private boolean isRelevance = false;

    /**
     * 是否固定
     */
    private boolean isFixed = false;
    /**
     * 关联文件上次修改时间
     */
    private Long lastModifiedTimeOfAssociatedFile;
    private Charset charset = Charset.defaultCharset();

    public CenterTab(String tabTitle) {
        this(tabTitle, new TextCodeArea());
    }

    public CenterTab(String tabTitle, TextCodeArea textArea) {
        this(tabTitle, textArea, Charset.defaultCharset());
    }

    public CenterTab(String tabTitle, TextCodeArea textArea, Charset charset) {
        super(tabTitle);
        textCodeArea = textArea;
        initTextAreaListeners();
        this.setContent(new VirtualizedScrollPane<>(textCodeArea));
        setAutoLine(UserConfigController.getInstance().getAutoLineConfig());
        this.charset = charset;
        // 绑定标签页监听
        CenterTabPaneManager.getInstance().setTabsListener(this);
    }

    public boolean isRelevance() {
        return isRelevance;
    }

    public void setRelevance(boolean relevance) {
        isRelevance = relevance;
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
        TabUtil.updateTabContextMenu(this);
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
            LogUtil.getLogger(this.getClass()).info("正在自动保存---");
        } catch (IOException ignored) {
            // 如果发生IO异常，记录忽视的日志信息，但不中断程序执行
            LogUtil.getLogger(this.getClass()).info("已忽视IO异常!");
        }
        // 更新最后修改时间
        tab.setLastModifiedTimeOfAssociatedFile(file.lastModified());
    }

    /**
     * 初始化监听器方法
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

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }
}

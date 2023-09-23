package org.jcnc.jnotepad.views.manager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import org.jcnc.jnotepad.controller.event.handler.setting.SetBtn;
import org.jcnc.jnotepad.views.root.left.sidebar.tools.SidebarToolBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 侧边工具栏管理类
 *
 * @author gewuyou
 */
public class SidebarToolBarManager {
    private static final SidebarToolBarManager INSTANCE = new SidebarToolBarManager();
    /**
     * 工具栏
     */
    SidebarToolBar sidebarToolBar = SidebarToolBar.getInstance();
    List<Button> buttonList = new ArrayList<>();

    public static SidebarToolBarManager getInstance() {
        return INSTANCE;
    }

    public void initSidebarToolBar() {
        registerSidebarToolBar();
        initButtons();
    }

    private void initButtons() {
        // 将按钮添加到工具栏
        sidebarToolBar.getItems().addAll(buttonList);
    }

    public void registerSidebarToolBar() {
        registerButton(sidebarToolBar.getSetButton(), new SetBtn());
    }

    public void registerButton(Button button, EventHandler<ActionEvent> eventHandler) {
        buttonList.add(button);
        setButton(button, eventHandler);
    }

    private void setButton(Button button, EventHandler<ActionEvent> eventHandler) {
        button.setOnAction(eventHandler);
    }
}

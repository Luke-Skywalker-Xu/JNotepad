package org.jcnc.jnotepad.views.manager;

import javafx.scene.Node;
import org.jcnc.jnotepad.api.core.views.manager.AbstractManager;
import org.jcnc.jnotepad.api.core.views.manager.builder.SideBarButtonBuilder;
import org.jcnc.jnotepad.controller.event.handler.toolbar.DirTreeBtn;
import org.jcnc.jnotepad.controller.event.handler.toolbar.RunBtn;
import org.jcnc.jnotepad.controller.event.handler.toolbar.SetBtn;
import org.jcnc.jnotepad.util.UiUtil;
import org.jcnc.jnotepad.views.root.left.sidebar.tools.SidebarToolBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 侧边工具栏管理类
 *
 * @author gewuyou
 */
public class SidebarToolBarManager extends AbstractManager<Node> {
    private static final SidebarToolBarManager INSTANCE = new SidebarToolBarManager();
    /**
     * 工具栏
     */
    SidebarToolBar sidebarToolBar = SidebarToolBar.getInstance();
    private final List<Node> nodeList = new ArrayList<>();

    public static SidebarToolBarManager getInstance() {
        return INSTANCE;
    }

    public void initSidebarToolBar() {
        registerSidebarToolBar();
        // 将节点添加到工具栏
        sidebarToolBar.getItems().addAll(nodeList);
    }


    public void registerSidebarToolBar() {
        // 注册设置按钮
        registerNode(
                new SideBarButtonBuilder()
                        .setButton(sidebarToolBar.getSetButton())
                        .setImageView(UiUtil.sidebarIconByPng("tools"))
                        .setImageViewEssentialAttribute(10D, 10D, true, 2.5D, 2.5D)
                        .setButtonEssentialAttribute(20D, 20D)
                        .setEventHandler(new SetBtn()).build());
        // 注册文件树按钮
        registerNode(
                new SideBarButtonBuilder()
                        .setButton(sidebarToolBar.getDirTreeButton())
                        .setImageView(UiUtil.sidebarIconByPng("directory"))
                        .setImageViewEssentialAttribute(10D, 10D, true, 2.5D, 2.5D)
                        .setButtonEssentialAttribute(20D, 20D)
                        .setEventHandler(new DirTreeBtn()).build());


        // Cmd 按钮
        registerNode(
                new SideBarButtonBuilder()
                        .setButton(sidebarToolBar.getRunButton())
                        .setImageView(UiUtil.sidebarIconByPng("cmd"))
                        .setImageViewEssentialAttribute(10D, 10D, true, 2.5D, 2.5D)
                        .setButtonEssentialAttribute(20D, 20D)
                        .setEventHandler(new RunBtn()).build());
    }


    /**
     * 获取节点列表
     *
     * @return 节点列表
     */
    @Override
    public List<Node> getNodeList() {
        return nodeList;
    }
}

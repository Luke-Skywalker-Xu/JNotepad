package org.jcnc.jnotepad.views.manager;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jcnc.jnotepad.controller.event.handler.setting.SetBtn;
import org.jcnc.jnotepad.views.manager.builder.SideBarButtonBuilder;
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
                        .setImageView(new ImageView(new Image("tools.png")))
                        .setImageViewEssentialAttribute(10D, 10D, true, 2.5D, 2.5D)
                        .setButtonEssentialAttribute(20D, 20D)
                        .setEventHandler(new SetBtn()).build());

        registerNode(
                new SideBarButtonBuilder()
                        .setButton(sidebarToolBar.getFileButton())
                        .setImageView(new ImageView(new Image("tools.png")))
                        .setImageViewEssentialAttribute(10D, 10D, true, 2.5D, 2.5D)
                        .setButtonEssentialAttribute(20D, 20D)
                        // TODO: 2023/10/2 修改点击事件
                        .setEventHandler(new SetBtn()).build());

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

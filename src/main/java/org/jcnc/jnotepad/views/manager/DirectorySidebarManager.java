package org.jcnc.jnotepad.views.manager;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TreeItem;
import org.jcnc.jnotepad.common.manager.ApplicationCacheManager;
import org.jcnc.jnotepad.common.util.FileUtil;
import org.jcnc.jnotepad.controller.event.handler.menubar.OpenDirectory;
import org.jcnc.jnotepad.model.entity.DirFileModel;
import org.jcnc.jnotepad.views.root.center.main.MainBorderPane;
import org.jcnc.jnotepad.views.root.center.main.center.directory.DirectorySidebarPane;

import java.io.File;
import java.util.List;

/**
 * 文件树管理类
 *
 * @author : cccqyu
 */
public class DirectorySidebarManager {

    private DirectorySidebarManager() {
    }

    private static final ApplicationCacheManager CACHE_MANAGER = ApplicationCacheManager.getInstance();

    /**
     * 单例模式，保证只有一个 DirectorySidebar 实例
     */
    private static final DirectorySidebarManager INSTANCE = new DirectorySidebarManager();


    public static DirectorySidebarManager getInstance() {
        return INSTANCE;
    }

    private static final MainBorderPane MAIN_BORDER_PANE = MainBorderPane.getInstance();

    private static final DirectorySidebarPane DIRECTORY_SIDEBAR_PANE = DirectorySidebarPane.getInstance();

    /**
     * 控制文件树显示
     */
    public void controlShow() {
        boolean isVisible = DIRECTORY_SIDEBAR_PANE.isVisible();
        // 设置自身显示
        DIRECTORY_SIDEBAR_PANE.setVisible(!isVisible);
        if (isVisible) {
            // 布局中移除
            MAIN_BORDER_PANE.setLeft(null);
        } else {
            MAIN_BORDER_PANE.setLeft(DIRECTORY_SIDEBAR_PANE);
        }

    }

    /**
     * 控制文件树显示
     *
     * @param bool 打开
     */
    public void controlShow(boolean bool) {
        // 设置自身显示
        DIRECTORY_SIDEBAR_PANE.setVisible(bool);
        if (!MAIN_BORDER_PANE.getChildren().contains(DIRECTORY_SIDEBAR_PANE)) {
            MAIN_BORDER_PANE.setLeft(DIRECTORY_SIDEBAR_PANE);
        }

    }

    private static ChangeListener<Boolean> getTreeItemListener(TreeItem<DirFileModel> item) {
        return (observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                item.setGraphic(item.getValue().getIconIsSelected());
            } else {
                item.setGraphic(item.getValue().getIconIsNotSelected());
            }
        };
    }

    /**
     * 设置文件树内容
     *
     * @param dirFileModel 文件
     */
    public void setTreeView(DirFileModel dirFileModel) {
        TreeItem<DirFileModel> rootItem = new TreeItem<>(dirFileModel, dirFileModel.getIconIsNotSelected());

        DIRECTORY_SIDEBAR_PANE.setRoot(rootItem);
        rootItem.expandedProperty().addListener(getTreeItemListener(rootItem));
        expandFolder(dirFileModel, rootItem);
    }

    /**
     * 递归展开 dirFileModel
     *
     * @param dirFileModel 文件
     * @param item         文件树子项内容
     */
    private void expandFolder(DirFileModel dirFileModel, TreeItem<DirFileModel> item) {
        List<DirFileModel> childFileList = dirFileModel.getChildFile();
        if (childFileList != null) {
            for (DirFileModel childFile : childFileList) {
                TreeItem<DirFileModel> childItem = new TreeItem<>(childFile, childFile.getIconIsNotSelected());
                childItem.expandedProperty().addListener(getTreeItemListener(childItem));
                item.getChildren().add(childItem);
                expandFolder(childFile, childItem);
            }

        }
    }

    /**
     * 展开已打开文件树
     *
     * @since 2023/10/2 23:12
     */
    public void expandTheOpenFileTree() {
        // 获取缓存
        Object cacheData = CACHE_MANAGER.getCacheData(OpenDirectory.GROUP, "folderThatWasOpened");
        // 判空
        if (cacheData == null) {
            return;
        }
        // 打开侧边栏
        controlShow(true);
        // 设置文件树功能
        setTreeView(FileUtil.getDirFileModel(new File((String) cacheData)));
    }


}

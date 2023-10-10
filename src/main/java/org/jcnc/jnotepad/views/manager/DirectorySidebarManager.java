package org.jcnc.jnotepad.views.manager;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import org.jcnc.jnotepad.common.constants.SplitPaneItemConstants;
import org.jcnc.jnotepad.common.manager.ApplicationCacheManager;
import org.jcnc.jnotepad.controller.event.handler.toolbar.OpenDirectory;
import org.jcnc.jnotepad.model.entity.DirFileModel;
import org.jcnc.jnotepad.util.FileUtil;
import org.jcnc.jnotepad.views.root.center.main.MainBorderPane;
import org.jcnc.jnotepad.views.root.center.main.center.directory.DirectorySidebarPane;

import java.io.File;
import java.util.List;

/**
 * 文件树管理类
 *
 * <p>管理文件树，处理文件树操作</p>
 *
 * @author cccqyu
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


    private static final double LAST_DIVIDER_POSITION = 0.3;

    private static boolean isShow = false;

    /**
     * 控制文件树显示
     */
    public void controlShow() {
        // 获取root分割面板
        SplitPane root = (SplitPane) MAIN_BORDER_PANE.getCenter();

        // 获取root的上部分割面板
        SplitPane topSplitPane = (SplitPane) root.getItems().get(SplitPaneItemConstants.ROOT_SPLIT_PANE_TOP_SPLIT_PANE);

        if (isShow) {
            topSplitPane.setDividerPositions(0);
        } else {
            // 展开分割条，文件树
            topSplitPane.setDividerPositions(LAST_DIVIDER_POSITION);
        }
        isShow = !isShow;
    }

    /**
     * 控制文件树显示
     *
     * @param bool 打开
     */
    public void controlShow(boolean bool) {
        if (bool) {
            // 获取分割面板
            SplitPane root = (SplitPane) MAIN_BORDER_PANE.getCenter();
            // 获取root的上部分割面板
            SplitPane topSplitPane = (SplitPane) root.getItems().get(SplitPaneItemConstants.ROOT_SPLIT_PANE_TOP_SPLIT_PANE);

            topSplitPane.setDividerPositions(LAST_DIVIDER_POSITION);
            isShow = true;
        }
    }

    /**
     * 设置文件树项监听事件
     *
     * @param item 文件树项
     * @return 监听事件
     */
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
     * Check if the given `DirFileModel` represents a directory.
     *
     * @param childFile the `DirFileModel` to check
     * @return `true` if the `childFile` represents a directory, `false` otherwise
     */
    private static boolean isDirectoryByDirFileModel(DirFileModel childFile) {
        return new File(childFile.getPath()).isDirectory();
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
                // 只有文件夹树才添加监听事件
                if (isDirectoryByDirFileModel(childFile)) {
                    childItem.expandedProperty().addListener(getTreeItemListener(childItem));
                }
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

package org.jcnc.jnotepad.views.manager;

import javafx.scene.control.SplitPane;
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


    private static final double lastDividerPosition = 0.3;

    /**
     * 控制文件树显示
     */
    public void controlShow() {
        // 获取分割面板
        SplitPane center = (SplitPane) MAIN_BORDER_PANE.getCenter();
        // 获取分割条位置
        double dividerPosition = center.getDividerPositions()[0];
        // 保留分割条位置一位小数
        String formattedNumber = String.format("%.1f", dividerPosition);
        double roundedNumber = Double.parseDouble(formattedNumber);

        // 分割条位置不等于 代表展开
        if (roundedNumber != 0.0) {
            // 收缩分割条 收缩文件树
            center.setDividerPositions(0.0);

        } else {
            // 展开分割条，文件树
            center.setDividerPositions(lastDividerPosition);

        }
    }

    /**
     * 控制文件树显示
     *
     * @param bool 打开
     */
    public void controlShow(boolean bool) {
        if (bool) {
            // 获取分割面板
            SplitPane center = (SplitPane) MAIN_BORDER_PANE.getCenter();
            center.setDividerPositions(lastDividerPosition);
        }
    }

    /**
     * 设置文件树内容
     *
     * @param dirFileModel 文件
     */
    public void setTreeView(DirFileModel dirFileModel) {
        TreeItem<DirFileModel> rootItem = new TreeItem<>(dirFileModel);

        DIRECTORY_SIDEBAR_PANE.setRoot(rootItem);

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
                TreeItem<DirFileModel> childItem = new TreeItem<>(childFile);
                item.getChildren().add(childItem);
                expandFolder(childFile, childItem);
            }

        }
    }

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

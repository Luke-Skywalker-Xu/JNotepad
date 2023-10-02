package org.jcnc.jnotepad.views.manager;

import javafx.scene.control.TreeItem;
import org.jcnc.jnotepad.model.entity.DirFileModel;
import org.jcnc.jnotepad.views.root.center.main.MainBorderPane;
import org.jcnc.jnotepad.views.root.center.main.center.directory.DirectorySidebarPane;

import java.util.List;

/**
 * @author : cccqyu
 * @createTime 2023/10/2  20:33
 * @description 文件树管理类
 */
public class DirectorySidebarManager {

    private DirectorySidebarManager() {
    }

    ;
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
     * */
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
     * @param bool 打开
     * */
    public void controlShow(boolean bool) {
        // 设置自身显示
        DIRECTORY_SIDEBAR_PANE.setVisible(bool);
        if (!MAIN_BORDER_PANE.getChildren().contains(DIRECTORY_SIDEBAR_PANE)) {
            MAIN_BORDER_PANE.setLeft(DIRECTORY_SIDEBAR_PANE);
        }

    }

    /**
     * 设置文件树内容
     * @param dirFileModel 文件
    * */
    public void setTreeView(DirFileModel dirFileModel) {
        TreeItem<DirFileModel> rootItem = new TreeItem<DirFileModel>(dirFileModel);

        DIRECTORY_SIDEBAR_PANE.setRoot(rootItem);

        expandFolder(dirFileModel, rootItem);
    }
    /**
     * 递归展开 dirFileModel
     * @param dirFileModel 文件
     * @param item  文件树子项内容
     * */
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


}

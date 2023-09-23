package org.jcnc.jnotepad.views.manager;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import org.jcnc.jnotepad.exception.AppException;
import org.jcnc.jnotepad.views.root.RootBorderPane;

import static org.jcnc.jnotepad.views.root.bottom.RootBottomSideBarVerticalBox.initSidebarVerticalBox;

/**
 * 根布局管理器类，用于管理记事本应用程序的根布局组件。
 *
 * <p>该类负责管理应用程序的视图组件，包括主界面布局</p>
 *
 * @author 许轲
 */
public class RootManager {

    private static RootManager instance = null;

    /**
     * 主布局
     */
    private BorderPane root;

    /**
     * 私有构造函数。设置场景和根布局。
     *
     * @param scene 与视图相关联的 JavaFX 场景。
     */
    private RootManager(Scene scene) {
        root = new BorderPane();
        scene.setRoot(root);
    }

    /**
     * 获取 RootManager 的实例。如果实例不存在，则创建一个新实例。
     *
     * @param scene 与视图相关联的 JavaFX 场景。
     * @return RootManager 的实例。
     */
    public static RootManager getInstance(Scene scene) {
        if (instance == null) {
            instance = new RootManager(scene);
        }
        return instance;
    }

    /**
     * 获取 RootManager 的实例。
     *
     * @return RootManager 的实例。
     * @throws AppException 如果实例未初始化。
     */
    public static RootManager getInstance() {
        if (instance != null) {
            return instance;
        } else {
            throw new AppException("RootManager 的实例未初始化!");
        }
    }


    /**
     * 初始化屏幕组件。
     *
     * @param scene 与视图相关联的 JavaFX 场景。
     */
    public void initScreen(Scene scene) {
        // 创建主界面布局
        root = new BorderPane();
        root.setCenter(RootBorderPane.getInstance());

        scene.setRoot(root);
        initSidebarVerticalBox();
    }
}

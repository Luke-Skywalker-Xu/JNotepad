package org.jcnc.jnotepad.view.manager;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import org.jcnc.jnotepad.exception.AppException;
import org.jcnc.jnotepad.root.RootBorderPane;

/**
 * 视图管理器类，用于管理记事本应用程序的视图组件。
 *
 * <p>该类负责管理应用程序的视图组件，包括主界面布局和标签页索引等。</p>
 *
 * @author 许轲
 */
public class ViewManager {

    private static ViewManager instance = null;

    /**
     * 主布局
     */
    private BorderPane root;

    /**
     * 私有构造函数。设置场景和根布局。
     *
     * @param scene 与视图相关联的 JavaFX 场景。
     */
    private ViewManager(Scene scene) {
        root = new BorderPane();
        scene.setRoot(root);
    }

    /**
     * 获取 ViewManager 的实例。如果实例不存在，则创建一个新实例。
     *
     * @param scene 与视图相关联的 JavaFX 场景。
     * @return ViewManager 的实例。
     */
    public static ViewManager getInstance(Scene scene) {
        if (instance == null) {
            instance = new ViewManager(scene);
        }
        return instance;
    }

    /**
     * 获取 ViewManager 的实例。
     *
     * @return ViewManager 的实例。
     * @throws AppException 如果实例未初始化。
     */
    public static ViewManager getInstance() {
        if (instance != null) {
            return instance;
        } else {
            throw new AppException("ViewManager 的实例未初始化!");
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
        /*root.setTop(CustomTitleBar.getInstance());*/
        root.setCenter(RootBorderPane.getInstance());

        scene.setRoot(root);
    }
}

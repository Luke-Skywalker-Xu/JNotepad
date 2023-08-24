package org.jcnc.jnotepad.view.manager;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import org.jcnc.jnotepad.exception.AppException;
import org.jcnc.jnotepad.ui.menu.JNotepadMenuBar;
import org.jcnc.jnotepad.ui.status.JNotepadStatusBox;
import org.jcnc.jnotepad.ui.tab.JNotepadTabPane;


/**
 * 该类管理记事本应用程序的视图组件。
 *
 * @author 许轲
 */
public class ViewManager {

    private int tabIndex = 0;

    private Boolean line = true;


    // 主界面布局
    private BorderPane root; // 主布局


    private static ViewManager instance = null;


    /**
     * 自增并获取标签页索引
     *
     * @return int 标签页索引
     * @apiNote ++tabIndex
     */
    public int selfIncreaseAndGetTabIndex() {
        return ++tabIndex;
    }

    public Boolean getLine() {
        return line;
    }


    public BorderPane getRoot() {
        return root;
    }


    /**
     * 获取ViewManager的实例。如果实例不存在，则创建一个新实例。
     *
     * @param scene 与视图相关联的JavaFX场景。
     * @return ViewManager的实例。
     */
    public static ViewManager getInstance(Scene scene) {
        if (instance == null) {
            instance = new ViewManager(scene);
        }
        return instance;
    }

    public static ViewManager getInstance() {
        if (instance != null) {
            return instance;
        } else {
            throw new AppException("ViewManager的实例未初始化!");
        }
    }

    /**
     * 构造函数。设置场景和根布局。
     *
     * @param scene 与视图相关联的JavaFX场景。
     */
    private ViewManager(Scene scene) {
        root = new BorderPane();
        scene.setRoot(root);

    }

    /**
     * 初始化屏幕组件。
     *
     * @param scene 与视图相关联的JavaFX场景。
     */
    public void initScreen(Scene scene) {

        // 创建主界面布局
        root = new BorderPane();
        root.setTop(JNotepadMenuBar.getMenuBar());

        // 创建标签页和文本编辑区域
        root.setCenter(JNotepadTabPane.getInstance());
        root.setBottom(JNotepadStatusBox.getInstance());

        scene.setRoot(root);
    }
}

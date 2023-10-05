package org.jcnc.jnotepad.api.core.component.stage;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 抽象窗格舞台
 *
 * @author gewuyou
 */
public abstract class AbstractPaneStage extends BorderPane {
    private final Stage stage = new Stage();

    /**
     * 获取舞台图标
     *
     * @return 舞台图标
     */
    protected abstract Image getStageIcon();

    /**
     * 获取舞台标题
     *
     * @return 舞台标题
     */
    protected abstract String getStageTitle();

    /**
     * 获取自定义舞台
     *
     * @return 舞台
     */
    protected abstract Scene getCustomizationScene();

    /**
     * 初始化方法
     */
    protected abstract void initialize();

    /**
     * 自定义启动方法
     *
     * @param stage 自定义舞台
     */
    public abstract void run(Stage stage);

    /**
     * 启动方法
     */
    public void run() {
        stage.getIcons().add(getStageIcon());
        stage.setTitle(getStageTitle());
        stage.setScene(getCustomizationScene());
        stage.show();
    }
}

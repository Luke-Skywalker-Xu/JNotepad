package org.jcnc.jnotepad.api.core.component.stage;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 抽象窗格舞台。
 * <p>
 * 该类是一个抽象的窗格舞台，用于创建自定义的JavaFX窗口。
 * </p>
 *
 * @author gewuyou
 */
public abstract class AbstractPaneStage extends BorderPane {
    private final Stage stage = new Stage();

    /**
     * 获取舞台图标。
     *
     * @return 舞台图标
     */
    protected abstract Image getStageIcon();

    /**
     * 获取舞台标题。
     *
     * @return 舞台标题
     */
    protected abstract String getStageTitle();

    /**
     * 获取自定义舞台场景。
     *
     * @return 舞台场景
     */
    protected abstract Scene getCustomizationScene();

    /**
     * 初始化方法。
     * <p>
     * 在此方法中，您可以进行与窗口相关的初始化操作。
     * </p>
     */
    protected abstract void initialize();

    /**
     * 自定义启动方法。
     *
     * @param stage 自定义舞台
     */
    public abstract void run(Stage stage);

    /**
     * 启动方法。
     * <p>
     * 该方法设置窗口的图标、标题、场景，并将窗口设置为模态对话框，然后显示窗口。
     * </p>
     */
    public void run() {
        stage.getIcons().add(getStageIcon());
        stage.setTitle(getStageTitle());
        stage.setScene(getCustomizationScene());
        // 设置为模态
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}

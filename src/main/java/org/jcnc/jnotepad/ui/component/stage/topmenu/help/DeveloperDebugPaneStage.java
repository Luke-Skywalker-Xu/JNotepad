package org.jcnc.jnotepad.ui.component.stage.topmenu.help;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jcnc.jnotepad.api.core.component.stage.AbstractPaneStage;
import org.jcnc.jnotepad.app.manager.ApplicationManager;
import org.jcnc.jnotepad.app.util.LogUtil;
import org.jcnc.jnotepad.app.util.PopUpUtil;
import org.jcnc.jnotepad.app.util.UiUtil;
import org.jcnc.jnotepad.ui.component.stage.topmenu.builder.StageButtonBuilder;
import org.slf4j.Logger;

/**
 * 开发者调试页面
 *
 * @author gewuyou
 */
public class DeveloperDebugPaneStage extends AbstractPaneStage {
    private static final String DEBUG_STR = "开发者调试: {}启动!";
    Logger logger = LogUtil.getLogger(this.getClass());

    /**
     * 获取舞台图标
     *
     * @return 舞台图标
     */
    @Override
    protected Image getStageIcon() {
        return UiUtil.getAppIcon();
    }

    /**
     * 获取舞台标题
     *
     * @return 舞台标题
     */
    @Override
    protected String getStageTitle() {
        return "开发者调试页面";
    }

    /**
     * 获取自定义舞台
     *
     * @return 舞台
     */
    @Override
    protected Scene getCustomizationScene() {
        return new Scene(createVerticalLayout(), 800, 600);
    }

    /**
     * 创建垂直布局
     *
     * @return 垂直布局
     */
    private VBox createVerticalLayout() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setSpacing(10);
        HBox alertBox = new HBox(5);
        alertBox.getChildren().addAll(
                new StageButtonBuilder()
                        .setText("错误提示框").setEventEventHandler(e -> {
                            Button button = (Button) e.getSource();
                            // 在这里执行调试功能1的代码
                            logger.debug(DEBUG_STR, button.getText());
                            PopUpUtil.errorAlert("错误", "错误", "这是一个示例错误提示框!", null, null);
                        }).build(),
                new StageButtonBuilder()
                        .setText("信息提示框").setEventEventHandler(e -> {
                            Button button = (Button) e.getSource();
                            // 在这里执行调试功能2的代码
                            logger.debug(DEBUG_STR, button.getText());
                            PopUpUtil.infoAlert("信息", "信息", "这是一个示例信息提示框!", null, null);
                        }).build(),
                new StageButtonBuilder()
                        .setText("警告提示框").setEventEventHandler(e -> {
                            Button button = (Button) e.getSource();
                            // 在这里执行调试功能3的代码
                            logger.debug(DEBUG_STR, button.getText());
                            PopUpUtil.warningAlert("警告", "警告", "这是一个示例警告提示框!", null, null);
                        }).build(),
                new StageButtonBuilder()
                        .setText("疑问提示框").setEventEventHandler(e -> {
                            Button button = (Button) e.getSource();
                            // 在这里执行调试功能4的代码
                            logger.debug(DEBUG_STR, button.getText());
                            PopUpUtil.questionAlert("疑问", "疑问", "这是一个示例疑问提示框!", null, null);
                        }).build(),
                new StageButtonBuilder()
                        .setText("成功提示框").setEventEventHandler(e -> {
                            Button button = (Button) e.getSource();
                            // 在这里执行调试功能5的代码
                            logger.debug(DEBUG_STR, button.getText());
                            PopUpUtil.successAlert("成功", "成功", "这是一个示例成功提示框!", null, null);
                        }).build()
        );
        HBox toolBox = new HBox(5);
        toolBox.getChildren().addAll(
                new StageButtonBuilder()
                        .setText("重启软件").setEventEventHandler(e -> {
                            Button button = (Button) e.getSource();
                            // 执行重启操作
                            logger.debug(DEBUG_STR, button.getText());
                            ApplicationManager.getInstance().restart();
                        }).build()
        );
        // 添加一些调试功能按钮和标签
        Label alertLabel = new Label("提示框");
        Label toolLabel = new Label("工具");
        root.getChildren().addAll(alertLabel, alertBox, toolLabel, toolBox);
        return root;
    }


    /**
     * 初始化方法
     */
    @Override
    protected void initialize() {
        // 在此写初始化
        logger.info("开发者调试页面初始化");
    }

    /**
     * 自定义启动方法
     *
     * @param stage 自定义舞台
     */
    @Override
    public void run(Stage stage) {
        // 在此添加自定义逻辑
        stage.show();
    }
}

package org.jcnc.jnotepad.ui.setstage;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jcnc.jnotepad.app.manager.ApplicationManager;
import org.jcnc.jnotepad.common.util.LogUtil;
import org.jcnc.jnotepad.common.util.PopUpUtil;
import org.jcnc.jnotepad.common.util.UiUtil;
import org.slf4j.Logger;

/**
 * @author luke
 */
public class DeveloperDebugStage extends Stage {
    Logger logger = LogUtil.getLogger(this.getClass());

    public void start(Stage primaryStage) {
        // 创建主舞台
        primaryStage.setTitle("开发者调试页面");
        primaryStage.getIcons().add(UiUtil.getAppIcon());
        // 创建一个垂直布局
        VBox root = new VBox(10);
        HBox alertBox = new HBox(5);
        HBox toolBox = new HBox(5);

        root.setPadding(new Insets(20));
        root.setSpacing(10);

        // 添加一些调试功能按钮和标签
        Label alertLabel = new Label("提示框");
        Label toolLabel = new Label("工具");

        Button debugButton1 = new Button("错误提示框");
        Button debugButton2 = new Button("信息提示框");
        Button debugButton3 = new Button("警告提示框");
        Button debugButton4 = new Button("疑问提示框");
        Button debugButton5 = new Button("成功提示框");

        // 按钮点击事件处理
        debugButton1.setOnAction(e -> {
            // 在这里执行调试功能1的代码
            logger.debug("开发者调试: {}启动!", debugButton1.getText());
            PopUpUtil.errorAlert("错误", "错误", "这是一个示例错误提示框!", null, null);

        });

        debugButton2.setOnAction(e -> {
            // 在这里执行调试功能2的代码
            logger.debug("开发者调试: {}启动!", debugButton2.getText());
            PopUpUtil.infoAlert("信息", "信息", "这是一个示例信息提示框!", null, null);
        });

        debugButton3.setOnAction(e -> {
            // 在这里执行调试功能3的代码
            logger.debug("开发者调试: {}启动!", debugButton3.getText());
            PopUpUtil.warningAlert("警告", "警告", "这是一个示例警告提示框!", null, null);
        });
        debugButton4.setOnAction(e -> {
            // 在这里执行调试功能4的代码
            logger.debug("开发者调试: {}启动!", debugButton4.getText());
            PopUpUtil.questionAlert("疑问", "疑问", "这是一个示例疑问提示框!", null, null);
        });
        debugButton5.setOnAction(e -> {
            // 在这里执行调试功能5的代码
            logger.debug("开发者调试: {}启动!", debugButton5.getText());
            PopUpUtil.successAlert("成功", "成功", "这是一个示例成功提示框!", null, null);
        });

        Button debugButton6 = new Button("重启软件");
        debugButton6.setOnAction(event -> {
            logger.debug("开发者调试: {}启动!", debugButton6.getText());
            // 执行重启操作
            ApplicationManager.getInstance().restart();
        });

        alertBox.getChildren().addAll(debugButton1, debugButton2, debugButton3, debugButton4, debugButton5);
        toolBox.getChildren().addAll(debugButton6);
        // 将组件添加到布局中
        root.getChildren().addAll(alertLabel, alertBox, toolLabel, toolBox);

        // 创建场景
        Scene scene = new Scene(root, 800, 600);

        // 将场景添加到舞台
        primaryStage.setScene(scene);

        // 显示舞台
        primaryStage.show();
    }
}

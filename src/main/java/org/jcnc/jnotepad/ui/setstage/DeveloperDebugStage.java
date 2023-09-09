package org.jcnc.jnotepad.ui.setstage;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jcnc.jnotepad.util.PopUpUtil;

/**
 * @author luke
 */
public class DeveloperDebugStage extends Stage {

    public void start(Stage primaryStage) {
        // 创建主舞台
        primaryStage.setTitle("开发者调试页面");

        // 创建一个垂直布局
        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(10);

        // 添加一些调试功能按钮和标签
        Label titleLabel = new Label("开发者调试页面");
        Button debugButton1 = new Button("错误提示框");
        Button debugButton2 = new Button("调试功能2");
        Button debugButton3 = new Button("调试功能3");

        // 按钮点击事件处理
        debugButton1.setOnAction(e -> {
            // 在这里执行调试功能1的代码
            System.out.println("开发者调试: " + debugButton1.getText() + " 启动!");
            PopUpUtil.errorAlert("错误", "读写错误", "配置文件读写错误!", null, null);

        });

        debugButton2.setOnAction(e -> {
            // 在这里执行调试功能2的代码
        });

        debugButton3.setOnAction(e -> {
            // 在这里执行调试功能3的代码
        });

        // 将组件添加到布局中
        root.getChildren().addAll(titleLabel, debugButton1, debugButton2, debugButton3);

        // 创建场景
        Scene scene = new Scene(root, 400, 300);

        // 将场景添加到舞台
        primaryStage.setScene(scene);

        // 显示舞台
        primaryStage.show();
    }
}

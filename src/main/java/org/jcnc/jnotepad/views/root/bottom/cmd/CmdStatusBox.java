package org.jcnc.jnotepad.views.root.bottom.cmd;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.jcnc.jnotepad.ui.module.TerminalEmulatorComponent;
import org.jcnc.jnotepad.util.UiUtil;

/**
 * CmdStatusBox 类表示应用程序的命令状态框。
 * <p>
 * 该框包括一个菜单栏，用于运行、终端和构建命令。用户可以点击终端菜单项以切换终端的显示状态。
 * 终端显示时，将创建一个新的窗口以显示终端模拟器组件。
 *
 * @author luke
 */
public class CmdStatusBox extends HBox {

    Stage terminalStage = new Stage();

    private static final CmdStatusBox CMD_STATUS_BOX = new CmdStatusBox();

    /**
     * 用于跟踪终端的显示状态
     */
    private boolean terminalVisible = false;

    private CmdStatusBox() {
        initStatusBox();
    }

    /**
     * 获取 CmdStatusBox 的实例。
     *
     * @return CmdStatusBox 的实例
     */
    public static CmdStatusBox getInstance() {
        return CMD_STATUS_BOX;
    }

    /**
     * 初始化命令状态框。
     */
    public void initStatusBox() {
        var menuBar = new MenuBar();
        HBox.setHgrow(menuBar, Priority.ALWAYS);
        menuBar.setPadding(new Insets(-3, 0, -3, 35));

        var runMenu = new Menu();
        var cmdMenu = new Menu();
        var buildMenu = new Menu();

        var runLabel = new Label("运行");
        var cmdLabel = new Label("终端");
        var buildLabel = new Label("构建");

        runMenu.setGraphic(runLabel);
        cmdMenu.setGraphic(cmdLabel);
        buildMenu.setGraphic(buildLabel);

        cmdLabel.setOnMouseClicked(mouseEvent -> {
            toggleTerminal(); // 切换终端的显示/隐藏状态
        });

        menuBar.getMenus().addAll(runMenu, cmdMenu, buildMenu);
        this.getChildren().add(menuBar);
    }

    /**
     * 切换终端的显示/隐藏状态。
     */
    private void toggleTerminal() {
        if (terminalVisible) {
            // 隐藏终端
            terminalVisible = false;
            hideTerminal();
        } else {
            // 显示终端
            terminalVisible = true;
            showTerminal();
        }
    }

    /**
     * 隐藏终端窗口。
     */
    private void hideTerminal() {
        terminalStage.close();
    }

    /**
     * 显示终端窗口。
     */
    private void showTerminal() {
        // 创建一个新的舞台（窗口）
        terminalStage.setTitle("终端");
        terminalStage.getIcons().add(UiUtil.getAppIcon());

        // 创建一个根节点（布局）
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, UiUtil.getAppWindow().getWidth() - 50, UiUtil.getAppWindow().getHeight() / 3);

        // 创建TerminalEmulatorComponent并添加到根节点
        TerminalEmulatorComponent terminal = new TerminalEmulatorComponent();

        root.setCenter(terminal);
        terminalStage.setScene(scene);

        // 显示窗口
        terminalStage.show();
    }
}

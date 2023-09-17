package org.jcnc.jnotepad.views.root.bottom.cmd;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.jcnc.jnotepad.ui.module.TerminalEmulatorComponent;

/**
 * @author luke
 */
public class CmdStatusBox extends HBox {

    private static final CmdStatusBox CMD_STATUS_BOX = new CmdStatusBox();

    /**
     * 用于跟踪终端的显示状态
     */
    private boolean terminalVisible = false;

    private CmdStatusBox() {
        initStatusBox();
    }

    public static CmdStatusBox getInstance() {
        return CMD_STATUS_BOX;
    }

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

    private void toggleTerminal() {
        if (terminalVisible) {
            // 隐藏终端
            terminalVisible = false;
            // 在这里添加隐藏终端的代码
        } else {
            // 显示终端
            terminalVisible = true;
            showTerminal(); // 显示终端
        }
    }

    private void showTerminal() {
        TerminalEmulatorComponent terminal = new TerminalEmulatorComponent();
        // 在这里添加显示终端的代码

    }
}

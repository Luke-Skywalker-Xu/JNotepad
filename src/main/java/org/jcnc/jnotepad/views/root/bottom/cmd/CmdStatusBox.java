package org.jcnc.jnotepad.views.root.bottom.cmd;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jcnc.jnotepad.controller.event.handler.menubar.SaveAsFile;
import org.jcnc.jnotepad.util.LogUtil;

/**
 * @author luke
 */
public class CmdStatusBox extends HBox {

    private static final CmdStatusBox CMD_STATUS_BOX = new CmdStatusBox();

    private CmdStatusBox() {
        initStatusBox();
    }

    public static CmdStatusBox getInstance() {
        return CMD_STATUS_BOX;
    }

    /**
     * 初始化底部CMD按钮组件
     */
    public void initStatusBox() {


        var menuBar = new MenuBar();
        HBox.setHgrow(menuBar, Priority.ALWAYS);
        menuBar.setPadding(new Insets(-3, 0, -3, 35));

        // 创建菜单项 1
        var runMenu = new Menu();
        var cmdMenu = new Menu();
        var buildMenu = new Menu();

        // 创建菜单项 2
        var runLabel = new Label("运行");
        var cmdLabel = new Label("终端");
        var buildLabel = new Label("构建");

        // 创建菜单点击事件 2
        runMenu.setGraphic(runLabel);
        cmdMenu.setGraphic(cmdLabel);
        buildMenu.setGraphic(buildLabel);

        cmdLabel.setOnMouseClicked(mouseEvent -> {
            showTerminal();
            LogUtil.getLogger(this.getClass()).info("打开终端成功!");
        });

        // 将一级菜单添加到菜单栏
        menuBar.getMenus().addAll(runMenu, cmdMenu, buildMenu);

        // 将菜单栏添加到CmdStatusBox
        this.getChildren().add(menuBar);


    }

    /**
     * 在菜单上方创建一个终端
     */
    private void showTerminal() {

    }
}

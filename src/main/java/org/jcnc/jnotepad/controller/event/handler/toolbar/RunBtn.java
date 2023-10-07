package org.jcnc.jnotepad.controller.event.handler.toolbar;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.views.manager.BuildPanelManager;

/**
 * 终端处理器
 *
 *
 * @author cccqyu
 */
public class RunBtn implements EventHandler<ActionEvent> {

    private static final BuildPanelManager BUILD_PANEL_MANAGER = BuildPanelManager.getInstance();

    @Override
    public void handle(ActionEvent event) {
        BUILD_PANEL_MANAGER.controlShow();
    }
}

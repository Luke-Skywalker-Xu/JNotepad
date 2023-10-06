package org.jcnc.jnotepad.component.module.vbox;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.jcnc.jnotepad.component.module.vbox.components.CmdTerminalBox;
import org.jcnc.jnotepad.component.module.vbox.components.DebugBox;
import org.jcnc.jnotepad.component.module.vbox.components.RunBox;

import static org.jcnc.jnotepad.common.constants.TextConstants.*;

/**
 * 底部Run,Debug,Cmd面板
 *
 *
 * @author cccqyu
 */
public class BuildPanel extends TabPane {

    private static BuildPanel instance = null;

    public static BuildPanel getInstance() {

        if (instance == null) {
            instance = new BuildPanel();
        }
        return instance;
    }

    private final CmdTerminalBox cmdTerminalBox;
    private final RunBox runBox;
    private final DebugBox debugBox;

    private BuildPanel() {
        cmdTerminalBox = new CmdTerminalBox();
        runBox = new RunBox();
        debugBox = new DebugBox();

        Tab runTab = new Tab(RUN,runBox);
        runTab.setClosable(false);

        Tab buildTab = new Tab(BUILD, debugBox);
        buildTab.setClosable(false);

        Tab cmdTab = new Tab(TERMINAL,cmdTerminalBox);
        cmdTab.setClosable(false);
        this.getTabs().addAll(runTab,buildTab,cmdTab);
    }

    public CmdTerminalBox getCmdTerminalBox() {
        return cmdTerminalBox;
    }

    public RunBox getRunBox() {
        return runBox;
    }

    public DebugBox getBuildBox() {
        return debugBox;
    }
}

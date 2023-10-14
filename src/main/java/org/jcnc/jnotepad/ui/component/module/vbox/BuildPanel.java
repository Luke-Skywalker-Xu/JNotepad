package org.jcnc.jnotepad.ui.component.module.vbox;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.jcnc.jnotepad.ui.component.module.vbox.components.CmdTerminalBox;
import org.jcnc.jnotepad.ui.component.module.vbox.components.DebugBox;
import org.jcnc.jnotepad.ui.component.module.vbox.components.RunBox;

/**
 * 底部运行、调试和命令终端面板。
 *
 * <p>这个类实现了一个包含运行信息、调试信息和命令终端的底部面板。它是TabPane的子类，用于将这三个组件以选项卡的形式显示在底部面板上。</p>
 *
 * <p>可以通过调用getInstance方法获取单例实例。</p>
 *
 * @author cccqyu
 * @see CmdTerminalBox
 * @see RunBox
 * @see DebugBox
 * @see TabPane
 */
public class BuildPanel extends TabPane {

    private static BuildPanel instance = null;
    private final CmdTerminalBox cmdTerminalBox;
    private final RunBox runBox;
    private final DebugBox debugBox;
    private BuildPanel() {
        cmdTerminalBox = new CmdTerminalBox();
        runBox = new RunBox();
        debugBox = new DebugBox();

        Tab runTab = new Tab("运行", runBox);
        runTab.setClosable(false);

        Tab buildTab = new Tab("调试", debugBox);
        buildTab.setClosable(false);

        Tab cmdTab = new Tab("命令终端", cmdTerminalBox);
        cmdTab.setClosable(false);
        this.getTabs().addAll(runTab, buildTab, cmdTab);
    }

    /**
     * 获取BuildPanel的单例实例。
     *
     * @return BuildPanel的单例实例
     */
    public static BuildPanel getInstance() {

        if (instance == null) {
            instance = new BuildPanel();
        }
        return instance;
    }

    /**
     * 获取命令终端组件。
     *
     * @return CmdTerminalBox对象
     */
    public CmdTerminalBox getCmdTerminalBox() {
        return cmdTerminalBox;
    }

    /**
     * 获取运行信息组件。
     *
     * @return RunBox对象
     */
    public RunBox getRunBox() {
        return runBox;
    }

    /**
     * 获取调试信息组件。
     *
     * @return DebugBox对象
     */
    public DebugBox getDebugBox() {
        return debugBox;
    }
}

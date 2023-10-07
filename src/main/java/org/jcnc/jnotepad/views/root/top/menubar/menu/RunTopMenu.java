package org.jcnc.jnotepad.views.root.top.menubar.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.jcnc.jnotepad.api.core.views.top.menu.AbstractTopMenu;
import org.jcnc.jnotepad.component.module.vbox.BuildPanel;
import org.jcnc.jnotepad.util.LogUtil;
import org.jcnc.jnotepad.views.manager.BuildPanelManager;
import org.jcnc.jnotepad.views.manager.CenterTabPaneManager;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTab;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.jcnc.jnotepad.common.constants.TextConstants.DE_BUG;
import static org.jcnc.jnotepad.common.constants.TextConstants.RUN;

/**
 * 文件菜单
 *
 * @author gewuyou
 */
public class RunTopMenu extends AbstractTopMenu {
    private static final BuildPanelManager BUILD_PANEL_MANAGER = BuildPanelManager.getInstance();
    private static final BuildPanel BUILD_PANEL = BuildPanel.getInstance();
    private static final RunTopMenu INSTANCE = new RunTopMenu();
    private final Map<String, MenuItem> runMenuItems = new HashMap<>();

    public static RunTopMenu getInstance() {
        return INSTANCE;
    }

    /**
     * 获取菜单名称
     *
     * @return 菜单名称
     */
    @Override
    public String getMenuName() {
        return RUN;
    }

    /**
     * 获取菜单
     *
     * @return 菜单
     */
    @Override
    public Menu getMenu() {
        return topMenuBar.getRunMenu();
    }

    /**
     * 获取菜单项集合
     *
     * @return 菜单项集合
     */
    @Override
    public Map<String, MenuItem> getMenuItems() {
        return runMenuItems;
    }

    EventHandler<ActionEvent> codeRun = event -> {

        // 获取TextCodeArea的文本内容
        CenterTab centerTab = CenterTabPaneManager.getInstance().getSelected();

        String code = centerTab.getLineNumberTextArea().getText();

        String fileName = centerTab.getText();

        // 将C代码写入临时文件
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.write(code);
        } catch (IOException ex) {
            LogUtil.getLogger(this.getClass()).info("正在写入：{}", code);
        }

        // 编译C代码
        compileAndRunCode(fileName);
    };

    /**
     * 编译和运行C代码的方法
     */
    private void compileAndRunCode(String fileName) {
        try {
            CenterTab centerTab = CenterTabPaneManager.getInstance().getSelected();
            // 创建ProcessBuilder并指定GCC编译命令
            ProcessBuilder processBuilder = new ProcessBuilder("gcc", fileName, "-o", centerTab.getText());

            // 设置工作目录
            processBuilder.directory(null);

            // 启动编译器进程
            Process compileProcess = processBuilder.start();

            // 读取编译器的输出信息
            BufferedReader compileReader = new BufferedReader(new InputStreamReader(compileProcess.getInputStream()));
            String line;
            while ((line = compileReader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待编译完成并获取返回值
            int compileExitCode = compileProcess.waitFor();
            if (compileExitCode == 0) {
                System.out.println("编译成功！");

                // 运行编译后的可执行文件
                Process runProcess = new ProcessBuilder("./" + centerTab.getText()).start();

                // 读取运行结果
                BufferedReader runReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                StringBuilder result = new StringBuilder();
                while ((line = runReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
                // 显示运行结果
                BUILD_PANEL_MANAGER.controlShow(true);
                BUILD_PANEL_MANAGER.setText(BUILD_PANEL.getRunBox(), result.toString());
            } else {
                System.out.println("编译失败，返回代码：" + compileExitCode);
            }
        } catch (IOException | InterruptedException e) {
            LogUtil.getLogger(this.getClass()).info("编译失败：{}", fileName);
        }
    }

    /**
     * 注册顶部菜单
     */
    @Override
    protected void registerTopMenu() {

        // 运行
        registerMenuItem(topMenuBar.getRunItem(), RUN, "runItem", codeRun);


        // 调试 test
        registerMenuItem(topMenuBar.getDeBugItem(), DE_BUG, "deBugItem", event -> {
            BUILD_PANEL_MANAGER.controlShow(true);
            BUILD_PANEL_MANAGER.setText(BUILD_PANEL.getBuildBox(), "待开发");
        });


    }
}

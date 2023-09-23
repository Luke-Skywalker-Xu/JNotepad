package org.jcnc.jnotepad.app.manager;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.common.constants.AppConstants;
import org.jcnc.jnotepad.common.constants.TextConstants;
import org.jcnc.jnotepad.common.manager.ThreadPoolManager;
import org.jcnc.jnotepad.controller.ResourceController;
import org.jcnc.jnotepad.controller.config.PluginConfigController;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.plugin.PluginManager;
import org.jcnc.jnotepad.util.UiUtil;
import org.jcnc.jnotepad.views.manager.RootManager;
import org.jcnc.jnotepad.views.manager.SidebarToolBarManager;
import org.jcnc.jnotepad.views.manager.TopMenuBarManager;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;


/**
 * 应用程序管理类<br/>
 * 此类负责管理应用程序的生命周期等操作
 *
 * @author gewuyou
 */
public class ApplicationManager {
    private static final ApplicationManager INSTANCE = new ApplicationManager();
    /**
     * 线程池
     */
    private final ExecutorService threadPool = ThreadPoolManager.getThreadPool();
    private Pane root = new Pane();
    private Scene scene;
    private Stage primaryStage;
    private Application application;


    private ApplicationManager() {
    }

    public static ApplicationManager getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化程序
     *
     * @apiNote
     * @since 2023/9/20 17:26
     */
    public void initializeApp() {
        // 设置应用程序主题
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        // 初始化scene
        initScene();
        // 初始化插件
        PluginManager.getInstance().initializePlugins();
        // 初始化顶部菜单栏
        TopMenuBarManager.getInstance().initTopMenuBar();
        // 初始化侧边工具栏
        SidebarToolBarManager.getInstance().initSidebarToolBar();
        // 初始化ui组件
        initUiComponents();
        // 初始化primaryStage
        initPrimaryStage();
    }

    public void initializeDefaultAction() {
        // 使用线程池加载关联文件并创建文本区域
        List<String> rawParameters = application.getParameters().getRaw();
        Controller.getInstance().openAssociatedFileAndCreateTextArea(rawParameters);
    }

    private void initScene() {
        // 初始化scene
        double width = AppConstants.SCREEN_WIDTH;
        double length = AppConstants.SCREEN_LENGTH;
        scene = new Scene(root, width, length);
        scene.getStylesheets().add(Objects.requireNonNull(application.getClass().getResource("/css/styles.css")).toExternalForm());
    }

    private void initPrimaryStage() {
        primaryStage.setScene(scene);
        primaryStage.setWidth(scene.getWidth());
        primaryStage.setHeight(scene.getHeight());
        primaryStage.getIcons().add(UiUtil.getAppIcon());
    }

    /**
     * 加载资源
     *
     * @apiNote
     * @since 2023/9/20 18:29
     */
    public void loadAppResources() {
        // 加载资源
        ResourceController.getInstance().loadResources();
        // 绑定快捷键
        UiResourceBundle.bindStringProperty(primaryStage.titleProperty(), TextConstants.TITLE);
    }

    /**
     * 停止程序
     *
     * @apiNote 在停止程序之前会执行此操作
     */
    public void stopApp() {
        PluginConfigController instance = PluginConfigController.getInstance();
        // 刷新插件配置文件
        instance.getConfig().setPlugins(PluginManager.getInstance().getPluginInfos());
        instance.writeConfig();
        // 销毁插件可能申请的资源
        PluginManager.getInstance().destroyPlugins();
        // 关闭线程池
        threadPool.shutdownNow();
    }

    /**
     * 获取当前窗口。
     *
     * @return 当前窗口
     */
    public Window getWindow() {
        return scene.getWindow();
    }

    /**
     * 获取当前窗口的场景
     *
     * @return javafx.scene.Scene
     * @since 2023/9/20 18:21
     */
    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * 加载ui组件
     *
     * @apiNote
     * @since 2023/9/20 17:25
     */
    public void initUiComponents() {
        // 加载组件
        RootManager rootManager = RootManager.getInstance(scene);
        rootManager.initScreen(scene);
    }

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}

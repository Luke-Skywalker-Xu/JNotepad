package org.jcnc.jnotepad.app.manager;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.common.constants.AppConstants;
import org.jcnc.jnotepad.common.constants.TextConstants;
import org.jcnc.jnotepad.common.manager.ThreadPoolManager;
import org.jcnc.jnotepad.common.util.UiUtil;
import org.jcnc.jnotepad.controller.ResourceController;
import org.jcnc.jnotepad.controller.cache.CacheController;
import org.jcnc.jnotepad.controller.config.AppConfigController;
import org.jcnc.jnotepad.controller.config.PluginConfigController;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.plugin.manager.PluginManager;
import org.jcnc.jnotepad.views.manager.*;

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
     * 初始化程序(Initializes the application)
     *
     * @apiNote
     * @since 2023/9/20 17:26
     */
    public void initializeApp() {
        // 设置应用程序主题 SetTheApplicationTheme
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        // 初始化scene
        initScene();
        // 初始化插件
        PluginManager.getInstance().initPlugins();
        // 初始化顶部菜单栏
        TopMenuBarManager.getInstance().initTopMenuBar();
        // 初始化侧边工具栏
        SidebarToolBarManager.getInstance().initSidebarToolBar();
        // 初始化下方状态栏
        BottomStatusBoxManager.getInstance().initStatusBox();
        // 初始标签页布局组件
        CenterTabPaneManager.getInstance().initCenterTabPane();
        // 初始化应用布局
        initAppLayout();
        // 初始化primaryStage
        initPrimaryStage();
    }

    public void initializeDefaultAction() {
        // 使用加载关联文件并创建文本区域
        List<String> rawParameters = application.getParameters().getRaw();
        Controller.getInstance().openAssociatedFileAndCreateTextArea(rawParameters);
        // 加载已打开的文件夹
        DirectorySidebarManager.getInstance().expandTheOpenFileTree();
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
        primaryStage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                CenterTabPaneManager instance = CenterTabPaneManager.getInstance();
                instance.checkFileTabStatus(instance.getSelected());
            }
        });
    }

    /**
     * 加载缓存
     */
    public void loadAppCache() {
        // 加载缓存
        CacheController.getInstance().loadCaches();
    }

    /**
     * 加载资源
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
        PluginConfigController pluginConfigController = PluginConfigController.getInstance();
        // 刷新插件配置文件
        pluginConfigController.getConfig().setPlugins(PluginManager.getInstance().getPluginDescriptors());
        pluginConfigController.writeConfig();
        // 保存配置文件
        AppConfigController.getInstance().writeConfig();
        // 销毁插件可能申请的资源
        PluginManager.getInstance().destroyPlugins();
        // 保存已打开的文件标签页
        CenterTabPaneManager.getInstance().saveOpenFileTabs();
        // 将缓存写入本地
        CacheController.getInstance().writeCaches();
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
     * 加载程序布局
     *
     * @apiNote
     * @since 2023/9/20 17:25
     */
    public void initAppLayout() {
        // 加载根布局容器
        RootManager rootManager = RootManager.getInstance(scene);
        rootManager.initScreen(scene);
        // 初始化底部根侧边栏垂直布局
        RootBottomSideBarVerticalBoxManager.getInstance().initSidebarVerticalBox();
        // 初始化主界面边界布局
        MainBorderPaneManager.getInstance().initMainBorderPane();
        // 初始化顶部边界面板
        RootTopBorderPaneManager.getInstance().initRootBorderPane();
        // 初始化右侧边栏垂直布局
        RootRightSideBarVerticalBoxManager.getInstance().initRootRightSideBarVerticalBox();
        // 初始化根布局
        RootBorderPaneManager.getInstance().initRootBorderPane();

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

    public void stopApplication() {
        Platform.exit();
    }
}

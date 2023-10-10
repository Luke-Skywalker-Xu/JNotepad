package org.jcnc.jnotepad.app.manager;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jcnc.jnotepad.JnotepadApp;
import org.jcnc.jnotepad.app.common.constants.AppConstants;
import org.jcnc.jnotepad.app.common.constants.TextConstants;
import org.jcnc.jnotepad.app.common.manager.ThreadPoolManager;
import org.jcnc.jnotepad.app.config.AppConfig;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.app.utils.FileUtil;
import org.jcnc.jnotepad.app.utils.LogUtil;
import org.jcnc.jnotepad.app.utils.UiUtil;
import org.jcnc.jnotepad.controller.ResourceController;
import org.jcnc.jnotepad.controller.cache.CacheController;
import org.jcnc.jnotepad.controller.config.AppConfigController;
import org.jcnc.jnotepad.controller.config.PluginConfigController;
import org.jcnc.jnotepad.controller.exception.AppException;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.controller.plugin.manager.PluginManager;
import org.jcnc.jnotepad.ui.views.manager.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import static org.jcnc.jnotepad.app.common.constants.AppConstants.DEFAULT_PROPERTY;
import static org.jcnc.jnotepad.app.common.constants.AppConstants.PROGRAM_FILE_DIRECTORY;

/**
 * 应用程序管理类
 *
 * <p>
 * 此类负责管理应用程序的生命周期和操作。它包括初始化应用程序、执行默认操作、加载缓存、加载资源、迁移程序根文件夹、停止前操作等功能。
 * </p>
 *
 * <p>
 * 该类是一个单例类，通过getInstance方法获取实例。
 * </p>
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

    /**
     * 获取ApplicationManager的单例实例
     *
     * @return ApplicationManager的单例实例
     */
    public static ApplicationManager getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化应用程序
     *
     * <p>
     * 此方法用于初始化应用程序的各个组件，包括设置应用程序主题、初始化UI组件、初始化插件、初始化顶部菜单栏、初始化侧边工具栏、初始化下方状态栏、初始化标签页布局等。
     * </p>
     */
    public void initializeApp() {
        // 设置应用程序主题
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

    /**
     * 执行默认操作
     *
     * <p>
     * 此方法用于执行应用程序的默认操作，例如根据参数打开关联文件并创建文本区域，加载已打开的文件夹等。
     * </p>
     */
    public void executeDefaultAction() {
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
        scene.getStylesheets().add(Objects.requireNonNull(application.getClass().getResource("/jcnc/app/css/styles.css")).toExternalForm());
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
     *
     * <p>
     * 此方法用于加载应用程序的缓存。
     * </p>
     */
    public void loadAppCache() {
        // 加载缓存
        CacheController.getInstance().loadCaches();
    }

    /**
     * 加载资源
     *
     * <p>
     * 此方法用于加载应用程序的资源，包括加载资源文件和绑定快捷键。
     * </p>
     */
    public void loadAppResources() {
        // 加载资源
        ResourceController.getInstance().loadResources();
        // 绑定快捷键
        UiResourceBundle.bindStringProperty(primaryStage.titleProperty(), TextConstants.TITLE);
    }

    /**
     * 迁移程序根文件夹
     *
     * <p>
     * 此方法用于迁移应用程序的根文件夹，将根文件夹从之前的位置迁移到新的位置。
     * </p>
     */
    public void migrateFileRootFolder() {
        AppConfig config = AppConfigController.getInstance().getConfig();
        String lastRootPath = config.getLastRootPath();
        if (lastRootPath == null) {
            return;
        }
        // 获取源文件夹
        File sourceFolder = new File(lastRootPath, PROGRAM_FILE_DIRECTORY);
        // 获取目标文件夹
        File targetFolder = new File(config.getRootPath(), PROGRAM_FILE_DIRECTORY);
        // 设置忽略文件夹
        Set<File> ignoredFolders = config.getIgnoreFolder();
        // 设置忽略文件
        Set<File> ignoredFiles = config.getIgnoreFile();
        // 移动文件夹
        FileUtil.migrateFolder(sourceFolder, targetFolder, ignoredFolders, ignoredFiles);
        // 删除.jnotepad
        if (!sourceFolder.equals(new File(Paths.get(System.getProperty(DEFAULT_PROPERTY), PROGRAM_FILE_DIRECTORY).toString()))) {
            try {
                Files.delete(sourceFolder.toPath());
            } catch (IOException e) {
                throw new AppException(e);
            }
        }
        // 保存新配置
        AppConfigController.getInstance().writeConfig();
    }

    /**
     * 停止前操作
     *
     * <p>
     * 在停止应用程序之前，执行一系列操作，包括刷新插件配置、销毁插件、保存已打开的文件标签页、将缓存写入本地、迁移程序根文件夹、关闭线程池等。
     * </p>
     */
    public void operationBeforeStopping() {
        PluginConfigController pluginConfigController = PluginConfigController.getInstance();
        // 刷新插件配置文件
        pluginConfigController.getConfig().setPlugins(PluginManager.getInstance().getPluginDescriptors());
        pluginConfigController.writeConfig();

        // 销毁插件可能申请的资源
        PluginManager.getInstance().destroyPlugins();
        // 保存已打开的文件标签页
        CenterTabPaneManager.getInstance().saveOpenFileTabs();
        // 将缓存写入本地
        CacheController.getInstance().writeCaches();
        // 迁移文件夹
        migrateFileRootFolder();
        // 关闭线程池
        threadPool.shutdownNow();
    }

    /**
     * 获取当前窗口
     *
     * @return 当前窗口
     */
    public Window getWindow() {
        return scene.getWindow();
    }

    /**
     * 获取当前窗口的场景
     *
     * @return 当前窗口的场景
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
     * <p>
     * 此方法用于加载应用程序的布局，包括根布局容器、底部根侧边栏垂直布局、主界面边界布局、顶部边界面板、右侧边栏垂直布局、根布局等组件。
     * </p>
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

    /**
     * 重启应用程序
     *
     * <p>
     * 此方法用于重启当前的Java应用程序。
     * </p>
     */
    public void restart() {
        try {
            // 获取当前Java应用程序的命令
            String javaCommand = System.getProperty("java.home") + "/bin/java";
            String mainClass = JnotepadApp.class.getName();

            // 构建新进程来重新启动应用程序
            ProcessBuilder builder = new ProcessBuilder(javaCommand, "-cp", System.getProperty("java.class.path"), mainClass);
            builder.start();
            // 关闭当前应用程序
            stop();
        } catch (IOException e) {
            LogUtil.getLogger("正在重启当前应用程序".getClass());
        }
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

    /**
     * 停止应用程序
     *
     * <p>
     * 此方法用于停止应用程序。
     * </p>
     */
    public void stop() {
        Platform.exit();
    }
}

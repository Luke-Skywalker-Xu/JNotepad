package org.jcnc.jnotepad;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


/**
 *  视图管理类
 */
public class ViewManager {

	public static Label encodingLabel; // 新增属性用于显示文本编码

	public static int tabIndex = 0;

	// 定义菜单栏
	public static MenuBar menuBar;
	public static Menu fileMenu;
	public static MenuItem newItem, openItem, saveItem, saveAsItem;

	// 定义主界面
	public static BorderPane root;

	// 定义多个Tab页
	public static TabPane tabPane;

	// 定义状态栏
	public static Label statusLabel;

	private static ViewManager instance = null;


	public static ViewManager getInstance(Scene scene) {
		if(instance == null) {
			instance = new ViewManager(scene);
		}

		return instance;
	}

	/**
	 * 构造函数，设置场景和根布局
	 * @param scene 场景
	 */
	private ViewManager(Scene scene){

		root = new BorderPane();

		scene.setRoot(root);
	}

	public void initScreen(Scene scene){
		// 创建菜单栏并添加菜单项
		menuBar = new MenuBar();
		fileMenu = new Menu("文件");
		newItem = new MenuItem("新建");
		openItem = new MenuItem("打开");
		saveItem = new MenuItem("保存");
		saveAsItem = new MenuItem("另存为");
		fileMenu.getItems().addAll(newItem, openItem, saveItem, saveAsItem);
		menuBar.getMenus().add(fileMenu);

		// 创建主界面
		root = new BorderPane();
		root.setTop(menuBar);

		// 创建Tab页和文本编辑区
		tabPane = new TabPane();
		root.setCenter(tabPane);

		// 创建状态栏
		statusLabel = new Label("行: 1 \t列: 1 \t字数: 0 ");


		encodingLabel = new Label(); // 创建新的标签用于显示编码信息
		HBox statusBox = new HBox(statusLabel, encodingLabel); // 使用 HBox 放置状态标签和编码标签
		root.setBottom(statusBox);
		BorderPane.setMargin(statusBox, new Insets(5, 10, 5, 10));

		scene.setRoot(root);


	}



	

}

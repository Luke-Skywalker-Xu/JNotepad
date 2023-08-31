package org.jcnc.jnotepad.root;

import org.jcnc.jnotepad.root.bottom.RootBottomSideBarVBox;
import org.jcnc.jnotepad.root.center.main.MainBorderPane;
import org.jcnc.jnotepad.root.left.sidebar.tools.ToolHBox;
import org.jcnc.jnotepad.root.right.RootRightSideBarVBox;
import org.jcnc.jnotepad.root.top.RootTopBorderPane;
import org.jcnc.jnotepad.ui.module.AbstractBorderPane;

/*
 * 根舞台下的Root主布局
 * */
public class RootBorderPane extends AbstractBorderPane {

    private static final RootBorderPane INSTANCE = new RootBorderPane();

    private RootBorderPane() {
        initRootBorderPane();
    }

    private void initRootBorderPane() {
        setCenterComponent(MainBorderPane.getInstance());       //中间,用于显示Main主布局
        setLeftComponent(ToolHBox.getInstance());        //主布局的左边
        setRightComponent(RootRightSideBarVBox.getInstance());  //主布局的右边
        setTopComponent(RootTopBorderPane.getInstance());       //主布局的上面
        setBottomComponent(RootBottomSideBarVBox.getInstance());//主布局的下面
    }

    public static RootBorderPane getInstance() {
        return INSTANCE;
    }
}



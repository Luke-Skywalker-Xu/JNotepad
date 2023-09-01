package org.jcnc.jnotepad.root;

import org.jcnc.jnotepad.root.bottom.RootBottomSideBarVBox;
import org.jcnc.jnotepad.root.center.main.MainBorderPane;
import org.jcnc.jnotepad.root.left.sidebar.tools.ToolHBox;
import org.jcnc.jnotepad.root.right.RootRightSideBarVBox;
import org.jcnc.jnotepad.root.top.RootTopBorderPane;
import org.jcnc.jnotepad.ui.module.AbstractBorderPane;

/**
 * 根舞台下的Root主布局
 *
 * @author 许轲
 */
public class RootBorderPane extends AbstractBorderPane {

    private static final RootBorderPane INSTANCE = new RootBorderPane();

    private RootBorderPane() {
        initRootBorderPane();
    }

    public static RootBorderPane getInstance() {
        return INSTANCE;
    }

    private void initRootBorderPane() {
        //中间,用于显示Main主布局
        setCenterComponent(MainBorderPane.getInstance());
        //主布局的左边
        setLeftComponent(ToolHBox.getInstance());
        //主布局的右边
        setRightComponent(RootRightSideBarVBox.getInstance());
        //主布局的上面
        setTopComponent(RootTopBorderPane.getInstance());
        //主布局的下面
        setBottomComponent(RootBottomSideBarVBox.getInstance());
    }
}



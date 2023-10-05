package org.jcnc.jnotepad.model.entity;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * 默认上下文菜单
 *
 * @author gewuyou
 */
public class DefaultContextMenu extends ContextMenu {
    private final MenuItem fold;
    private final MenuItem unfold;
    private final MenuItem print;

    public DefaultContextMenu() {
        fold = new MenuItem("折叠所选文本");
        fold.setOnAction(aE -> {
            hide();
            fold();
        });

        unfold = new MenuItem("从光标处展开");
        unfold.setOnAction(aE -> {
            hide();
            unfold();
        });

        print = new MenuItem("打印");
        print.setOnAction(aE -> {
            hide();
            print();
        });

        getItems().addAll(fold, unfold, print);
    }

    /**
     * 折叠多行所选文本，仅显示第一行并隐藏其余部分。
     */
    private void fold() {
        ((org.fxmisc.richtext.CodeArea) getOwnerNode()).foldSelectedParagraphs();
    }

    /**
     * 展开当前行/段落（如果有折叠）。
     */
    private void unfold() {
        org.fxmisc.richtext.CodeArea area = (org.fxmisc.richtext.CodeArea) getOwnerNode();
        area.unfoldParagraphs(area.getCurrentParagraph());
    }

    private void print() {
        System.out.println(((org.fxmisc.richtext.CodeArea) getOwnerNode()).getText());
    }
}

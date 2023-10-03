package org.jcnc.jnotepad.model.entity;

import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

/**
 * @author : cccqyu
 * @createTime 2023/10/2  20:31
 * @description 文件夹实体类
 */
public class DirFileModel {
    private String path;
    private String name;

    private FontIcon iconIsNotSelected;

    private FontIcon iconIsSelected;

    private List<DirFileModel> childFile;


    public DirFileModel(String path, String name, List<DirFileModel> childFile, FontIcon iconIsNotSelected, FontIcon iconIsSelected) {
        this.path = path;
        this.name = name;
        this.childFile = childFile;
        this.iconIsNotSelected = iconIsNotSelected;
        this.iconIsSelected = iconIsSelected;
    }


    public List<DirFileModel> getChildFile() {
        return childFile;
    }

    public void setChildFile(List<DirFileModel> childFile) {
        this.childFile = childFile;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public FontIcon getIconIsNotSelected() {
        return iconIsNotSelected;
    }

    public void setIconIsNotSelected(FontIcon iconIsNotSelected) {
        this.iconIsNotSelected = iconIsNotSelected;
    }

    public FontIcon getIconIsSelected() {
        return iconIsSelected;
    }

    public void setIconIsSelected(FontIcon iconIsSelected) {
        this.iconIsSelected = iconIsSelected;
    }
}
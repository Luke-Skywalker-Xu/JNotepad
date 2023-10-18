package org.jcnc.jnotepad.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.Node;

import java.io.File;
import java.util.List;


/**
 * 文件夹实体类
 *
 * <p>用于存储文件夹结构</p>
 *
 * @author cccqyu
 */
public class DirFileModel {
    /**
     * 路径
     */
    private String path;

    /**
     * 文件名
     */
    private String name;

    /**
     * 未选中时的图标
     */
    @JsonIgnore
    private Node iconIsNotSelected;
    /**
     * 选中时的图标
     */
    @JsonIgnore
    private Node iconIsSelected;
    /**
     * 子文件
     */
    private List<DirFileModel> childFile;

    /**
     * 是否打开
     */
    private boolean isOpen;

    public DirFileModel(String path, String name, List<DirFileModel> childFile, boolean isOpen) {
        this.path = path;
        this.name = name;
        this.childFile = childFile;
        this.isOpen = isOpen;
    }

    public DirFileModel(String path, String name, List<DirFileModel> childFile, Node iconIsNotSelected, Node iconIsSelected) {
        this.path = path;
        this.name = name;
        this.childFile = childFile;
        this.iconIsNotSelected = iconIsNotSelected;
        this.iconIsSelected = iconIsSelected;
        this.isOpen = false;
    }

    public DirFileModel(String path, String name, List<DirFileModel> childFile, Node iconIsNotSelected, Node iconIsSelected, boolean isOpen) {
        this.path = path;
        this.name = name;
        this.childFile = childFile;
        this.iconIsNotSelected = iconIsNotSelected;
        this.iconIsSelected = iconIsSelected;
        this.isOpen = isOpen;
    }

    public DirFileModel() {
    }

    /**
     * Check if the given `DirFileModel` represents a directory.
     *
     * @param childFile the `DirFileModel` to check
     * @return `true` if the `childFile` represents a directory, `false` otherwise
     */
    public static boolean isDirectoryByDirFileModel(DirFileModel childFile) {
        return new File(childFile.getPath()).isDirectory();
    }

    public boolean isOpen() {
        return isOpen;
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

    public Node getIconIsNotSelected() {
        return iconIsNotSelected;
    }

    public void setIconIsNotSelected(Node iconIsNotSelected) {
        this.iconIsNotSelected = iconIsNotSelected;
    }

    public Node getIconIsSelected() {
        return iconIsSelected;
    }

    public void setIconIsSelected(Node iconIsSelected) {
        this.iconIsSelected = iconIsSelected;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

}
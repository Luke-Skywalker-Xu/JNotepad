package org.jcnc.jnotepad.model.entity;

import java.util.List;

/**
 * @author : cccqyu
 * @createTime 2023/10/2  20:31
 * @description 文件夹实体类
 */
public class DirFileModel {
    private String path;
    private String name;

    private List<DirFileModel> childFile;

    public DirFileModel(String path, String name, List<DirFileModel> childFile) {
        this.path = path;
        this.name = name;
        this.childFile = childFile;
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
}
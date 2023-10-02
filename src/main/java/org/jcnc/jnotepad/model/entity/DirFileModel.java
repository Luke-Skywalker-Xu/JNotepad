package org.jcnc.jnotepad.model.entity;

import java.util.List;


/**
 * 文件夹实体类
 *
 * <p>用于存储文件夹结构</p>
 *
 * @author cccqyu
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
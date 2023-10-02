package org.jcnc.jnotepad.util;

import org.jcnc.jnotepad.model.entity.DirFileModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : cccqyu
 * @createTime 2023/10/2  2:31
 * @description 文件工具类
 */
public class FileUtil {

    /**
     * 将文件夹转为DirFileModel
     *
     * @param file 文件
     * @return DirFileModel 存储文件夹与文件关系的实体类
     */
    public static DirFileModel getDirFileModel(File file) {
        if (!file.exists()) {
            return null;
        }

        DirFileModel dirFileModel = new DirFileModel(file.getAbsolutePath(), file.getName(), new ArrayList<>());

        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    DirFileModel childDirFileModel = getDirFileModel(f);
                    dirFileModel.getChildFile().add(childDirFileModel);
                } else {
                    dirFileModel.getChildFile().add(new DirFileModel(f.getAbsolutePath(), f.getName(), null));
                }
            }
        }

        return dirFileModel;
    }
}

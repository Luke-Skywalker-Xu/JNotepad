package org.jcnc.jnotepad.util;

import javafx.scene.Node;
import org.jcnc.jnotepad.controller.event.handler.menuitem.OpenFile;
import org.jcnc.jnotepad.controller.exception.AppException;
import org.jcnc.jnotepad.model.entity.DirFileModel;
import org.kordamp.ikonli.javafx.FontIcon;

import java.awt.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Set;

import static org.kordamp.ikonli.antdesignicons.AntDesignIconsFilled.*;

/**
 * 文件工具
 *
 * @author gewuyou
 */
public class FileUtil {
    private static final MessageDigest MESSAGE_DIGEST_SHA_256;
    private static final int BUFFER_SIZE = 8192;

    static {
        try {
            MESSAGE_DIGEST_SHA_256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new AppException(e);
        }
    }

    private FileUtil() {

    }

    /**
     * 将字节数组转换为String类型哈希值
     *
     * @param bytes 字节数组
     * @return 哈希值
     */
    private static String bytes2HashCode(byte[] bytes) {
        StringBuilder hashString = new StringBuilder();
        for (byte b : bytes) {
            hashString.append(String.format("%02x", b));
        }
        return hashString.toString();
    }


    /**
     * 获取本地文件Sha256哈希值字符串
     *
     * @param file 本地文件
     * @return 本地文件Sha256哈希值
     */
    public static String getLocalFileSha256HashString(File file) {
        try (
                // 获取文件输入流
                FileInputStream fileInputStream = new FileInputStream(file);
                // 获取字节流通道
                FileChannel channel = fileInputStream.getChannel()
        ) {
            // 设置8k缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            while (channel.read(buffer) != -1) {
                buffer.flip();
                MESSAGE_DIGEST_SHA_256.update(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            throw new AppException(e);
        }
        return bytes2HashCode(MESSAGE_DIGEST_SHA_256.digest());
    }

    /**
     * 获取本地文件Sha256哈希值字符串
     *
     * @param pathStr 本地文件路径字符串
     * @return 本地文件Sha256哈希值
     */
    public static String getLocalFileSha256HashString(String pathStr) {
        return getLocalFileSha256HashString(new File(pathStr));
    }

    /**
     * 获取本地文件Sha256哈希值字符串
     *
     * @param path 本地文件路径
     * @return 本地文件Sha256哈希值
     */
    public static String getLocalFileSha256HashString(Path path) {
        return getLocalFileSha256HashString(path.toFile());
    }

    /**
     * 获取文件中的文本内容。
     *
     * @param file 文件对象
     * @return 文本内容
     */
    public static String getFileText(File file) {
        return getFileText(file, EncodingDetector.detectEncodingCharset(file));
    }


    /**
     * 获取文件中的文本内容。
     *
     * @param file     文件对象
     * @param encoding 文件编码
     * @return 文本内容
     */
    public static String getFileText(File file, Charset encoding) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file, encoding))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append("\n");
                }
                stringBuilder.append(line);
            }
        } catch (IOException ignored) {
            LogUtil.getLogger(OpenFile.class).info("已忽视IO异常!");
        }
        return stringBuilder.toString();
    }

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

        DirFileModel dirFileModel = new DirFileModel(file.getAbsolutePath(), file.getName(), new ArrayList<>(), new FontIcon(FOLDER), new FontIcon(FOLDER_OPEN));

        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    DirFileModel childDirFileModel = getDirFileModel(f);
                    dirFileModel.getChildFile().add(childDirFileModel);
                } else {
                    // todo 在此监测文件后缀，设置对应的图标
                    dirFileModel.getChildFile().add(new DirFileModel(f.getAbsolutePath(), f.getName(), null, getIconCorrespondingToFileName(f.getName()), getIconCorrespondingToFileName(f.getName())));
                }
            }
        }
        return dirFileModel;
    }

    /**
     * 文件夹迁移
     *
     * @param sourceFolder 源文件夹
     * @param targetFolder 目标文件夹
     * @since 2023/10/5 12:18
     */

    public static void migrateFolder(File sourceFolder, File targetFolder) {
        // 创建目标文件夹
        targetFolder.mkdirs();

        // 获取源文件夹中的所有文件和文件夹
        File[] files = sourceFolder.listFiles();

        if (files != null) {
            // 遍历源文件夹中的每个文件和文件夹
            for (File file : files) {
                if (file.isDirectory()) {
                    // 如果是文件夹，递归调用自身进行迁移
                    migrateFolder(file, new File(targetFolder, file.getName()));
                } else {
                    // 如果是文件，将文件复制到目标文件夹中
                    Path sourceFilePath = file.toPath();
                    Path targetFilePath = new File(targetFolder, file.getName()).toPath();
                    try {
                        Files.copy(sourceFilePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new AppException(e);
                    }
                }
            }
        }
    }

    /**
     * 迁移文件夹
     *
     * @param sourceFolder   源文件夹
     * @param targetFolder   目标文件夹
     * @param ignoredFolders 忽略的文件夹集合
     * @param ignoredFiles   忽略的文件集合
     * @since 2023/10/5 13:58
     */
    public static void migrateFolder(File sourceFolder, File targetFolder, Set<File> ignoredFolders, Set<File> ignoredFiles) {
        // 创建目标文件夹
        targetFolder.mkdir();
        // 获取源文件夹中的所有文件和文件夹
        File[] files = sourceFolder.listFiles();
        if (files != null) {
            // 遍历源文件夹中的每个文件和文件夹
            for (File file : files) {
                // 如果是文件夹且不是忽略的文件夹，递归调用自身进行迁移
                if (file.isDirectory() && !ignoredFolders.contains(file)) {
                    migrateFolder(targetFolder, ignoredFolders, ignoredFiles, file);
                    continue;
                }
                // 如果是文件且不是忽略的文件，将文件复制到目标文件夹中
                if (!file.isDirectory() && !ignoredFiles.contains(file)) {
                    migrateFile(targetFolder, file);
                }
            }
        }
    }

    /**
     * 迁移文件
     *
     * @param targetFolder 目标文件夹
     * @param file         文件
     */
    public static void migrateFile(File targetFolder, File file) {
        Path sourceFilePath = file.toPath();
        Path targetFilePath = new File(targetFolder, file.getName()).toPath();
        try {
            Files.copy(sourceFilePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new AppException(e);
        }
        // 删除源文件
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    /**
     * 迁移文件夹
     *
     * @param targetFolder   目标文件夹
     * @param ignoredFolders 忽略的文件夹集合
     * @param ignoredFiles   忽略的文件集合
     * @param file           文件
     */
    private static void migrateFolder(File targetFolder, Set<File> ignoredFolders, Set<File> ignoredFiles, File file) {
        migrateFolder(file, new File(targetFolder, file.getName()), ignoredFolders, ignoredFiles);
        // 调用完毕删除当前目录
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    /**
     * Opens the file explorer to the specified file or its parent directory.
     *
     * @param file the file or directory to open in the file explorer
     */
    public static void openExplorer(File file) {
        try { // 判断传入的是文件还是文件夹
            if (file.isDirectory()) {
                Desktop.getDesktop().open(file);
            }
            // 如果是文件则打开所在文件夹
            else {
                Desktop.getDesktop().open(file.getParentFile());
            }
        } catch (IOException e) {
            throw new AppException(e);
        }
    }


    /**
     * Retrieves the icon corresponding to the given file name.
     *
     * @param fileName the file name
     * @return the corresponding icon for the file extension
     */
    public static Node getIconCorrespondingToFileName(String fileName) {
        // todo 在此根据文件缀名获取对应的图标
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return UiUtil.getIconMap().getOrDefault(fileExtension, FontIcon.of(FILE_UNKNOWN));
    }
}


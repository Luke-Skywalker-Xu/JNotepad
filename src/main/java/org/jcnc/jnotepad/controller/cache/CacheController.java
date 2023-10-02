package org.jcnc.jnotepad.controller.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import org.jcnc.jnotepad.common.manager.ApplicationCacheManager;
import org.jcnc.jnotepad.common.util.JsonUtil;
import org.jcnc.jnotepad.common.util.LogUtil;
import org.jcnc.jnotepad.exception.AppException;
import org.jcnc.jnotepad.model.entity.Cache;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static org.jcnc.jnotepad.common.constants.AppConstants.DEFAULT_PROPERTY;

/**
 * 缓存控制器
 *
 * @author gewuyou
 */
public class CacheController {

    private static final ApplicationCacheManager APPLICATION_CACHE_MANAGER = ApplicationCacheManager.getInstance();
    Logger logger = LogUtil.getLogger(this.getClass());

    private static final CacheController INSTANCE = new CacheController();

    private String cacheDir;

    private CacheController() {
        cacheDir = Paths.get(System.getProperty(DEFAULT_PROPERTY), ".jnotepad", "caches").toString();
    }

    public static CacheController getInstance() {
        return INSTANCE;
    }

    public void loadCaches() {
        // 如果本地没有缓存的话，就创建缓存
        if (createCachesIfNotExists()) {
            return;
        }

        // 检查并获取缓存根目录
        File cacheFileDir = createCacheRootIfNotExist();
        // 获取缓存命名空间
        String[] namespaces = cacheFileDir.list();
        // 如果缓存根目录下为空，则创建缓存
        if (Objects.requireNonNull(namespaces).length == 0) {
            APPLICATION_CACHE_MANAGER.setCaches(new HashMap<>(16));
            return;
        }
        Map<String, Cache> caches = new HashMap<>(16);
        for (String namespace : namespaces) {
            // 获取命名空间对应的文件夹
            File namespaceDir = new File(cacheFileDir, namespace);
            // 获取缓存组对应的文件名称列表
            String[] groupNames = namespaceDir.list();
            // 如果命名空间文件夹下没有文件则删除该文件夹
            if (cleanEmptyFileOrFolder(namespaceDir)) {
                continue;
            }
            for (String groupName : Objects.requireNonNull(groupNames)) {
                // 获取缓存组对应的文件
                File groupFile = new File(namespaceDir, groupName);
                // 清理空文件
                if (cleanEmptyFileOrFolder(groupFile)) {
                    continue;
                }
                // 获取缓存
                try {
                    String cacheJson = Files.readString(groupFile.toPath());
                    // 获取缓存集合
                    Map<String, Cache> cacheMap = JsonUtil.fromJsonString(cacheJson, new TypeReference<>() {
                    });
                    // 设置缓存
                    cacheMap.forEach((k, v) -> setUpCache(namespace, groupName, k, v, caches));
                } catch (IOException e) {
                    logger.error("读取缓存文件出错!", e);
                }
            }
            // 设置缓存
            APPLICATION_CACHE_MANAGER.setCaches(caches);
        }

    }

    /**
     * 设置缓存
     *
     * @param namespace 命名空间
     * @param groupName 缓存组
     * @param k         缓存名称
     * @param v         缓存类
     * @param caches    缓存集合
     */
    private void setUpCache(String namespace, String groupName, String k, Cache v, Map<String, Cache> caches) {
        // 判断缓存是否过期,没有过期才加载进内存
        if (v.getLastReadOrWriteTime() + v.getExpirationTime() > System.currentTimeMillis() || v.getExpirationTime() < 0) {
            v.setNamespace(namespace);
            v.setGroup(groupName);
            v.setName(k);
            caches.put(v.getCacheKey(), v);
        }
    }

    /**
     * 清理空文件或空文件夹并返回结果
     *
     * @param fileOrFolder 文件或文件夹
     * @return 是否清理
     */
    private boolean cleanEmptyFileOrFolder(File fileOrFolder) {
        try {
            if (fileOrFolder.isFile() && fileOrFolder.length() == 0) {
                Files.delete(fileOrFolder.toPath());
                logger.info("删除缓存文件:{}", fileOrFolder);
                return true;
            }

            if (fileOrFolder.isDirectory() && Objects.requireNonNull(fileOrFolder.list()).length == 0) {
                Files.delete(fileOrFolder.toPath());
                logger.info("删除缓存文件夹:{}", fileOrFolder);
                return true;
            }
        } catch (IOException e) {
            throw new AppException(e);
        }
        return false;
    }

    /**
     * 写缓存(writeCache)
     */
    public void writeCaches() {
        writeCaches(APPLICATION_CACHE_MANAGER.getCaches());
    }

    /**
     * 写缓存
     *
     * @param caches 缓存集合
     */
    public void writeCaches(Map<String, Cache> caches) {
        // 检查并获取缓存根目录
        File cacheFileDir = createCacheRootIfNotExist();
        Map<File, Map<String, Cache>> fileMap = new HashMap<>(16);
        // 生成缓存
        caches.forEach((key, value) -> {
            // 判断当前命名空间对应目录是否创建
            File namespaceDir = new File(cacheFileDir, value.getNamespace());
            if (!namespaceDir.exists()) {
                namespaceDir.mkdirs();
            }
            // 判断当前组文件是否创建
            File groupFile = new File(namespaceDir, value.getGroup());
            if (!groupFile.exists()) {
                try {
                    boolean flag = groupFile.createNewFile();
                    if (!flag) {
                        throw new AppException("创建文件失败");
                    }
                } catch (IOException e) {
                    throw new AppException(e);
                }
            }
            fileMap.computeIfAbsent(groupFile, k -> new HashMap<>(16));
            // 设置需要写入的数据
            fileMap.get(groupFile).put(value.getName(), value);
        });
        Set<File> fileSet = fileMap.keySet();
        // 清空原来的缓存
        fileSet.forEach(file -> {
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(new byte[0]);
            } catch (IOException e) {
                throw new AppException(e);
            }
        });
        // 写入缓存
        for (Map.Entry<File, Map<String, Cache>> entry : fileMap.entrySet()) {
            try (FileWriter writer = new FileWriter(entry.getKey(), true)) {
                writer.write(JsonUtil.toJsonString(entry.getValue()));
            } catch (IOException e) {
                throw new AppException(e);
            }
        }
    }

    /**
     * 如果不存在，则创建缓存根目录
     *
     * @return 缓存根目录
     */
    private File createCacheRootIfNotExist() {
        File cacheFileDir = new File(cacheDir);
        if (!cacheFileDir.exists()) {
            cacheFileDir.mkdirs();
        }
        return cacheFileDir;
    }

    /**
     * 如果本地没有缓存则创建缓存
     *
     * @return 是否创建成功
     */
    private boolean createCachesIfNotExists() {
        File cacheFileDir = createCacheRootIfNotExist();
        if (Objects.requireNonNull(cacheFileDir.list()).length == 0) {
            APPLICATION_CACHE_MANAGER.setCaches(new HashMap<>(16));
            return true;
        }
        return false;
    }

    public String getCacheDir() {
        return cacheDir;
    }

    public void setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
    }
}

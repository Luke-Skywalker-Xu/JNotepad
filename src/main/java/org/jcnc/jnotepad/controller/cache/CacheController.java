package org.jcnc.jnotepad.controller.cache;

import org.jcnc.jnotepad.exception.AppException;
import org.jcnc.jnotepad.model.entity.Cache;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import static org.jcnc.jnotepad.common.constants.AppConstants.DEFAULT_PROPERTY;

/**
 * 缓存控制器
 *
 * @author gewuyou
 */
public class CacheController {

    private static final CacheController INSTANCE = new CacheController();

    private String cacheDir;

    private CacheController() {
        cacheDir = Paths.get(System.getProperty(DEFAULT_PROPERTY), ".jnotepad", "caches").toString();
        loadCaches();
    }

    public static CacheController getInstance() {
        return INSTANCE;
    }

    private void loadCaches() {

    }

    private void writeCaches(Map<String, Cache<?>> caches) {
        File cacheFileDir = new File(cacheDir);
        if (!cacheFileDir.exists()) {
            cacheFileDir.mkdirs();
        }
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

        });

        //
    }

    /**
     * 如果缓存
     */
    private void createCachesIfNotExists() {

    }

}

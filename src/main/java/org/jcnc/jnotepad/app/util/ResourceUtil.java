package org.jcnc.jnotepad.app.util;

import org.jcnc.jnotepad.JnotepadApp;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Objects;

/**
 * 资源工具
 *
 * @author gewuyou
 */
public class ResourceUtil {
    public static final String MODULE_DIR = "/jcnc/app/";

    private ResourceUtil() {
    }

    /**
     * Retrieves an input stream for the specified resource.
     *
     * @param resource the path to the resource
     * @return the input stream for the resource
     */
    public static InputStream getResourceAsStream(String resource) {
        String path = resolve(resource);
        return Objects.requireNonNull(
                JnotepadApp.class.getResourceAsStream(resolve(path)),
                "Resource not found: " + path
        );
    }

    /**
     * Retrieves the resource with the specified path.
     *
     * @param resource the path of the resource to retrieve
     * @return the URI of the retrieved resource
     */
    public static URI getResource(String resource) {
        String path = resolve(resource);
        URL url = Objects.requireNonNull(JnotepadApp.class.getResource(resolve(path)), "Resource not found: " + path);
        return URI.create(url.toExternalForm());
    }

    /**
     * Resolves a resource path by checking if it starts with a "/". If it does,
     * the resource path is returned as is. If it doesn't, the resource path is
     * concatenated with the module directory path.
     *
     * @param resource  the resource path to be resolved
     * @param moduleDir the module directory path
     * @return the resolved resource path
     */
    public static String resolve(String resource, String moduleDir) {
        Objects.requireNonNull(resource);
        return resource.startsWith("/") ? resource : moduleDir + resource;
    }

    /**
     * Resolve the given resource using the default module directory.
     *
     * @param resource the resource to resolve
     * @return the resolved resource
     */
    public static String resolve(String resource) {
        return resolve(resource, MODULE_DIR);
    }
}

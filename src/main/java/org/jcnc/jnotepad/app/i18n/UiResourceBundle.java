package org.jcnc.jnotepad.app.i18n;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * UI资源绑定，用于加载语言文件。
 *
 * <p>
 * 此类用于加载和管理UI资源文件，支持国际化和多语言功能。可以通过绑定StringProperty和键值对应的内容，以及获取当前资源文件的内容。
 * </p>
 *
 * <p>
 * 该类是一个单例类，通过getInstance方法获取实例。
 * </p>
 *
 * <p>
 * 使用方法示例：
 * <code>
 * UiResourceBundle.bindStringProperty(stringProperty, "key");
 * String content = UiResourceBundle.getContent("key");
 * </code>
 * </p>
 *
 * @author songdragon
 */
public class UiResourceBundle {

    private static final UiResourceBundle INSTANCE = new UiResourceBundle();
    /**
     * resource目录下的i18n/i18nXXX.properties
     */
    private static final String BASENAME = "jcnc/app/i18n/i18n";
    /**
     * 资源文件的观察者绑定。
     */
    private final ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();
    /**
     * 当前语言
     */
    private Locale currentLocale;

    private UiResourceBundle() {

    }

    /**
     * 获取UiResourceBundle的单例实例
     *
     * @return UiResourceBundle的单例实例
     */
    public static UiResourceBundle getInstance() {
        return INSTANCE;
    }

    /**
     * 工具方法：绑定StringProperty和Key对应的内容
     *
     * @param stringProperty 字符串属性
     * @param key            键值
     */
    public static void bindStringProperty(StringProperty stringProperty, String key) {
        if (stringProperty == null) {
            return;
        }
        stringProperty.bind(getInstance().getStringBinding(key));
    }

    /**
     * 获取当前资源中的key值
     *
     * @param key 资源所对应键
     * @return 当前键所对应的值
     */
    public static String getContent(String key) {
        return INSTANCE.getResources().getString(key);
    }

    /**
     * 获取当前资源文件
     *
     * @return 资源文件
     */
    public ObjectProperty<ResourceBundle> resourcesProperty() {
        return resources;
    }

    public final ResourceBundle getResources() {
        return resourcesProperty().get();
    }

    public final void setResources(ResourceBundle resources) {
        resourcesProperty().set(resources);
    }

    /**
     * 重置当前local
     *
     * @param toLocal 要设置的新的Locale
     */
    public final void resetLocal(Locale toLocal) {
        if (this.currentLocale == toLocal) {
            return;
        }
        this.currentLocale = toLocal;
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASENAME, currentLocale);
        this.setResources(resourceBundle);

    }

    /**
     * 获取key对应的绑定属性内容
     *
     * @param key key
     * @return key对应的内容
     */
    public StringBinding getStringBinding(String key) {
        return Bindings.createStringBinding(() -> getResources().getString(key), resourcesProperty());
    }

    /**
     * 注册资源变更监听器
     *
     * @param listener 变更监听器
     */
    public void addListener(ChangeListener<? super ResourceBundle> listener) {
        this.resources.addListener(listener);
    }
}

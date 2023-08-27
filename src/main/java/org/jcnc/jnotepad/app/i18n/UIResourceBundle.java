package org.jcnc.jnotepad.app.i18n;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import org.jcnc.jnotepad.app.config.LocalizationConfig;

import java.util.Locale;
import java.util.ResourceBundle;

public class UIResourceBundle {

    private static final UIResourceBundle INSTANCE = new UIResourceBundle();
    private static final String BASENAME = "i18n/i18n";

    private Locale currentLocale;

    public static final UIResourceBundle getInstance() {
        return INSTANCE;
    }

    private UIResourceBundle() {
        this.resetLocal();
    }

    private ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();

    public ObjectProperty<ResourceBundle> resourcesProperty() {
        return resources;
    }

    public final ResourceBundle getResources() {
        return resourcesProperty().get();
    }

    public final void setResources(ResourceBundle resources) {
        resourcesProperty().set(resources);
    }

    public final void resetLocal() {
        if (this.currentLocale == LocalizationConfig.getCurrentLocal()) {
            return;
        }
        this.currentLocale = LocalizationConfig.getCurrentLocal();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASENAME, currentLocale);
        this.setResources(resourceBundle);

    }

    public StringBinding getStringBinding(String key) {
        return new StringBinding() {
            {
                bind(resourcesProperty());
            }

            @Override
            public String computeValue() {
                return getResources().getString(key);
            }
        };
    }

    public static void bindStringProperty(StringProperty stringProperty, String key) {
        if (stringProperty == null) {
            return;
        }
        stringProperty.bind(getInstance().getStringBinding(key));
    }

    public static String getContent(String key) {
        return getInstance().getResources().getString(key);
    }

    public void addListener(ChangeListener<? super ResourceBundle> listener) {
        this.resources.addListener(listener);
    }
}

package org.jcnc.jnotepad.Interface;

import java.util.Properties;

/**
 * @author 许轲
 */
public interface ConfigInterface {
    void showErrorAlert();
    Properties readPropertiesFromFile();
    void initializePropertiesFile();
}

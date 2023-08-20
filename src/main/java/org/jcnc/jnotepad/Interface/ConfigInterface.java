package org.jcnc.jnotepad.Interface;

import java.util.Properties;

public interface ConfigInterface {
    void showErrorAlert();
    Properties readPropertiesFromFile();
    void initializePropertiesFile();
}

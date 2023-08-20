package org.jcnc.jnotepad.init;

import javafx.scene.control.Alert;
import org.jcnc.jnotepad.Interface.ConfigInterface;

import java.io.*;
import java.util.Properties;

import static org.jcnc.jnotepad.constants.Constants.PROPERTY_FILE_NAME;

public class Config implements ConfigInterface {
    public Properties readPropertiesFromFile() {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(PROPERTY_FILE_NAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            initializePropertiesFile();
        }
        return properties;
    }

    public void initializePropertiesFile() {
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty("title", "JNotepad");

        try (OutputStream outputStream = new FileOutputStream(PROPERTY_FILE_NAME)) {
            defaultProperties.store(outputStream, "JNotepad Properties");
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    public void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("错误");
        alert.setHeaderText("文件读写错误");
        alert.setContentText("文件读写错误");
        alert.showAndWait();
    }
}

package org.jcnc.jnotepad.app.init;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jcnc.jnotepad.exception.AppException;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.tool.PopUpUtil;
import org.slf4j.Logger;

import java.io.*;

import static org.jcnc.jnotepad.constants.AppConstants.CONFIG_NAME;
import static org.jcnc.jnotepad.constants.TextConstants.JNOTEPAD_CONFIG;

/**
 * 加载应用配置类
 * <br/>空出了加载文件的具体实现
 *
 * @author gewuyou
 */
public abstract class LoadJnotepadConfig<T> {
    Logger logger = LogUtil.getLogger(this.getClass());

    /**
     * 返回json配置文件的json节点
     *
     * @param inputStream 输入流
     * @return java.lang.String
     * @apiNote
     * @since 2023/8/25 17:17
     */
    protected JsonNode getConfigJson(InputStream inputStream) {
        StringBuffer jsonData = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }
        } catch (IOException e) {
            PopUpUtil.errorAlert("错误", "读写错误", "配置文件读写错误!");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(jsonData.toString());
        } catch (JsonProcessingException e) {
            throw new AppException(e.getMessage());
        }
        return jsonNode;
    }

    public final void load() {
        // 判断是否存在这个配置文件
        try (InputStream inputStream = new FileInputStream(CONFIG_NAME)) {
            logger.info("正在加载配置文件...");
            // 存在则加载
            loadConfig(inputStream);
        } catch (IOException e) {
            logger.info("未检测到配置文件!");
            // 不存在则创建
            createConfig();
            try {
                // 创建后重新加载
                loadConfig(new FileInputStream(CONFIG_NAME));
            } catch (FileNotFoundException ex) {
                throw new AppException(ex.getMessage());
            }
        }
    }

    /**
     * 解析配置文件
     *
     * @param inputStream 输入流
     * @return T
     * @since 2023/8/25 15:18
     */
    protected abstract T parseConfig(InputStream inputStream);

    private void createConfig() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_NAME))) {
            writer.write(JNOTEPAD_CONFIG);
        } catch (IOException e) {
            PopUpUtil.errorAlert("错误", "读写错误", "配置文件读写错误!");
        }
    }


    /**
     * 加载配置文件
     *
     * @param inputStream 配置文件的输入流
     */
    protected abstract void loadConfig(InputStream inputStream);
}

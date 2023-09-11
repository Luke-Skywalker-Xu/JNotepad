package org.jcnc.jnotepad.model.entity;

import lombok.Data;

/**
 * 快捷键信息类
 *
 * @author gewuyou
 */
@Data
public class ShortcutKey {
    /**
     * 按钮名称
     */
    private String buttonName;
    /**
     * 快捷键值
     */
    private String shortcutKeyValue;
}

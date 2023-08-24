package org.jcnc.jnotepad.app.entity;

/**
 * 样式
 *
 * @author gewuyou
 */
public class Style {
    private String styleName;
    private String styleValue;

    public Style() {
    }

    public Style(String styleName, String styleValue) {
        this.styleName = styleName;
        this.styleValue = styleValue;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getStyleValue() {
        return styleValue;
    }

    public void setStyleValue(String styleValue) {
        this.styleValue = styleValue;
    }
}

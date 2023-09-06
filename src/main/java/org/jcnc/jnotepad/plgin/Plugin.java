package org.jcnc.jnotepad.plgin;

/**
 * @author luke
 */
public interface Plugin extends PluginCategory {
    String getDisplayName();
    void execute();
}

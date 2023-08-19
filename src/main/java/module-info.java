module org.jcnc.jnotepad {
    requires javafx.controls;
    requires atlantafx.base;
    requires cn.hutool;

    exports org.jcnc.jnotepad;
    exports org.jcnc.jnotepad.tool;
    exports org.jcnc.jnotepad.Interface;
    exports org.jcnc.jnotepad.controller.event.handler;
    exports org.jcnc.jnotepad.controller.manager;
    exports org.jcnc.jnotepad.view.init;
    exports org.jcnc.jnotepad.view.manager;
    exports org.jcnc.jnotepad.constants;
    exports org.jcnc.jnotepad.ui;
}
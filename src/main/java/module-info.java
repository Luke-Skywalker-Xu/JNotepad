module org.jcnc.jnotepad {
    requires javafx.controls;
    // 不知道为什么，不加这个，日志框架在打包后的程序不起作用，会报错
    // Exception in thread "JavaFX Application Thread" java.lang.NoClassDefFoundError: javax/naming/NamingException
    // 但我打开源代码，他们的模块的确有包含这个，java.naming，这个没懂，我干脆自己导入
    requires java.naming;
    requires atlantafx.base;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires org.slf4j;
    requires ch.qos.logback.core;
    requires ch.qos.logback.classic;
    requires com.ibm.icu;
    exports org.jcnc.jnotepad;
    exports org.jcnc.jnotepad.app.config;
    exports org.jcnc.jnotepad.app.i18n;
    exports org.jcnc.jnotepad.constants;
    exports org.jcnc.jnotepad.controller.config;
    exports org.jcnc.jnotepad.controller.manager;
    exports org.jcnc.jnotepad.controller.i18n;
    exports org.jcnc.jnotepad.controller.event.handler.tool;
    exports org.jcnc.jnotepad.controller.event.handler.menuBar;
    exports org.jcnc.jnotepad.tool;
    exports org.jcnc.jnotepad.Interface;
    opens org.jcnc.jnotepad.app.config;
    exports org.jcnc.jnotepad.root.center.main.bottom.status;

}
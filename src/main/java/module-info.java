module org.jcnc.jnotepad {
    requires javafx.controls;
    // 不知道为什么，不加这个，日志框架在打包后的程序不起作用，会报错
    // Exception in thread "JavaFX Application Thread" java.lang.NoClassDefFoundError: javax/naming/NamingException
    // 但我打开源代码，他们的模块的确有包含这个，java.naming，这个没懂，我干脆自己导入
    requires java.naming;
    requires atlantafx.base;
    requires com.google.gson;
    requires org.slf4j;
    requires ch.qos.logback.core;
    requires ch.qos.logback.classic;
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
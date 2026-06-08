package com.bittercode;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import com.bittercode.util.AzureConfigResolver;

public class BootstrapApplication {

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(AzureConfigResolver.getOrDefault("PORT", "server.port", "8080"));
        String webappDir = new File("src/main/webapp").getAbsolutePath();

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getConnector();
        Context context = tomcat.addWebapp("", webappDir);
        context.setParentClassLoader(BootstrapApplication.class.getClassLoader());
        tomcat.start();
        tomcat.getServer().await();
    }
}

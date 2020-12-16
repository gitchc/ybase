package com.yonyou.mde.web.configurer;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

//@Configuration
public class MyErrorPageConfig {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                ErrorPage errorPage1 = new ErrorPage(HttpStatus.NOT_FOUND, "/page/error/4xx.html");
                ErrorPage errorPage2 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/page/error/5xx.html");
                factory.addErrorPages(errorPage1, errorPage2);
            }
        };
    }
}
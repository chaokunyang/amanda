package com.timeyang.amanda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * @author chaokunyang
 */
@Configuration
public class WebServletContextConfiguration extends WebMvcConfigurerAdapter {
    @Bean
    public LocaleResolver localeResolver()
    {
        return new SessionLocaleResolver();
    }

    // Configuring an interceptor that is responsible or swapping out the current locale allows for easy testing by a developer, and also gives you the option of including a select list in your UI that lets the user pick the locale they prefer.
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}

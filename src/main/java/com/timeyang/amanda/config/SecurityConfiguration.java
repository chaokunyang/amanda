package com.timeyang.amanda.config;

import com.timeyang.amanda.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * 安全配置
 *
 * @author chaokunyang
 * @create 2017-04-15
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true, order = 0, mode = AdviceMode.PROXY
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    // https://github.com/spring-projects/spring-security/issues/3078
    // The SessionRegistry is not exposed as a Bean so there is no way for ApplicationEvent to be published to it. A workaround is to do something like:
    @Bean
    protected SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder())
                .and()
                .eraseCredentials(true);
    }

    @Override
    public void configure(WebSecurity security) throws Exception {
        security.ignoring().antMatchers("/assets/**", "/favicon.ico", "/built/**");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests() // authorize all requests using the access rules defined earlier
                .antMatchers("/session/list")
                .hasAuthority("VIEW_USER_SESSIONS")
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login").failureUrl("/login?loginFailed")
                .defaultSuccessUrl("/admin")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and().logout()
                .logoutUrl("/logout").logoutSuccessUrl("/login?loggedOut")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .permitAll()
                .and().sessionManagement()
                .sessionFixation().changeSessionId()
                .maximumSessions(1).maxSessionsPreventsLogin(true)
                .sessionRegistry(this.sessionRegistry())
                .and().and().csrf()
                .requireCsrfProtectionMatcher(request -> {
                    String m = request.getMethod();
                    return !request.getServletPath().startsWith("/api/") &&
                            ("POST".equals(m)) || "PUT".equals(m) ||
                            "DELETE".equals(m) || "PATCH".equals(m); // rest services 不用考虑跨站请求伪造
                });
    }

    // missing HttpSessionEventPublisher in spring boot。Because SessionRegistry is not exposed as a Bean so there is no way for ApplicationEvent to be published to it, spring boot doesn't provide HttpSessionEventPublisher. solved by register both two
    // https://github.com/spring-projects/spring-boot/issues/1537
    // Register HttpSessionEventPublisher
    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

}
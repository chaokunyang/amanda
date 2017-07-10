package com.timeyang.amanda.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Amanda Config Properties. Mainly for username, password, name
 *
 * @author chaokunyang
 */
@Getter
@Setter
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "amanda")
@Log4j2
public class AmandaProperties {

    private static final String defaultUsername = "amanda";
    private static final String defaultPassword = "timeyang";
    private static final String defaultName = "amanda";

    private String username;
    private String password;
    private String name;

    @PostConstruct
    public void setup() {
        if(defaultUsername.equals(this.username)) {
            log.warn("You don't provide username in command line, default username " + defaultUsername + " will be used for init. This is not secure, we strongly suggest you to provide username in the form of '--amanda.username=xxx' or change them in config file");
        }
        if(defaultPassword.equals(this.password)) {
            log.warn("You don't provide password in command line, default password " + defaultPassword + " will be used for init. This is not secure, we strongly suggest you to provide password in the form of '--amanda.password=xxx' or change them in config file");
        }
        if(defaultName.equals(this.name)) {
            log.warn("You don't provide name in command line, default name " + defaultName + " will be used for init. Recommend provide name in cmd in the form of '--amanda.name=xxx' or change them in config file");
        }

    }
}

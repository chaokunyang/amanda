package com.timeyang.amanda.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 会话监控端点
 *
 * @author chaokunyang
 */
@RestController
@RequestMapping("/api/session")
public class SessionEndpoint {

    @Autowired
    private SessionRegistry sessionRegistry;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelMap list() {
        ModelMap map = new ModelMap();

        List<SessionInformation> sessions = new ArrayList<>();
        for(Object principal : this.sessionRegistry.getAllPrincipals()) {
            sessions.addAll(this.sessionRegistry.getAllSessions(principal, true));
        }
        map.put("timestamp", System.currentTimeMillis());
        map.put("numberOfSessions", sessions.size());
        map.put("sessions", sessions);

        return map;
    }

}

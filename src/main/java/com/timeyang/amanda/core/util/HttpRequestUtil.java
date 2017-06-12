package com.timeyang.amanda.core.util;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chaokunyang
 */
public class HttpRequestUtil {

    /**
     * Extract path from a controller mapping. /controllerUrl/** => return matched **
     * @param request incoming request.
     * @return extracted path
     */
    public static String extractPathFromPattern(final HttpServletRequest request){

        String path = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        AntPathMatcher apm = new AntPathMatcher();
        String finalPath = apm.extractPathWithinPattern(bestMatchPattern, path);

        return finalPath;

    }
}

package pl.edu.pg.eti.kask.blog.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mateusz.buchajewicz
 * Util which provides series of methods, which are commonly used in Servlet classes
 */
public class ServletUtils {
    /**
     * Extracts pathInfo from request
     * @param request request from which pathInfo should be extracted
     * @return pathInfo, or empty string if pathInfo is null
     */
    public static String parseRequestPath(HttpServletRequest request) {
        return request.getPathInfo() == null ? "" : request.getPathInfo();
    }

    /**
     * Extract numerical id from request, if it's there as a wildcard
     * @param request
     * @return id from wildcard as Long
     */
    public static Long getIdFromWildcard(HttpServletRequest request) {
        return Long.parseLong(parseRequestPath(request).replaceAll("/", ""));
    }
}

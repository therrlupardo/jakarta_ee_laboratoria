package pl.edu.pg.eti.kask.blog.utils;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
    public static String parseRequestPath(HttpServletRequest request) {
        return request.getPathInfo() == null ? "" : request.getPathInfo();
    }
}

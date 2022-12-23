package identification_of_rice.utils;


import javax.servlet.http.HttpServletRequest;

public abstract class UrlUtils {
    public static String getWholeUrl(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        return requestURI;
    }
}

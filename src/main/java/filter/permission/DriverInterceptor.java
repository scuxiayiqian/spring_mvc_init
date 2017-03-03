package filter.permission;

import util.Permission;
import util.PermissionSupport;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by oblige on 3/5/15.
 */
public class DriverInterceptor implements HandlerInterceptor{

    private static Pattern PTN_DRIVER = Pattern.compile("^/driver/(\\d+)($|/.*$)");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        Matcher matcher = PTN_DRIVER.matcher(uri);
        if (matcher.find()) {
            HttpSession session = request.getSession(true);
            boolean allow = PermissionSupport.assertThis(session, Permission.DRIVER, Long.parseLong(matcher.group(1)));
            if (!allow) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

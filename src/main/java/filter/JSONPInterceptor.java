package filter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by oblige on 11/11/14.
 */
public class JSONPInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String[]> parms = request.getParameterMap();

        if(parms.containsKey("callback")) {
            OutputStream out = response.getOutputStream();
            out.write((parms.get("callback")[0] + "(").getBytes());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Map<String, String[]> parms = request.getParameterMap();

        if(parms.containsKey("callback")) {
            OutputStream out = response.getOutputStream();
            out.write(");".getBytes());
            response.setContentType("text/javascript;charset=UTF-8");
            out.close();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

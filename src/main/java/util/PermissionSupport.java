package util;

import javax.servlet.http.HttpSession;

/**
 * Created by oblige on 3/4/15.
 */
public class PermissionSupport {

    /**
     * Check permission stored in current session with same value
     * @param session
     * @param permission
     * @param value
     * @return
     */
    public static boolean assertThis(HttpSession session, String permission, Object value) {
        return null != value && value.equals(session.getAttribute(permission));
    }

    /**
     * Store permission into current session
     * @param session
     * @param permission
     * @param value
     */
    public static void store(HttpSession session, String permission, Object value) {
        session.setAttribute(permission, value);
    }

    /**
     * Remove permission from current session
     * @param session
     * @param permission
     */
    public static void delete(HttpSession session, String permission) {
        session.removeAttribute(permission);
    }
}

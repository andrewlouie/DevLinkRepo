/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package andrew.addons;
import javax.servlet.http.*;
/**
 *
 * @author Andrew
 */
public class CookieUtil {
    public static String getCookieVal(Cookie[] cookies,String cookieName) {
        String cookieValue = "";
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookieName.equals(cookie.getName())) {
                    cookieValue = cookie.getValue();
                }
            }
        }
        return cookieValue;
    }
}

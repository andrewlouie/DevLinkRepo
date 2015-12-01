/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package andrew.addons;

import andrew.data.UserDB;
import andrew.tables.User;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andrew
 */
public class UsrChk {
    public static User validUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cookie[] cookies = request.getCookies();
        String username = CookieUtil.getCookieVal(cookies,"userIdCookie");
        String password = CookieUtil.getCookieVal(cookies,"optionCookie");
        if (user == null) {
            if (!"".equals(username)) {
                User usertest = UserDB.selectUserByName(username);
                if (usertest != null && usertest.getPassword().equals(password)) {
                    user = usertest;
                }
            }
        }
        return user;
    }
}

package andrew.servlets;

import andrew.addons.PasswordUtil;
import andrew.addons.UsrChk;
import andrew.data.CategoryDB;
import andrew.data.TypeDB;
import andrew.data.UserDB;
import andrew.tables.Category;
import andrew.tables.Type;
import andrew.tables.User;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/changepw.jsp";
        request.setAttribute("Categories", InitServlet.categories);
        request.setAttribute("Types", InitServlet.types);
        request.setAttribute("title","Change Password");
        User user = UsrChk.validUser(request);
        if (user == null) {
            request.setAttribute("ref","ChangePassword");
            request.setAttribute("title","Login");
            url = "/login.jsp";
            getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
            return;
        }
        String pw = request.getParameter("password");
        String pw2 = request.getParameter("repeatpassword");
        String cpw = request.getParameter("currentpw");
        
        String hashedPassword;
        String salt = "";
        String error = "";
        String saltedAndHashedPassword = "";
        try {
            if ((cpw == null) || (!PasswordUtil.hashPassword(cpw+user.getSalt()).equals(user.getPassword()))) {
                error = "Current password is not correct";
            }
            else if (!pw.equals(pw2)) error = "Passwords do not match";
            else {
                String message;
                try {
                    PasswordUtil.checkPasswordStrength(pw);
                    message = "";
                } catch (Exception ex) {
                    message = ex.getMessage();
                }
                if (message.length() > 0) error = message;
                // hash and salt password
                try {
                    hashedPassword = PasswordUtil.hashPassword(pw);
                    salt = PasswordUtil.getSalt();
                    saltedAndHashedPassword = PasswordUtil.hashPassword(pw + salt);
        
                } catch (NoSuchAlgorithmException ex) {
                    if (message.length() > 0) error = ex.getMessage();
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            error = "Something went wrong";
        }
        if ("".equals(error)) {
            user.setSalt(salt);
            user.setPassword(saltedAndHashedPassword);
            UserDB.update(user);
            Cookie cred = new Cookie("userIdCookie","");
            cred.setMaxAge(0);
            response.addCookie(cred);
            Cookie credp = new Cookie("optionCookie","");
            credp.setMaxAge(0);
            response.addCookie(credp);
            error = "Password has been changed";
        }
        request.setAttribute("error",error);
        request.setAttribute("user",user);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }    
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException
    {
        String url = "/changepw.jsp";
        request.setAttribute("Categories", InitServlet.categories);
        request.setAttribute("Types", InitServlet.types);
        User user = UsrChk.validUser(request);
        request.setAttribute("title","Change Password");
        if (user == null) {
            request.setAttribute("ref","ChangePassword");
            request.setAttribute("title","Login");
            url = "/login.jsp";
            getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
            return;
        }
        request.setAttribute("user",user);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}
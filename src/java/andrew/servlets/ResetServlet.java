package andrew.servlets;

import andrew.addons.PasswordUtil;
import andrew.addons.UsrChk;
import andrew.data.CategoryDB;
import andrew.data.TypeDB;
import andrew.data.UserDB;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import andrew.tables.*;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ResetServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = "/reset.jsp";
        request.setAttribute("title","Reset Your Password");
        String error = "";
        String passreset = request.getParameter("passreset");
        String pw = request.getParameter("pass1");
        String pw2 = request.getParameter("pass2");
        //set user
        User user = UserDB.selectUserByPassreset(passreset);
        if (user != null) {
            String hashedPassword;
        String salt = "";
        String saltedAndHashedPassword = "";
        if (!pw.equals(pw2)) error = "Password does not match";
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
        if ("".equals(error)) {
            user.setPassword(saltedAndHashedPassword);
            user.setSalt(salt);
            user.setPassreset(null);
            UserDB.update(user);
            url = "/login.jsp";
            request.setAttribute("title","Login");
        }
        }
        else { url = "/login.jsp";request.setAttribute("title","Login");}
        request.setAttribute("passreset",passreset);
        request.setAttribute("error", error);
        User user2 = UsrChk.validUser(request);
        request.setAttribute("user",user2);
        request.setAttribute("Categories", InitServlet.categories);
        request.setAttribute("Types", InitServlet.types);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
        }
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException
    {
        String passreset = request.getParameter("q");
        User user = UserDB.selectUserByPassreset(passreset);
        String url = "/login.jsp";
        request.setAttribute("title","Login");
        if (user != null) {
            request.setAttribute("passreset", passreset);
            url = "/reset.jsp";
            request.setAttribute("title","Reset Your Password");
        }
        User user2 = UsrChk.validUser(request);
        request.setAttribute("user",user2);
        request.setAttribute("Categories", InitServlet.categories);
        request.setAttribute("Types", InitServlet.types);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    
}
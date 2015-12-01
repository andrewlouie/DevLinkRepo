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
import java.util.List;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        User user2 = UsrChk.validUser(request);
        request.setAttribute("user",user2);
        String url = "/login.jsp";
        request.setAttribute("title","Login");
        String error = "";
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Boolean remember = ("1".equals(request.getParameter("remember")));
        //find user
        User user = null;
        if (username.length() == 0) error = "Please enter your username or email address";
        else if (UserDB.selectUserByName(username) != null) user = UserDB.selectUserByName(username);
        else if (UserDB.selectUserByEmail(username) != null) user = UserDB.selectUserByEmail(username);
        else error = "Username not found";
        //check password
        if (user != null) {
            try {
            if (user.getActivation() != null && user.getActivation().length() > 0) error = "Account has not been activated.  Click <a href='./Reactivate'>here</a> to resend activation email or change your email address";
            else if (PasswordUtil.hashPassword(password+user.getSalt()).equals(user.getPassword())) {
                HttpSession session = request.getSession();
               session.setAttribute("user",user);
               if (remember) {
                   Cookie cred = new Cookie("userIdCookie",username);
                   cred.setMaxAge(3*24*60*60);
                   response.addCookie(cred);
                   Cookie credp = new Cookie("optionCookie",user.getPassword());
                   credp.setMaxAge(3*24*60*60);
                   response.addCookie(credp);
               }
            }
            else error = "Password is incorrect";
            }
            catch (Exception e) {
            error = "Password is incorrect or there was an error " + e;
            }
        }
        String ref = request.getParameter("ref");
        if ("".equals(error)) response.sendRedirect("./" + ref);
        else {
        request.setAttribute("ref",ref);
        request.setAttribute("username", username);
        request.setAttribute("error", error);
request.setAttribute("Categories", InitServlet.categories);
        request.setAttribute("Types", InitServlet.types);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
        }
    }    
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException
    {
        User user2 = UsrChk.validUser(request);
        request.setAttribute("user",user2);
        String url = "/login.jsp";
        request.setAttribute("title","Login");
        String ref = request.getParameter("ref");
        request.setAttribute("ref",ref);
        request.setAttribute("Categories", InitServlet.categories);
        request.setAttribute("Types", InitServlet.types);
                        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    
}
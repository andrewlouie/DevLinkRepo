package andrew.ajax;

import andrew.data.UserDB;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UserNameCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(UserDB.usernameExists(request.getParameter("username"))));
    }
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.sendRedirect("/404");
    }
}
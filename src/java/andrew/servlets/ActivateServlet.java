package andrew.servlets;

import andrew.addons.UsrChk;
import andrew.data.CategoryDB;
import andrew.data.TypeDB;
import andrew.data.UserDB;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import andrew.tables.*;
import java.util.List;

public class ActivateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = UsrChk.validUser(request);
        request.setAttribute("user",user);
        String activation = request.getParameter("q");
        User user2 = UserDB.selectUserByActivation(activation);
        request.setAttribute("title","Login");
        String url = "/login.jsp";
request.setAttribute("Categories", InitServlet.categories);
        request.setAttribute("Types", InitServlet.types);        
        if (user2 != null) {
            user2.setActivation(null);
            UserDB.update(user2);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
        }
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException
    {
     this.doPost(request,response);
    }
    
    
}
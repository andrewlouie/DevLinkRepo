package andrew.servlets;

import andrew.data.UserDB;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import andrew.tables.*;
import andrew.addons.*;
import andrew.data.CategoryDB;
import andrew.data.LinkDB;
import andrew.data.TypeDB;
import java.util.List;

public class UserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setAttribute("user",UsrChk.validUser(request));
        String task = "";
        if (request.getRequestURI().length() > request.getContextPath().length() + request.getServletPath().length()) task = request.getRequestURI().substring(request.getContextPath().length() + request.getServletPath().length() + 1);
        User profile = UserDB.selectUserByName(task);
        String page = request.getParameter("q");
        String url = "";
        if (profile == null) { url = "/usernotfound.jsp"; request.setAttribute("title","User Not Found"); }
        else if (page == null || page.length() == 0) {
            url = "/user.jsp";
            request.setAttribute("title",profile.getUserName() + " Profile");
            String profilepic = "includes/person-placeholder.png";
            if (profile.getPic()) {
                String profpic = NextFile.findExt(request.getServletContext().getRealPath("") + "profilepics\\", profile.getUserName());
                if (!"".equals(profpic)) profilepic = "profilepics/" + profpic;
            }
            request.setAttribute("profilepic",profilepic);
        }
        else if (page.equals("UpVotes")) {
            url = "/userupvotes.jsp";
            request.setAttribute("title",profile.getUserName() + " UpVotes");
            List<Link> upvoted = LinkDB.selectLinkByUpVotes(profile);
            request.setAttribute("links", upvoted);
        }
        else if (page.equals("Links")) {
            url = "/userlinks.jsp";
            request.setAttribute("title",profile.getUserName() + " Links");
        }
        request.setAttribute("profile",profile);
        request.setAttribute("url",request.getRequestURL());
        request.setAttribute("get",iif.iif(request.getQueryString() != null,"?" + request.getQueryString(),""));
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
        this.doPost(request, response);
    }
}
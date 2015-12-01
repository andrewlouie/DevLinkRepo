package andrew.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import andrew.tables.*;
import andrew.addons.*;
import andrew.data.LinkDB;
import java.util.Date;

public class LinkServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setAttribute("user",UsrChk.validUser(request));
        String task = "";
        if (request.getRequestURI().length() > request.getContextPath().length() + request.getServletPath().length()) task = request.getRequestURI().substring(request.getContextPath().length() + request.getServletPath().length() + 1);
        int tsk = 0;
        try {
            tsk = Integer.parseInt(task);
        }
        catch (Exception e){
        }
        Link link = LinkDB.selectLinkById(tsk);
        String url = "";
        if (link == null) { url = "/linknotfound.jsp"; request.setAttribute("title","Link Not Found"); }
        else {
        request.setAttribute("link",link);
        url = "/link.jsp";
        request.setAttribute("title",link.getTitle());
        String cmt = request.getParameter("comment");
        if (cmt != null && cmt.length() > 0) {
            if (UsrChk.validUser(request) != null) {
                User user = UsrChk.validUser(request);
                Comment comment = new Comment();
                comment.setComment(cmt);
                Date today = new Date();
                comment.setDateTime(today);
                comment.setUser(user);
                comment.setLink(link);
                link.addComment(comment);
                LinkDB.update(link);
                response.sendRedirect("./" + task);
                return;
            }
            else { url = "/login.jsp"; request.setAttribute("ref","Link/" + task); request.setAttribute("title","Login"); }
        }
        }
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
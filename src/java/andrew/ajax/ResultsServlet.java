package andrew.ajax;

import andrew.tables.Link;
import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

public class ResultsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String pageid = request.getParameter("pageid");
        HttpSession session = request.getSession();
        if (session.getAttribute(pageid) == null) {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("<script>location.reload(true)</script>");
            return;
        }
        List<Link> links = (List<Link>) session.getAttribute(pageid);
        Integer offset = (Integer) session.getAttribute(pageid + "offset");
        int max = 10;
        if (links != null) {
            if (offset > links.size()) links = null;
            else {
                if (links.size() < max+offset) max = links.size() - offset;
                links = links.subList(offset,offset+max);
            }
        }
        request.setAttribute("links",links);
        session.setAttribute(pageid + "offset", offset+max);
        getServletContext()
                .getRequestDispatcher("/ajaxlinks.jsp")
                .forward(request, response);
    }   
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.sendRedirect("/404");
    }
}
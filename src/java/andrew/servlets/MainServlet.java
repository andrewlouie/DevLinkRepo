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
import java.util.Date;
import java.util.List;

public class MainServlet extends HttpServlet {

    
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String search = request.getParameter("search");
        String aacategory = request.getParameter("category");
        String aatype = request.getParameter("type");
        String aaskill = request.getParameter("skilllevel");
        //these get processed after SQL
        String aaoffset = request.getParameter("offset");
        String aamax = request.getParameter("max");
        session.setAttribute("url",request.getRequestURL());
        session.setAttribute("get",iif.iif(request.getQueryString() != null,"?" + request.getQueryString(),""));
        int category = 0;
        try {
            category = Integer.parseInt(aacategory);
        }
        catch (Exception e) {
        }
        int type = 0;
        try {
            type = Integer.parseInt(aatype);
        }
        catch (Exception e) {
        }
        int offset = 0;
        try {
            offset = Integer.parseInt(aaoffset);
        }
        catch (Exception e) {
        }
        int skill = -1;
        try {
            skill = Integer.parseInt(aaskill);
        }
        catch (Exception e) {
        }
        int max = 50;
        try {
            max = Integer.parseInt(aamax);
        }
        catch (Exception e) {
        }
        request.setAttribute("search",search);
        request.setAttribute("selectedcat",category);
        request.setAttribute("selectedtype",type);
        request.setAttribute("selectedskill",skill);
        request.setAttribute("offset",offset);
        request.setAttribute("max",max);
        String task = "";
        try { 
            task = request.getRequestURI().substring(request.getContextPath().length() + 1);
        }
        catch (StringIndexOutOfBoundsException e) {
        }
        User user = (User) session.getAttribute("user");
        Cookie[] cookies = request.getCookies();
        boolean logout = "logout".equals(request.getParameter("action"));
        request.setAttribute("Categories", InitServlet.categories);
        request.setAttribute("Types", InitServlet.types);
            //set parameters for searching.  LinkDB method will determine the SQL string
        Category cat = CategoryDB.selectCatById(category);
        Type typ = TypeDB.selectTypeById(type);
             //sort the results with another method and limit them if necessary
        List<Link> links = LinkDB.selectLinks(cat,typ,search,skill);
        if ("".equals(task)) { request.setAttribute("title","Hot");links = SortList.SortByHot(links); }
        else if ("TopUsers".equals(task)) { request.setAttribute("aaparam","TopUsers"); links = SortList.SortByTopUser(links); request.setAttribute("title","Top Users"); }
        else if ("Top".equals(task)) { request.setAttribute("aaparam","Top"); links = SortList.SortByTop(links); request.setAttribute("title","Top"); }
        else { request.setAttribute("title","New");request.setAttribute("aaparam","New"); }
        
        //limit the results.  default is 0-100
        
        Date now = new Date();
        String pageId = session.getId() + now.getTime();
        session.setAttribute(pageId, links);
        session.setAttribute(pageId + "offset",max);
        request.setAttribute("pageId",pageId);
        if (links != null) {
            request.setAttribute("linkstotal",links.size());
            if (offset > links.size()) links = null;
            else {
                if (links.size() < max+offset) max = links.size() - offset;
                links = links.subList(offset,offset+max);
            }
        }
        if (logout) {
            Cookie cred = new Cookie("userIdCookie","");
            cred.setMaxAge(0);
            response.addCookie(cred);
            Cookie credp = new Cookie("optionCookie","");
            credp.setMaxAge(0);
            response.addCookie(credp);
            user = null;
            session.setAttribute("user",user);
            response.sendRedirect("/");
            return;
        }
        else {
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
        }
        String page = request.getServletPath();
        request.setAttribute("page",page);
        String url = "/main.jsp";
        session.setAttribute("user",user);
        request.setAttribute("links",links);
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
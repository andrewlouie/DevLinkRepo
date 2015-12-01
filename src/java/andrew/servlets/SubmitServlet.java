package andrew.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import andrew.addons.*;
import andrew.data.CategoryDB;
import andrew.data.TypeDB;
import andrew.data.UserDB;
import java.util.List;
import andrew.tables.Category;
import andrew.tables.Type;
import andrew.tables.Link;
import andrew.tables.User;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.validator.*;

public class SubmitServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        //this returns either Submit or Preview.  request.getParameter("submit"));
        request.setAttribute("Categories", InitServlet.categories);
        request.setAttribute("Types", InitServlet.types);
        if (UsrChk.validUser(request) != null) {
            String url = "/addlink.jsp";
            request.setAttribute("title","Add a Link");
            int maxlength = 500;
            //set variables for parameters
            String cat = request.getParameter("category");
            String type = request.getParameter("type");
            String desc = request.getParameter("description");
            if (desc != null && desc.length() > maxlength) desc = desc.substring(0,maxlength);
            String title = request.getParameter("title");
            if (title != null && title.length() > maxlength) title = title.substring(0,maxlength);
            
            boolean pic = false;
            int sl = 0;
            try {
                sl = Integer.parseInt(request.getParameter("skilllevel"));
            } 
            catch(Exception ex) {
            }
            finally {
                if (sl > 4 || sl < 0) sl = 0;
            }
            
            String website = request.getParameter("url");
            
            //validation
            Link link = new Link();
            List<String> e = new ArrayList<>();
            
            UrlValidator urlValidator = new UrlValidator();
            
            if (website.length() > maxlength) website = website.substring(0,maxlength);
            if (website.isEmpty() || website.length() == 0) e.add("URL is missing");
            else if (!urlValidator.isValid(website)) e.add("URL is invalid");
            else link.setUrl(website);
            
            link.setSkilllevel(sl);
            link.setPicture(pic);
            if (title.isEmpty() || title.length() == 0) e.add("Title is missing");
            else link.setTitle(title);
        
            if (desc.isEmpty() || desc.length() == 0) e.add("Description is missing");
            else link.setDescription(desc);
            
            int cate = 0;
            try {
                cate = Integer.parseInt(cat);
            }
            catch (Exception ex) {
            }
            Category categ = CategoryDB.selectCatById(cate);
            if (categ == null) e.add("Category is invalid");
            else link.setCategory(categ);
            
            int typ = 0;
            try {
                typ = Integer.parseInt(type);
            }
            catch (Exception ex) {
            }
            Type ty = TypeDB.selectTypeById(typ);
            if (ty == null) e.add("Type is invalid");
            else link.setType(ty);
            
            request.setAttribute("link",link);
            if (e.isEmpty()) {
            
            //create link
            Date today = new Date();
            link.setDateTime(today);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            user.addLink(link);
            user = UserDB.update(user);
            session.setAttribute("user",user);
            response.sendRedirect("./User/" + user.getUserName() + "?q=Links");
            return;
        }
        request.setAttribute("errors", e);
        
            getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    else {
    request.setAttribute("ref","SubmitLink");
            String url = "/login.jsp";
            request.setAttribute("title","Login");
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    }    
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {
    
        request.setAttribute("Categories", InitServlet.categories);
        request.setAttribute("Types", InitServlet.types);
        
    if (UsrChk.validUser(request) != null) {
            String url = "/addlink.jsp";
            request.setAttribute("title","Add a Link");
            getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    else {
    request.setAttribute("ref","SubmitLink");
            String url = "/login.jsp";
            request.setAttribute("title","Login");
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);}
    }
}
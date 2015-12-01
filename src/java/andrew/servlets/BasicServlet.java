package andrew.servlets;

import andrew.addons.MailUtilGmail;
import andrew.addons.UsrChk;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import andrew.tables.*;
import javax.mail.MessagingException;

public class BasicServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        User user2 = UsrChk.validUser(request);
        request.setAttribute("user",user2);
        String page = request.getServletPath().substring(1);
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");
        String newcat = request.getParameter("newcat");
        String form = request.getParameter("form");
        request.setAttribute("title",page);
        String url = "/faq.jsp";
        switch (page) {
            case "About": url = "/about.jsp"; break;
            case "Rules": url = "/rules.jsp"; break;
            case "Suggest": 
                url = "/suggest.jsp";
                if (form != null && "suggest".equals(form)) {
                    if (newcat != null && !"".equals(newcat)) {
                    String to = "andrew_louie@hotmail.com";
                    String from = "devlinkrepo@gmail.com";
                    String subject = "DevLinkRepo Category Suggestion";
                    String body = "Someone has suggested you add the category " + newcat;
                    boolean isBodyHTML = true;
                    try {
                        MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
                    } catch (MessagingException exc) {
                    }
                    request.setAttribute("message","Thank you");
                    }
                    else request.setAttribute("message","Please fill in all fields");
                
                }
                break;
            
            case "Contact": 
                url = "/contact.jsp";
                if (form != null && "contact".equals(form)) {
                    if (name != null && email != null && message != null && !"".equals(name) && !"".equals(email) && !"".equals(message)) {
                    String to = "andrew_louie@hotmail.com";
                    String from = "devlinkrepo@gmail.com";
                    String subject = "DevLinkRepo Contact Form";
                    String body = "Someone has suggested filled out the website contact form:" +
                            "<br>" + 
                            "Name: " + name + "<br>" +
                            "Email: " + email + "<br>" +
                            "Message: " + message;
                    boolean isBodyHTML = true;
                    try {
                        MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
                    } catch (MessagingException exc) {
                    }
                    request.setAttribute("message","Thank you, I will respond shortly");
                    }
                    else request.setAttribute("message","Please fill in all fields");
                
                }
                break;
            
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
        this.doPost(request,response);
    }
    
    
}
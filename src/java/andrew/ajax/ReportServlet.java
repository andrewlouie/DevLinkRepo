package andrew.ajax;

import andrew.addons.MailUtilGmail;
import java.io.*;
import javax.mail.MessagingException;
import javax.servlet.*;
import javax.servlet.http.*;

public class ReportServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("linkid");
        String problem = request.getParameter("reason");
        String other = request.getParameter("other");
        String to = "devlinkrepo@gmail.com";
            String from = "devlinkrepo@gmail.com"; 
            String subject = "DevLinkRepo Reported Link";
            String body = "Link " + id + " is apparently a problem.<br>" + problem + " " + other;
            boolean isBodyHTML = true;
            try {
                MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
            } catch (MessagingException exc) {
            }
        response.getWriter().write("Sent");
    }        
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.sendRedirect("/404");
    }
}
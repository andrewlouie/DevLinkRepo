
package andrew.servlets;

import andrew.addons.MailUtilGmail;
import andrew.addons.UsrChk;
import andrew.data.CategoryDB;
import andrew.data.TypeDB;
import andrew.data.UserDB;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import andrew.tables.*;
import java.util.List;
import java.util.UUID;
import javax.mail.MessagingException;

public class ForgotServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String url = "/forgot.jsp";
        request.setAttribute("title","Forgot Your Password");
        request.setAttribute("Categories", InitServlet.categories);
        request.setAttribute("Types", InitServlet.types);
        User user2 = UsrChk.validUser(request);
        request.setAttribute("user",user2);
        User user = null;
        String error = "";
        if (username.length() == 0) error = "Please enter your username";
        else if (UserDB.selectUserByName(username) != null) user = UserDB.selectUserByName(username);
        else error = "Username not found";
        //resend email
        if ("".equals(error)) {
            ServletContext application = getServletContext();
            String root = application.getInitParameter("root");
            String rand = UUID.randomUUID().toString();
            rand = rand.replaceAll("[\\s\\-()]","").substring(0,12);
            user.setPassreset(rand);
            UserDB.update(user);
            String to = user.getEmail();
            String from = "devlinkrepo@gmail.com";
            String subject = "DevLinkRepo - Password Reset";
            String body = "Dear " + user.getFirstName() + ",<br><br>"
                    + "A request has been made to reset your password.<br>  " +
                    "Click <a href='" + root + "Reset?q=" + user.getPassreset() + "'>here</a> to reset it now.";
            boolean isBodyHTML = true;
            try {
                MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
            } catch (MessagingException exc) {
            }
            request.setAttribute("done",true);
        }
        request.setAttribute("username", username);
        request.setAttribute("error", error);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
        
    }    
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException
    {
        String url = "/forgot.jsp";
        request.setAttribute("title","Forgot Your Password");
        request.setAttribute("Categories", InitServlet.categories);
        request.setAttribute("Types", InitServlet.types);
        User user2 = UsrChk.validUser(request);
        request.setAttribute("user",user2);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    
}
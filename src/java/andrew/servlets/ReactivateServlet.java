package andrew.servlets;

import andrew.addons.MailUtilGmail;
import andrew.addons.PasswordUtil;
import andrew.addons.UsrChk;
import andrew.addons.emailcheck;
import andrew.data.UserDB;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import andrew.tables.*;
import javax.mail.MessagingException;

public class ReactivateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        User user2 = UsrChk.validUser(request);
        request.setAttribute("user",user2);
        String url = "/reactivate.jsp";
        request.setAttribute("title","Resend Activation Email");
        String error = "";
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        //find user
        User user = null;
        if (username.length() == 0) error = "Please enter your username";
        else if (UserDB.selectUserByName(username) != null) user = UserDB.selectUserByName(username);
        else error = "Username not found";
        if (email == null || email.length() == 0) error = "Email address is required";
        else if (user != null) {
            try {
                System.out.println("Got her2e");
            if (user.getActivation() == null || user.getActivation().length() == 0) error = "Account is already activated";
            else if (PasswordUtil.hashPassword(password+user.getSalt()).equals(user.getPassword())) {
               //set new email address
               if (!email.equals(user.getEmail())) {
                    if (email.isEmpty() || email.length() == 0) error = "Email is missing";
                    else if (!emailcheck.isValidEmailAddress(email)) error = "Email is invalid";
                    else if (UserDB.emailExists(email)) error = "Email is already assigned to another account";
                    else { user.setEmail(email); UserDB.update(user); }
                    System.out.println("Got dhere");
                }
                //resend email
               System.out.println(error);
               if ("".equals(error)) {
                   System.out.println("Resent email");
                   ServletContext application = getServletContext(); 
            String root = application.getInitParameter("root");
                   url = "/usercreated.jsp";
                   request.setAttribute("title","Profile Created");
                   String to = user.getEmail();
                   String from = "devlinkrepo@gmail.com";
                   String subject = "DevLinkRepo - Welcome";
                   String body = "Dear " + user.getFirstName() + ",<br><br>"
                    + "Thanks for joining DevLinkRepo.<br>  " +
                    "Click <a href='" + root + "Activate?q=" + user.getActivation() + "'>here</a> to activate your account.";
            boolean isBodyHTML = true;
            request.setAttribute("user2",user);
            try {
                MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
            } catch (MessagingException exc) {
            }

               }
            }
            else error = "Password is incorrect";
            }
            catch (Exception e) {
            error = "Password is incorrect or there was an error " + e;
            }
        }
        request.setAttribute("email",email);
        request.setAttribute("username", username);
        request.setAttribute("error", error);
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
        User user2 = UsrChk.validUser(request);
        request.setAttribute("user",user2);
        String url = "/reactivate.jsp";
        request.setAttribute("title","Resend Activation Email");
request.setAttribute("Categories", InitServlet.categories);
        request.setAttribute("Types", InitServlet.types);
                
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    
}
package andrew.servlets;

import andrew.data.UserDB;
import andrew.addons.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import andrew.tables.*;
import java.awt.Image;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;


public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        int maxlength = 500;
        String url = "/register.jsp";
        request.setAttribute("title","Register");
        String pic = "";
        String filename = "";
        String submit = "";
        String fn = "";
        String ln = "";
        String un = "";
        String em = "";
        String desc = "";
        String pw = "";
        String pw2 = "";
        String lang = "";
        String website = "";
        String skilllevel = "";
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                new DiskFileItemFactory()).parseRequest(request);
                for(FileItem item : multiparts){
                    if(item.isFormField()){
                    switch (item.getFieldName()) {
                        case "submit": submit = item.getString(); break;
                        case "firstname": fn = item.getString(); break;
                        case "lastname": ln = item.getString(); break;
                        case "username": un = item.getString(); break;
                        case "password": pw = item.getString(); break;
                        case "repeatpassword": pw2 = item.getString(); break;
                        case "email": em = item.getString(); break;
                        case "skilllevel": skilllevel = item.getString(); break;
                        case "languages": lang = item.getString(); break;
                        case "description": desc = item.getString(); break;
                        case "website": website = item.getString(); break;
                        case "pic": pic = item.getString(); break;
                    }
                    }
                }
                if ("Upload File".equals(submit)) {
                    boolean toobig = false;
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                        long sizeInBytes = item.getSize();
                        if (sizeInBytes > 10485760) { toobig = true; }
                        else {
                            String name = new File(item.getName()).getName();
                            String filen = NextFile.nextFile(request.getServletContext().getRealPath("") + "temp" + File.separator + name);
                            item.write( new File(request.getServletContext().getRealPath("") + "temp\\" + filen));
                            filename = filen;
                        }
                       }
                    }
                boolean valid = true;
                try {
                    Image image = ImageIO.read(new File(request.getServletContext().getRealPath("") + "temp\\" + filename));
                    if (image == null) {
                        valid = false;
                    }
                } catch(IOException ex) {
                valid=false;
                }
                if (toobig) {
                    pic = "";
                    request.setAttribute("message","File is too big, 10MB maximum");
                }
                else if (valid) {
                pic = filename;
                request.setAttribute("message", "File Uploaded Successfully");
                }
                else request.setAttribute("message", "The file is not valid or could not be opened");
                }
            } catch (Exception ex) {
               request.setAttribute("message", "File Upload Failed");
               pic = "";
            }
        
        int sl = 0;
        try {
            sl = Integer.parseInt(skilllevel);
        } 
        catch(Exception ex) {
        }
        finally {
            if (sl > 4 || sl < 0) sl = 0;
        }
        User user = new User();
        if (desc.length() > maxlength) desc = desc.substring(0,maxlength);
        if (lang.length() > maxlength) lang = lang.substring(0,maxlength);
        if (website.length() > maxlength) website = website.substring(0,maxlength);
        if ("Upload File".equals(submit)) {
        //remember all the fields that are filled out, regardless of validation
        user.setFirstName(fn);
        user.setLastName(ln);
        user.setUserName(un);
        user.setEmail(em);
        user.setDescription(desc);
        user.setLanguages(lang);
        user.setSkilllevel(sl);
        user.setWebsite(website);
        }
        else {
        //validation
        List<String> e = new ArrayList<>();
        if (fn.isEmpty() || fn.length() == 0) e.add("First name is missing");
        else if (fn.length() > 30) e.add("Maximum length for first name is 30 characters");
        else user.setFirstName(fn);
        if (ln.isEmpty() || ln.length() == 0) e.add("Last name is missing");
        else if (ln.length() > 30) e.add("Maximum length for last name is 30 characters");
        else user.setLastName(ln);
        if (un.isEmpty() || un.length() == 0) e.add("Username is missing");
        else if (un.length() > 30) e.add("Maximum length for user name is 30 characters");
        else if (UserDB.usernameExists(un)) e.add("Username is already in use");
        else if (!un.matches("^(?=.{5,30}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")) e.add("Username is not valid.  Must be between 5 and 30 characters.  May include _ and . but not at the start or end or in a row.");
        else user.setUserName(un);
        if (em.isEmpty() || em.length() == 0) e.add("Email is missing");
        else if (!emailcheck.isValidEmailAddress(em)) e.add("Email is invalid");
        else if (UserDB.emailExists(em)) e.add("Email is already assigned to an account");
        else user.setEmail(em);
        user.setSkilllevel(sl);
        String hashedPassword;
        String salt = "";
        String saltedAndHashedPassword = "";
        if (!pw.equals(pw2)) { e.add("Password does not match"); }
        else {
            String message;
            try {
                PasswordUtil.checkPasswordStrength(pw);
                message = "";
            } catch (Exception ex) {
                message = ex.getMessage();
            }
            if (message.length() > 0) e.add(message);
            // hash and salt password
                try {
                hashedPassword = PasswordUtil.hashPassword(pw);
                salt = PasswordUtil.getSalt();
                saltedAndHashedPassword = PasswordUtil.hashPassword(pw + salt);                    
            } catch (NoSuchAlgorithmException ex) {
                if (message.length() > 0) e.add(ex.getMessage());
            }
        }
        user.setDescription(desc);
        user.setLanguages(lang);
        user.setSalt(salt);
        user.setWebsite(website);
        user.setPassword(saltedAndHashedPassword);
        
        if (e.isEmpty()) {
            //create user
            ServletContext application = getServletContext(); 
            String root = application.getInitParameter("root");
            if (!pic.isEmpty() && pic.length() > 0) {
                FileUtils.moveFile(new File(request.getServletContext().getRealPath("") + "temp\\" + pic), new File(request.getServletContext().getRealPath("") + "profilepics\\" + un + "." + FilenameUtils.getExtension(pic)));
                //copy from temp to a new folder as the username
                user.setPic(true);
            }
        else user.setPic(false);
            Date today = new Date();
            user.setDatejoined(today);
            url = "/usercreated.jsp";
            String rand = UUID.randomUUID().toString();
            user.setActivation(rand.replaceAll("[\\s\\-()]","").substring(0,12));
            UserDB.insert(user);
            //send user an email
            String to = user.getEmail();
            String from = "devlinkrepo@gmail.com";
            String subject = "DevLinkRepo - Welcome";
            String body = "Dear " + user.getFirstName() + ",<br><br>"
                    + "Thanks for joining DevLinkRepo.<br>  " +
                    "Click <a href='" + root + "Activate?q=" + user.getActivation() + "'>here</a> to activate your account.";
            boolean isBodyHTML = true;

            try {
                MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
            } catch (MessagingException exc) {
            }
        }
        request.setAttribute("errors", e);
        }
        request.setAttribute("pic",pic);
        User user2 = UsrChk.validUser(request);
        request.setAttribute("user",user2);
        request.setAttribute("user2",user);
        request.setAttribute("profilepic",iif.iif(!"".equals(pic),"temp/" + pic,"includes/person-placeholder.png"));
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
        String url = "/register.jsp";
        request.setAttribute("title","Register");
        request.setAttribute("profilepic","includes/person-placeholder.png");
        request.setAttribute("Categories", InitServlet.categories);
        request.setAttribute("Types", InitServlet.types);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}
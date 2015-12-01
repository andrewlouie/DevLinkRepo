package andrew.servlets;

import andrew.addons.NextFile;
import andrew.addons.UsrChk;
import andrew.addons.emailcheck;
import andrew.data.CategoryDB;
import andrew.data.TypeDB;
import andrew.data.UserDB;
import andrew.tables.Category;
import andrew.tables.Type;
import andrew.tables.User;
import java.awt.Image;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class MemberServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = UsrChk.validUser(request);
        String url = "/members.jsp";
        request.setAttribute("title","Profile");
        if (user == null) {
            request.setAttribute("ref","Member");
            url = "/login.jsp";
            request.setAttribute("title","Login");
            getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
            return;
        }
        int maxlength = 500;
        String filename = "";
        String submit = "";
        String fn = "";
        String ln = "";
        String un = "";
        String em = "";
        String desc = "";
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
                        case "email": em = item.getString(); break;
                        case "skilllevel": skilllevel = item.getString(); break;
                        case "languages": lang = item.getString(); break;
                        case "description": desc = item.getString(); break;
                        case "website": website = item.getString(); break;
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
                    request.setAttribute("message","File is too big, 10MB maximum");
                }
                else if (valid) {
                request.setAttribute("message", "File Uploaded Successfully");
                //rename the picture and save it, delete the old one if it's all valid. update the user
                if (!user.getPic()) { user.setPic(true); UserDB.update(user); }
                String profpic = NextFile.findExt(request.getServletContext().getRealPath("") + "profilepics\\", user.getUserName());
                File oldfile = new File(request.getServletContext().getRealPath("") + "profilepics\\" + profpic);
                if (oldfile.exists()) oldfile.delete();
                FileUtils.moveFile(new File(request.getServletContext().getRealPath("") + "temp\\" + filename), new File(request.getServletContext().getRealPath("") + "profilepics\\" + un + "." + FilenameUtils.getExtension(filename)));
                }
                else request.setAttribute("message", "The file is not valid or could not be opened");
                }
            } catch (Exception ex) {
               request.setAttribute("message", "File Upload Failed");
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
        if (desc.length() > maxlength) desc = desc.substring(0,maxlength);
        if (lang.length() > maxlength) lang = lang.substring(0,maxlength);
        if (website.length() > maxlength) website = website.substring(0,maxlength);
        if ("Upload File".equals(submit)) {
        //remember all the fields that are filled out, regardless of validation but not updating the user on the server
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
        String oldun = "";
        List<String> e = new ArrayList<>();
        if (fn.isEmpty() || fn.length() == 0) e.add("First name is missing");
        else if (fn.length() > 30) e.add("Maximum length for first name is 30 characters");
        else user.setFirstName(fn);
        if (ln.isEmpty() || ln.length() == 0) e.add("Last name is missing");
        else if (ln.length() > 30) e.add("Maximum length for last name is 30 characters");
        else user.setLastName(ln);
        if (!un.equals(user.getUserName())) {
        if (un.isEmpty() || un.length() == 0) e.add("Username is missing");
        else if (un.length() > 30) e.add("Maximum length for user name is 30 characters");
        else if (UserDB.usernameExists(un)) e.add("Username is already in use");
        else if (!un.matches("^(?=.{5,30}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")) e.add("Username is not valid.  Must be between 5 and 30 characters.  May include _ and . but not at the start or end or in a row.");
        else { oldun = user.getUserName(); user.setUserName(un); }
        }
        if (!em.equals(user.getEmail())) {
        if (em.isEmpty() || em.length() == 0) e.add("Email is missing");
        else if (!emailcheck.isValidEmailAddress(em)) e.add("Email is invalid");
        else if (UserDB.emailExists(em)) e.add("Email is already assigned to an account");
        else user.setEmail(em);
        }
        user.setSkilllevel(sl);
        user.setDescription(desc);
        user.setLanguages(lang);
        user.setWebsite(website);
        if (e.isEmpty()) {
            //update user
            user = UserDB.update(user);
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            //if the username is changed we have to rename the picture
            if (!"".equals(oldun)) {
                File unfile = new File(request.getServletContext().getRealPath("") + "profilepics\\" + NextFile.findExt(request.getServletContext().getRealPath("") + "profilepics\\", oldun));
                if (unfile.exists()) {
                    unfile.renameTo(new File(request.getServletContext().getRealPath("") + "profilepics\\" + user.getUserName() + "." + FilenameUtils.getExtension(unfile.getName())));
                }
            }
        }
        request.setAttribute("errors", e);
        }
                String profilepic = "includes/person-placeholder.png";
        if (user.getPic()) {
            String profpic = NextFile.findExt(request.getServletContext().getRealPath("") + "profilepics\\", user.getUserName());
            if (!"".equals(profpic)) profilepic = "profilepics/" + profpic;
        }
        request.setAttribute("profilepic",profilepic);
        request.setAttribute("user",user);
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
        request.setAttribute("Categories", InitServlet.categories);
        request.setAttribute("Types", InitServlet.types);
        User user = UsrChk.validUser(request);
        String url = "/members.jsp";
        request.setAttribute("title","Profile");
        if (user == null) {
            request.setAttribute("ref","Member");
            url = "/login.jsp";
            request.setAttribute("title","Login");
            getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
            return;
        }
            String profilepic = "includes/person-placeholder.png";
        if (user.getPic()) {
            String profpic = NextFile.findExt(request.getServletContext().getRealPath("") + "profilepics\\", user.getUserName());
            if (!"".equals(profpic)) profilepic = "profilepics/" + profpic;
        }
        request.setAttribute("profilepic",profilepic);
        request.setAttribute("user",user);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    
}
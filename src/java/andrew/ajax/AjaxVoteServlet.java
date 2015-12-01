package andrew.ajax;

import andrew.addons.UsrChk;
import org.json.JSONObject;
import andrew.data.LinkDB;
import andrew.tables.Link;
import andrew.tables.User;
import andrew.tables.Vote;
import java.io.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.JSONException;

public class AjaxVoteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
        response.setCharacterEncoding("UTF-8");
        User user = null;
        if (UsrChk.validUser(request) != null) {
            HttpSession session = request.getSession();
            user = (User) session.getAttribute("user");
        }
            Vote vote = new Vote();
            Date today = new Date();
            vote.setDateTime(today);
            String ud = request.getParameter("vote");
            int li = 0;
            li = Integer.parseInt(request.getParameter("linkid"));
            Link link = LinkDB.selectLinkById(li);
            String ipAddress  = request.getHeader("X-FORWARDED-FOR");
            //set ipAddress variable
            if(ipAddress == null)
            {
                ipAddress = request.getRemoteAddr();
            }
            
            //check the up or down variable is set
            if (ud == null || ud.isEmpty() || ud.length() == 0) { response.getWriter().write("error"); return; }
            //check the link is set
            else if (link == null) { response.getWriter().write("error"); return; }
            //check the vote doesn't exists for that IP if no user is set.  If it's not the same void, it is switched in the voteExists method
            Vote v;
            if (user == null) v = link.getVote(ipAddress);
            else v = link.getVote(user.getUserName(), ipAddress);
            if (v != null) {
                if (user != null && v.getUser() == null) {
                    //add a user to the vote
                    v.setUser(user);
                    LinkDB.update(link);
                }
                else if (ud.equals("Up") == v.getUpDown()) {
                    //delete the vote
                    link.removeVote(v);
                    LinkDB.update(link);
                }
                else {
                    //update the vote
                    v.setUpDown(ud.equals("Up"));
                    LinkDB.update(link);
                }
            }
            //if everything passes, record the vote
            else {
            vote.setUpDown(ud.equals("Up"));
            vote.setUser(user);
            vote.setLink(link);
            vote.setIp(ipAddress);
            link.addVote(vote);
            LinkDB.update(link);
            }
            JSONObject json = new JSONObject();
            int up = link.getUpVotes();
            int down = link.getDownVotes();
        try {
            json.put("score",up - down);
            json.put("up", up);
            json.put("down", down);
        } catch (JSONException ex) {
            Logger.getLogger(AjaxVoteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            response.getWriter().write(json.toString());
    }   
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.sendRedirect("/404");
    }
}
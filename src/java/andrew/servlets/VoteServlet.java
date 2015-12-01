package andrew.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import andrew.data.LinkDB;
import andrew.tables.Vote;
import andrew.tables.User;
import java.util.Date;
import andrew.addons.*;
import andrew.tables.Link;

public class VoteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
            User user = null;
        if (UsrChk.validUser(request) != null) {
            HttpSession session = request.getSession();
            user = (User) session.getAttribute("user");
        }
            String url = request.getParameter("ref");
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
            if (ud == null || ud.isEmpty() || ud.length() == 0) { response.sendRedirect(url); return; }
            //check the link is set
            else if (link == null) { response.sendRedirect(url); return; }
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
            response.sendRedirect(url);
    }    
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {
    
    this.doPost(request, response);
}
    
    
}
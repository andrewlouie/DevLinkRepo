package andrew.tables;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    
    private String email;
    
    private String firstName;
    
    private String lastName;
    
    private String userName;
    
    private String password;
    
    private String salt;
    
    private String website;
    
    private int skilllevel;
    
    private boolean pic;
    
    private String description;
    
    private String languages;
    
    private String passreset;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date datejoined;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateloggedin;
    
    @OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    private List<Comment> comments;
    
    @OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    private List<Vote> votes;
    
    @OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    private List<Link> links;
    
    private String activation;
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    
    public int getSkilllevel() {
        return skilllevel;
    }

    public void setSkilllevel(int skilllevel) {
        this.skilllevel = skilllevel;
    }
        
    public boolean getPic() {
        return pic;
    }

    public void setPic(boolean pic) {
        this.pic = pic;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
    
    public String getPassreset() {
        return passreset;
    }

    public void setPassreset(String passreset) {
        this.passreset = passreset;
    }
    
    public Date getDatejoined() {
        return datejoined;
    }

    public void setDatejoined(Date datejoined) {
        this.datejoined = datejoined;
    }
    
    public Date getDateloggedin() {
        return dateloggedin;
    }

    public void setDateloggedin(Date dateloggedin) {
        this.dateloggedin = dateloggedin;
    }
    
    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public List<Vote> getVotes() {
        return votes;
    }
    
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }
    
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Link> getLinks() {
        Collections.reverse(links);
        return links;
    }
    
    public void setActivation (String activation) {
        this.activation = activation;
    }
    public String getActivation () {
        return activation;
    }
    public boolean isSet () {
        return userId > 0;
    }
    public void addLink (Link link) {
        link.setUser(this);
        this.links.add(link);
    }
    public void addComment (Comment comment) {
        comments.add(comment);
    }
    public String getSkill() {
        String level = "Beginner";
        switch (skilllevel) {
            case 0: level = "Beginner";
                break;
            case 1: level = "Intermediate";
                break;
            case 2: level = "Expert";
                break;
            case 3: level = "Advanced";
                break;
        }
        return level;
    }
    public int getRating() {
        int rate = 0;
        for (Link link : links) {
            rate += (link.getUpVotes() - link.getDownVotes());
        }
        return rate;
    }
    public int getLastLinkId() {
        return links.get(links.size() -1).getLinkId();
    }
}
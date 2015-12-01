package andrew.tables;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "link")
public class Link implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int linkId;
   
    @ManyToOne(fetch=EAGER,optional=false)
    @JoinColumn(name="catId")
    private Category category;
    
    @ManyToOne(fetch=EAGER,optional=false)
    @JoinColumn(name="userId")
    private User user;
    
    @ManyToOne(fetch=EAGER,optional=false)
    @JoinColumn(name="typeId")
    private Type type;
    
    @OneToMany(fetch=EAGER, cascade=CascadeType.ALL)
    private List<Vote> votes;
    
    @OneToMany(fetch=EAGER, cascade=CascadeType.ALL)
    private List<Comment> comments;
    
    private String url;
    
    private String description;
    
    private boolean picture;
    
    private String title;
    
    private int skilllevel;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateTime;
    
    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean getPicture() {
        return picture;
    }

    public void setPicture(boolean picture) {
        this.picture = picture;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public int getSkilllevel() {
        return skilllevel;
    }

    public void setSkilllevel(int skilllevel) {
        this.skilllevel = skilllevel;
    }
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public Type getType() {
        return type;
    }
    
    public void setType(Type type) {
        this.type = type;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
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
        Collections.reverse(comments);
        return comments;
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
    public int getVoteCount() {
        return votes.size();
    }
    public int getUpVotes() {
        int ups = 0;
            for (Vote vote : votes) {
            if (vote.getUpDown()) ups += 1;
        }
        return ups;
    }
    public int getDownVotes() {
        int downs = 0;
        for (Vote vote : votes) {
            if (!vote.getUpDown()) downs += 1;
        }
        return downs;
    }
    public int getScore() {
        int downs = 0;
        int ups = 0;
        for (Vote vote : votes) {
            if (!vote.getUpDown()) downs += 1;
            else ups += 1;
        }
        return ups - downs;
    }
    public int getCommentCount() {
        return comments.size();
    }
    public void addVote(Vote vote) {
        votes.add(vote);
    }
    public void addComment(Comment comment) {
        comments.add(comment);
    }
    public double getRating() {
        int score = getUpVotes() - getDownVotes();
        long epoch = dateTime.getTime() / 1000;
        double thing = Math.abs(score);
        if (thing < 1) thing = 1;
        double order = Math.log10(thing);
        int sign;
        if (score > 0) sign = 1;
        else if (score < 0) sign = -1;
        else sign = 0;
        long seconds = epoch - 1447812444;
        return sign * order + seconds / 90000;
    }
    public void removeVote(Vote vote) {
        votes.remove(vote);
    }
    public Vote getVote(String ip) {
        for (Vote v : votes) {
            if (v.getIp().equals(ip)) return v;
        }
        return null;
    }
    public Vote getVote(String un,String ip) {
        for (Vote v : votes) {
            if (v.getIp().equals(ip) || (v.getUser() != null && v.getUser().getUserName().equals(un))) { return v; }
        }
        return null;
    }
}
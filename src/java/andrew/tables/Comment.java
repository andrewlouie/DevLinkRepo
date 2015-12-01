package andrew.tables;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "comments")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int commentId;
    
    @ManyToOne(fetch=EAGER,optional=false)
    @JoinColumn(name="userId")
    private User user;
    
    @ManyToOne(fetch=EAGER,optional=false)
    @JoinColumn(name="linkId")
    private Link link;
    
    private String comment;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateTime;
        
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Link getLink() {
        return link;
    }
}
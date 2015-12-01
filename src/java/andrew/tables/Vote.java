package andrew.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "votes")
public class Vote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int voteId;
    
    @ManyToOne(fetch=EAGER,optional=false)
    @JoinColumn(name="linkId")
    private Link link;
    
    @ManyToOne(fetch=EAGER,optional=false)
    @JoinColumn(name="userId")
    private User user;
    
    private boolean upDown;
    
    private String ip;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateTime;

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }
    
    public boolean getUpDown() {
        return upDown;
    }

    public void setUpDown(boolean upDown) {
        this.upDown = upDown;
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
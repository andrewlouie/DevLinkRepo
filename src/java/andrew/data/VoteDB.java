package andrew.data;

import andrew.tables.Link;
import andrew.tables.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import andrew.tables.Vote;
import javax.persistence.TypedQuery;

public class VoteDB {

    public static void insert(Vote vote) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.persist(vote);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public static void update(Vote vote) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();       
        try {
            em.merge(vote);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static void delete(Vote vote) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.remove(em.merge(vote));
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        } finally {
            em.close();
        }       
    }
    
    public static Vote selectVoteByUserNameOrIp(User user,String ip,Link link) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT v FROM Vote v " +
                "WHERE (v.ip = :ip OR v.user = :user) AND v.link = :link";
        TypedQuery<Vote> q = em.createQuery(qString, Vote.class).setMaxResults(1);
        q.setParameter("user", user);
        q.setParameter("ip", ip);
        q.setParameter("link",link);
        try {
            Vote vote = q.getSingleResult();
            return vote;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public static Vote selectVoteByUserNameOrIp(String ip,Link link) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT v FROM Vote v " +
                "WHERE v.ip = :ip AND v.link = :link";
        TypedQuery<Vote> q = em.createQuery(qString, Vote.class);
        q.setParameter("ip", ip);
        q.setParameter("link", link);
        try {
            Vote vote = q.getSingleResult();
            return vote;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
            
    public static Vote getVote(User user,String ip,Link link) {
        Vote v = selectVoteByUserNameOrIp(user,ip,link);
        return v;
    }
    
    public static Vote getVote(String ip,Link link) {
        Vote v = selectVoteByUserNameOrIp(ip,link);
        return v;
    }
}
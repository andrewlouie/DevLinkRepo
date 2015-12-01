package andrew.data;

import andrew.addons.iif;
import andrew.tables.Category;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import andrew.tables.Link;
import andrew.tables.Type;
import andrew.tables.User;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class LinkDB {

    public static void insert(Link link) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.persist(link);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public static void update(Link link) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();       
        try {
            em.merge(link);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static void delete(Link link) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.remove(em.merge(link));
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }       
    }
     public static List<Link> selectLinksByUser(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT l from Link l " +
                "WHERE l.user = :user";
        TypedQuery<Link> q = em.createQuery(qString, Link.class);
        List<Link> results = null;
        try {
            q.setParameter("user", user);
            results = q.getResultList();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
        
        return results;
    }
    public static List<Link> selectLinksByDate(int firstResult,int maxResult) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT l from Link l " +
                "ORDER BY l.dateTime DESC";
        TypedQuery<Link> q = em.createQuery(qString, Link.class).setMaxResults(maxResult).setFirstResult(firstResult);
        List<Link> results = null;
        try {
            results = q.getResultList();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
        
        return results;
    }
     public static Link selectLinkById(int linkId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT u FROM Link u " +
                "WHERE u.linkId = :linkId";
        TypedQuery<Link> q = em.createQuery(qString, Link.class);
        q.setParameter("linkId", linkId);
        try {
            Link link = q.getSingleResult();
            return link;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    public static List<Link> selectLinkByUpVotes(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT DISTINCT l FROM Link l JOIN l.votes v" +
                " WHERE v.user = :user AND v.upDown = :true ORDER BY v.dateTime DESC";
        TypedQuery<Link> q = em.createQuery(qString, Link.class);
        List<Link> results = null;
       q.setParameter("user", user);
       q.setParameter("true",true);
        try {
            results = q.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
        return results;
    }
    public static List<Link> selectLinks(Category category,Type type,String search,int skill) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT l from Link l";
        int count = 0;
        if (category != null) { qString += " WHERE l.category = :category"; count++; }
        if (type != null) { qString += iif.iif(count > 0," AND"," WHERE") + " l.type = :type"; count++; }
        if (search != null && search.length() > 0) { qString += iif.iif(count > 0," AND"," WHERE") + " (l.title LIKE :search OR l.description LIKE :search)"; count++; }
        if (skill >= 0) { qString += iif.iif(count>0, " AND"," WHERE") + " l.skilllevel = :skill"; }
        qString += " ORDER BY l.dateTime DESC";
        TypedQuery<Link> q = em.createQuery(qString, Link.class);
        List<Link> results = null;
        if (category != null) q.setParameter("category",category);
        if (type != null) q.setParameter("type",type);
        if (search != null && search.length() > 0) q.setParameter("search","%" + search.replaceAll(" ","%") + "%");
        if (skill >= 0) q.setParameter("skill",skill);
        try {
            results = q.getResultList();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
        
        return results;
    }
}

package andrew.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


import andrew.tables.Category;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class CategoryDB {

    public static void insert(Category category) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.persist(category);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public static void update(Category category) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();       
        try {
            em.merge(category);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static void delete(Category category) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.remove(em.merge(category));
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }       
    }
       public static List<Category> selectCategories() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c from Category c";
        TypedQuery<Category> q = em.createQuery(qString, Category.class);
        List<Category> results = null;
        try {
            results = q.getResultList();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
        
        return results;
    }
       
    public static Category selectCatById(int catId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT u FROM Category u " +
                "WHERE u.catId = :catId";
        TypedQuery<Category> q = em.createQuery(qString, Category.class);
        q.setParameter("catId", catId);
        try {
            Category category = q.getSingleResult();
            return category;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
}
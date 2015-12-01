package andrew.data;

import andrew.tables.Category;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import andrew.tables.Type;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class TypeDB {

    public static void insert(Type type) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.persist(type);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public static void update(Type type) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();       
        try {
            em.merge(type);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static void delete(Type type) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.remove(em.merge(type));
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }       
    }
    public static List<Type> selectTypes() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c from Type c";
        TypedQuery<Type> q = em.createQuery(qString, Type.class);
        List<Type> results = null;
        try {
            results = q.getResultList();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
        
        return results;
    }
        public static Type selectTypeById(int typeId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT u FROM Type u " +
                "WHERE u.typeId = :typeId";
        TypedQuery<Type> q = em.createQuery(qString, Type.class);
        q.setParameter("typeId", typeId);
        try {
            Type type = q.getSingleResult();
            return type;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
}
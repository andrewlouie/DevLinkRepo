package andrew.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import andrew.tables.User;
import javax.persistence.TypedQuery;

public class UserDB {

    public static void insert(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.persist(user);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public static User update(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();       
        try {
            em.merge(user);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        } finally {
        String qString = "SELECT u FROM User u " +
                "WHERE u.userId = :userId";
        TypedQuery<User> q = em.createQuery(qString, User.class);
        q.setParameter("userId", user.getUserId());
        try {
            User usernew = q.getSingleResult();
            return usernew;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
        }
    }

    public static void delete(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.remove(em.merge(user));
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        } finally {
            em.close();
        }       
    }

    public static User selectUserByEmail(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT u FROM User u " +
                "WHERE u.email = :email";
        TypedQuery<User> q = em.createQuery(qString, User.class);
        q.setParameter("email", email);
        try {
            User user = q.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static User selectUserByName(String userName) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT u FROM User u " +
                "WHERE u.userName = :userName";
        TypedQuery<User> q = em.createQuery(qString, User.class);
        q.setParameter("userName", userName);
        try {
            User user = q.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public static User selectUserByActivation(String activation) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT u FROM User u " +
                "WHERE u.activation = :activation";
        TypedQuery<User> q = em.createQuery(qString, User.class);
        q.setParameter("activation", activation);
        try {
            User user = q.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
        public static User selectUserByPassreset(String passreset) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT u FROM User u " +
                "WHERE u.passreset = :passreset";
        TypedQuery<User> q = em.createQuery(qString, User.class);
        q.setParameter("passreset", passreset);
        try {
            User user = q.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public static boolean emailExists(String email) {
        User u = selectUserByEmail(email);   
        return u != null;
    }
    
    public static boolean usernameExists(String username) {
        User u = selectUserByName(username);
        return u != null;
    }
}
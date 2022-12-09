package jm.task.core.jdbc.dao;

import jakarta.persistence.criteria.CriteriaQuery;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    SessionFactory factory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createNativeQuery("CREATE TABLE " +
                    "user(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(20), age INT(150))").executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void dropUsersTable() {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createNativeQuery("DROP TABLE IF EXISTS user").executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User user = new User(name, lastName, age);
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = factory.openSession();
        CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.from(User.class);
        Transaction transaction = session.beginTransaction();
        List<User> userList = session.createQuery(criteriaQuery).getResultList();
        try {
            transaction.commit();
            return userList;
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("DELETE FROM user").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}

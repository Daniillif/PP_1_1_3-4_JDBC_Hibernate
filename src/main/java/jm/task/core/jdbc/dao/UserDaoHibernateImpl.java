package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()){
            Transaction tx1 = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS Users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";

            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            tx1.commit();
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()){
            Transaction tx1 = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS Users";
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            tx1.commit();
            session.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()){
            Transaction tx1 = session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            tx1.commit();
            session.close();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()){
            Transaction tx1 = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            tx1.commit();
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return (List<User>) session.createCriteria(User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        String sql = "Truncate table Users";
        session.createSQLQuery(sql).executeUpdate();
        tx1.commit();
        session.close();
    }
}
